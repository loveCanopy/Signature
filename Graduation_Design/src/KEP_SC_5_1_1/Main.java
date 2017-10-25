package KEP_SC_5_1_1;

import java.util.Random;
public class Main {

	
	public static Random random=KEP_SC_5_1.Main.random;
	public static long[] L_seq=new long[2];
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
		
		long[] E_seq = create_E_seq(Number);
		System.out.println("生成的D值");
		D_seq_1=create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq_1);
		System.out.println("生成的E值");
		KEP_SC_5_1.Main.print_seq(E_seq);
		
	
		System.out.println("L0,L1的值");
		L_seq = create_L_seq(A_seq, delta, P_V,E_seq);
		KEP_SC_5_1.Main.print_seq(L_seq);
		
		
		System.out.println("公钥为(" + KEP_SC_5_1.Main.toValue(B_seq) + "," + KEP_SC_5_1.Main.toValue(D_seq_1) + "," + create_M_ni + ")");
		System.out
				.println("私钥为(" + KEP_SC_5_1.Main.toValue(A_seq) + KEP_SC_5_1.Main.toValue(delta) + KEP_SC_5_1.Main.toValue(M_W) + KEP_SC_5_1.Main.toValue(P_V) + KEP_SC_5_1.Main.toValue(L_seq) + ")");
		Thread.sleep(1000);
		System.out.println("密钥交换");
		System.out.println("Alice操作部分");
		System.out.println("Alice获得Bob的公钥(" + KEP_SC_5_1.Main.toValue(B_seq) + "," + KEP_SC_5_1.Main.toValue(D_seq_1) + "," + create_M_ni + ")");
		System.out.println("Alice随机选择(x1,x2,...,xn)并计算Sb和K的值");
		long[] create_source = KEP_SC_5_1.Main.create_source(Number);
		KEP_SC_5_1.Main.print_seq(create_source);
		long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
		long create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq_1, create_M_ni);
		String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(create_K + "");
		System.out.println("将" + create_K + "作为会话密钥,将(" + create_B_sum + "," + encoderByMD5 + ")发送给Bob");
		System.out.println("Bob操作部分");
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' 满足 使得 (W*W')MOD M=1] --- " + w_ni);
		System.out.println(KEP_SC_5_1.Main.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum,L_seq));
		
	}

	public static long[] create_E_seq(int n){
		long[] E_seq=new long[n];
		int e=random.nextInt(2)*3+1;
		int min=-e;
		int max=e;
		for(int i=0;i<n;i++){
			E_seq[i]=(long)(random.nextInt(max - min ) + min);
		}
		return E_seq;
	}
	
	public static long[] create_D_seq_1(long[] D_seq,long[] E_seq,long M_ni){
		
		long[] D_seq_1=new long[D_seq.length];
		for(int i=0;i<D_seq_1.length;i++){
			D_seq_1[i]=(D_seq[i]+E_seq[i])%M_ni;
		}
			
		return D_seq_1;
	}
	
	/**
	 * @param A_seq
	 *            A序列
	 * @param delta
	 *            delta序列
	 * @param P_V
	 *            满足上述条件的P,V值
	 * @return L_seq 计算L0 和 L1 的值
	 */
	public static long[] create_L_seq(long[] A_seq, long[] delta, long[] P_V,long[] E_seq) {
		long P = P_V[0];
		long V = P_V[1];
		
		double temp_l0 = 0.0;
		double temp_l0_1=0.0;
		double temp_l0_2=0.0;
		for (int i = 0; i < A_seq.length; i++) {
			temp_l0_1=(V * A_seq[i]) % P;
			temp_l0_2=(1 - delta[i]) *temp_l0_1/P;
			temp_l0 +=temp_l0_2;
		}
		long L0 = (long) temp_l0 + 1;
		double temp_l1 = 0.0;
		double temp_l1_1=0.0;
		double temp_l1_2=0.0;
		for (int i = 0; i < A_seq.length; i++) {
			// temp_l1+=(double)(delta[i]*(P-(V*A_seq[i]%P))%P)/P;
			temp_l1_1=((P - ((V * A_seq[i]) % P)) % P);
			temp_l1_2=delta[i] * temp_l1_1/ P;
			temp_l1 +=temp_l1_2 ;
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
		
		L_seq[0] = L0-e1;
		L_seq[1] = L1+e2;
		return L_seq;
	}

	
	
}
