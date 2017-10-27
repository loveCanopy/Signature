package KEP_SC_5_1_4;

import java.util.Random;

/**
 * 整合算法 KEP_SC_5_1 KEP_SC_5_1_1 KEP_SC_5_1_2 KEP_SC_5_1_3
 * */


public class Main {
	public static Random random = KEP_SC_5_1.Main.random;
	public static long[] L_seq = new long[2];
	public static long[] M_W = new long[2];
	public static long[] P_V = new long[2];
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int Number = 6;
		long[] A_seq = new long[Number];
		long[] B_seq = new long[Number];
		long[] C_seq = new long[Number];
		long[] D_seq = new long[Number];
		long[] D_seq_1=new long[Number];
		long[] delta = new long[Number];
		// TODO Auto-generated method stub
		System.out.println("密钥生成");
		Thread.sleep(5000);
		A_seq = KEP_SC_5_1.Main.create_A_seq(Number);
		System.out.println("生成A序列");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A背包向量的密度");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("生成的M值,W值");
		M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("生成的B序列");
		B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("生成的P值,V值");
		P_V = KEP_SC_5_1.Main.createP_V(Number);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("随机生成的delta序列");
		delta = KEP_SC_5_1.Main.create_delta(Number);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("生成的C值");
		C_seq = KEP_SC_5_1.Main.create_C_seq(Number, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("生成的M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(Number, C_seq);
		System.out.println(create_M_ni);
		
		D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);
		
		long[] U_K_V = KEP_SC_5_1_2.Main.create_U_K_V(create_M_ni);
		System.out.println("u的值"+U_K_V[0] + ", k的值"+U_K_V[1]+ ", v的值"+U_K_V[2]);
		
		long[] R_seq =KEP_SC_5_1_2.Main.create_R_seq(Number,create_M_ni,U_K_V);
		System.out.println("生成的R序列");
		KEP_SC_5_1.Main.print_seq(R_seq);
		
	
		
		long[] E_seq = KEP_SC_5_1_1.Main.create_E_seq(Number);
		System.out.println("生成的E值");
		KEP_SC_5_1.Main.print_seq(E_seq);
		
		System.out.println("生成的D值");
		D_seq_1=create_D_seq(D_seq, R_seq,E_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq_1);
		System.out.println("L0,L1的值");
		L_seq = create_L_seq(A_seq, delta, P_V,R_seq,E_seq,create_M_ni,U_K_V);
		KEP_SC_5_1.Main.print_seq(L_seq);
		
		boolean flag = true;
		long create_K = 0;
		long L = 0;
		long R = 0;
		long Key=0;
		long[] create_source = new long[Number];
		while (flag) {
			create_source = KEP_SC_5_1.Main.create_source(Number);
			KEP_SC_5_1.Main.print_seq(create_source);
			create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq_1, create_M_ni);
			System.out.println("生成的K的值为"+create_K);
			L = KEP_SC_5_1_3.Main.create_L(L_seq,Number);
			System.out.println("生成的L的值"+L);
			R = (long) (create_K % (Math.pow(2, L)));
			System.out.println("生成的R的值"+R);
			if (R >= L_seq[1] && R + L_seq[0] < Math.pow(2, L)) {
				flag = false;
			}
			Key=(long)(create_K/(Math.pow(2, L)));
			if(Key!=0){
				flag=false;
			}
		}
		long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
		System.out.println("公钥为(" + KEP_SC_5_1.Main.toValue(B_seq) + "," + KEP_SC_5_1.Main.toValue(D_seq_1) + ","
				+ KEP_SC_5_1.Main.toValue(L_seq) + L +","+ create_M_ni + ")");
		System.out.println("私钥为(" + KEP_SC_5_1.Main.toValue(M_W) + KEP_SC_5_1.Main.toValue(P_V) + ")");
		Thread.sleep(1000);
		System.out.println("密钥交换");
		System.out.println("Alice操作部分");
		System.out.println("Alice获得Bob的公钥(" + KEP_SC_5_1.Main.toValue(B_seq) + "," + KEP_SC_5_1.Main.toValue(D_seq_1)
				+ "," + KEP_SC_5_1.Main.toValue(L_seq) + L +","+ create_M_ni + ")");
		System.out.println("Alice随机选择(x1,x2,...,xn)并计算Sb和K的值");
		String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(Key + "");
		System.out.println("将" + Key + "作为会话密钥,将(" + create_B_sum + "," + encoderByMD5 + ")发送给Bob");
		System.out.println("Bob操作部分");
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' 满足 使得 (W*W')MOD M=1] --- " + w_ni);
		System.out.println(get_K_ni(w_ni,M_W[0], P_V[1], P_V[0], create_M_ni,  Key,create_B_sum, L_seq,  U_K_V[0],
				 U_K_V[1] , R,  L));
		
		
	}
	
		public static long[] create_D_seq(long[] D_seq,long[] R_seq,long[] E_seq,long M_ni){
			
			long[] D_seq_1 = new long[D_seq.length];
			for (int i = 0; i < D_seq_1.length; i++) {
				D_seq_1[i] = (D_seq[i] + R_seq[i]+E_seq[i]) % M_ni;
			}

			return D_seq_1;
		}
	
		public static long[] create_L_seq(long[] A_seq, long[] delta, long[] P_V, long[] R_seq,long[] E_seq,long M_ni,long[] U_K_V){
			long P = P_V[0];
			long V = P_V[1];

			double temp_l0 = 0.0;
			double temp_l0_1 = 0.0;
			double temp_l0_2 = 0.0;
			for (int i = 0; i < A_seq.length; i++) {
				temp_l0_1 = (V * A_seq[i]) % P;
				temp_l0_2 = (1 - delta[i]) * temp_l0_1 / P;
				temp_l0 += temp_l0_2;
			}
			long L0 = (long) temp_l0 + 1;
			double temp_l1 = 0.0;
			double temp_l1_1 = 0.0;
			double temp_l1_2 = 0.0;
			for (int i = 0; i < A_seq.length; i++) {
				// temp_l1+=(double)(delta[i]*(P-(V*A_seq[i]%P))%P)/P;
				temp_l1_1 = ((P - ((V * A_seq[i]) % P)) % P);
				temp_l1_2 = delta[i] * temp_l1_1 / P;
				temp_l1 += temp_l1_2;
			}
			long L1 = (long) temp_l1 + 1;
			int e1=0;
			int e2=0;
			for(int i=0;i<E_seq.length;i++){
				if(E_seq[i]<0){
					e1+=E_seq[i];
				}else{
					e2+=E_seq[i];
				}
				
			}
			long l_temp = KEP_SC_5_1.Main.add_seq(R_seq)/U_K_V[0];
			L_seq[0] = L0 + (int)(l_temp/U_K_V[1])*U_K_V[2]-e1;
			L_seq[1] = L1 +e2;
			return L_seq;
			
		}
		
		public static String get_K_ni(long W_ni, long M, long V, long P, long M_ni, long K, long Sb, long[] L_seq, long u,
				long k ,long R, long L) throws Exception {
			
			long Sa = (W_ni * Sb) % M;
			long T = (V * Sa / P) % M_ni;
			System.out.println("T的值为" + T);
			int j=1;
			long K_ni=0;
			for(int i=0;i<=k-1;i++){
				if(i==0) K_ni= (long) ((long) T / Math.pow(2, L));
				if(i!=0) K_ni=(long)(((T+i*u)%M_ni)/Math.pow(2, L));
				if (KEP_SC_5_1.Main.encoderByMD5(K_ni + "").equals(KEP_SC_5_1.Main.encoderByMD5(K + ""))) {
						return "还原密钥成功 "+K_ni+"还原次数  "+j;
					}
				j++;
				
			}

			return "error";
			
			
		}
		
		}
