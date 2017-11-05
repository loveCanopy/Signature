package KEP_SC_5_1_2;

import java.util.Random;

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
		System.out.println("��Կ����");
		Thread.sleep(5000);
		A_seq = KEP_SC_5_1.Main.create_A_seq(Number);
		System.out.println("����A����");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		P_V = KEP_SC_5_1.Main.createP_V(Number);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		delta = KEP_SC_5_1.Main.create_delta(Number);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		C_seq = KEP_SC_5_1.Main.create_C_seq(Number, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(Number, C_seq);
		System.out.println(create_M_ni);
		D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);
		
		long[] U_K_V = create_U_K_V(create_M_ni);
		System.out.println("u��ֵ"+U_K_V[0] + ", k��ֵ"+U_K_V[1]+" , v��ֵΪ"+U_K_V[2]);
		
		long[] R_seq = create_R_seq(Number,create_M_ni,U_K_V);
		System.out.println("���ɵ�R����");
		KEP_SC_5_1.Main.print_seq(R_seq);
		
		
		
		System.out.println("���ɵ�Dֵ");
		D_seq_1=create_D_seq(D_seq, R_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq_1);
		                                                                                                                             
	
	
		System.out.println("L0,L1��ֵ");
		L_seq = create_L_seq(A_seq, delta, P_V,R_seq, create_M_ni,U_K_V);
		KEP_SC_5_1.Main.print_seq(L_seq);
		
		
		System.out.println("��ԿΪ(" + KEP_SC_5_1.Main.toValue(B_seq) + "," + KEP_SC_5_1.Main.toValue(D_seq_1) + "," + create_M_ni + ")");
		System.out
				.println("˽ԿΪ(" + KEP_SC_5_1.Main.toValue(A_seq) + KEP_SC_5_1.Main.toValue(delta) + KEP_SC_5_1.Main.toValue(M_W) + KEP_SC_5_1.Main.toValue(P_V) + KEP_SC_5_1.Main.toValue(L_seq) + ")");
		Thread.sleep(1000);
		System.out.println("��Կ����");
		System.out.println("Alice��������");
		System.out.println("Alice���Bob�Ĺ�Կ(" + KEP_SC_5_1.Main.toValue(B_seq) + "," + KEP_SC_5_1.Main.toValue(D_seq_1) + "," + create_M_ni + ")");
		System.out.println("Alice���ѡ��(x1,x2,...,xn)������Sb��K��ֵ");
		long[] create_source = KEP_SC_5_1.Main.create_source(Number);
		KEP_SC_5_1.Main.print_seq(create_source);
		long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
		long create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq_1, create_M_ni);
		String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(create_K + "");
		System.out.println("��" + create_K + "��Ϊ�Ự��Կ,��(" + create_B_sum + "," + encoderByMD5 + ")���͸�Bob");
		System.out.println("Bob��������");
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		System.out.println(get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum,L_seq,U_K_V[0],U_K_V[1]));
		
	}

	public static long[] create_U_K_V(long M_ni){
		long[] U_K_V=new long[3];
		long k = random.nextInt(8) + 2;
		long u = M_ni / k;
		long v=M_ni-k*u;
		U_K_V[0]=u;
		U_K_V[1]=k;
		U_K_V[2]=v;
		return U_K_V;
	}
	
	
	
	public static long[] create_R_seq(int n, long M_ni,long[] U_K_V) {
		
		long[] R_seq = new long[n];
		for (int i = 0; i < R_seq.length; i++) {
			R_seq[i] = random.nextInt((int)U_K_V[1] - 1) * U_K_V[0];
		}
		return R_seq;
	}

	public static long[] create_D_seq(long[] D_seq, long[] R_seq, long M_ni) {

		long[] D_seq_1 = new long[D_seq.length];
		for (int i = 0; i < D_seq_1.length; i++) {
			D_seq_1[i] = (D_seq[i] + R_seq[i]) % M_ni;
		}

		return D_seq_1;

	}

	/**
	 * @param A_seq
	 *            A����
	 * @param delta
	 *            delta����
	 * @param P_V
	 *            ��������������P,Vֵ
	 * @return L_seq ����L0 �� L1 ��ֵ
	 */
	public static long[] create_L_seq(long[] A_seq, long[] delta, long[] P_V, long[] R_seq,long M_ni,long[] U_K_V) {
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
		long l_temp = KEP_SC_5_1.Main.add_seq(R_seq)/U_K_V[0];
		L_seq[0] = L0 + (int)(l_temp/U_K_V[1])*U_K_V[2];
		L_seq[1] = L1 ;
		return L_seq;
	}

	/**
	 * KEP_SC_5.1.2 �ݹ����K' ʹ�� H(K)=H(K')
	 */
	public static String get_K_ni(long W_ni, long M, long V, long P, long M_ni, long K, long Sb, long[] L_seq, long u,
			long k) throws Exception {
		long Sa = (W_ni * Sb) % M;
		long T = (V * Sa / P) % M_ni;
		System.out.println("T��ֵΪ" + T);
		int l0 = 0, l1 = 1;
		long K1 = 0l;
		int i = 1;
		while (l0 <= L_seq[0]) {
			K1 = (T - l0) % M_ni;

			for (int j = 0; j <= k - 1; j++) {
				K1 = (T - l0 + j * u) % M_ni;
				if (KEP_SC_5_1.Main.encoderByMD5(K1 + "").equals(KEP_SC_5_1.Main.encoderByMD5(K + ""))) {
					System.out.println("�Ƚϴ���:" + i);
					return K1 + "";
				} else {
					i++;
				}
			}

			l0 = l0 + 1;
		}

		while (l1 <= L_seq[1]) {

			for (int j = 0; j < k - 1; j++) {
				K1 = (T + l1 + j * u) % M_ni;
				if (KEP_SC_5_1.Main.encoderByMD5(K1 + "").equals(KEP_SC_5_1.Main.encoderByMD5(K + ""))) {
					System.out.println("�Ƚϴ���" + i);
					return K1 + "";
				} else {
					i++;
				}

			}
			l1 = l1 + 1;
		}

		return "������";
	}

}
