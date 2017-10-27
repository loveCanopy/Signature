package Tool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Tool_class {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Random random = new Random();
		Set<String> set = new HashSet<String>();
		String methodName = args[0];
		int Number = Integer.parseInt(args[1]);
		int count = Integer.parseInt(args[2]);
		for (int i = 0; i < count; i++) {
			String str = "";
			for (int j = 0; j < Number; j++) {
				if (j != Number - 1)
					str += random.nextInt(2) + " ";
				if (j == Number - 1)
					str += random.nextInt(2);
			}
			if (!set.contains(str)) {
				set.add(str);
			}
		}

		if (methodName.equals("KEP_SC_5_1"))
			Test_KEP_SC_5_1(set, Number, methodName);
		if (methodName.equals("KEP_SC_5_1_1")) {
			Test_KEP_SC_5_1_1(set, Number, methodName);
		}
		if (methodName.equals("KEP_SC_5_1_2")) {
			Test_KEP_SC_5_1_2(set, Number, methodName);
		}
		if (methodName.equals("KEP_SC_5_1_3")) {
			Test_KEP_SC_5_1_3(set, Number, methodName);
		}
		if (methodName.equals("KEP_SC_5_1_4")) {
			Test_KEP_SC_5_1_4(set, Number, methodName);
		}
	}

	public static void Test_KEP_SC_5_1(Set<String> set, int N, String methodName) throws Exception {

		long[] A_seq = KEP_SC_5_1.Main.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		long[] M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		long[] B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		long[] P_V = KEP_SC_5_1.Main.createP_V(N);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		long[] delta = KEP_SC_5_1.Main.create_delta(N);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		long[] C_seq = KEP_SC_5_1.Main.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);
		System.out.println("���ɵ�Dֵ");
		long[] D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);
		KEP_SC_5_1.Main.print_seq(D_seq);
		System.out.println("L0,L1��ֵ");
		long[] L_seq = KEP_SC_5_1.Main.create_L_seq(A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(L_seq);
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		long[] create_source = new long[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = Long.parseLong(str.split(" ")[i]);
			}
			KEP_SC_5_1.Main.print_seq(create_source);
			long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
			long create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(create_K + "");
			System.out.println(
					KEP_SC_5_1.Main.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum, L_seq));
			System.out.println("---------------------------------");
		}

	}

	public static void Test_KEP_SC_5_1_1(Set<String> set, int N, String methodName) throws Exception {
		long[] A_seq = KEP_SC_5_1.Main.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		long[] M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		long[] B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		long[] P_V = KEP_SC_5_1.Main.createP_V(N);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		long[] delta = KEP_SC_5_1.Main.create_delta(N);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		long[] C_seq = KEP_SC_5_1.Main.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		// ��ͬ��KEP_SC_5_1��Dֵ
		long[] D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);
		long[] E_seq = KEP_SC_5_1_1.Main.create_E_seq(N);
		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.Main.print_seq(E_seq);
		System.out.println("���ɵ�Dֵ");
		long[] D_seq1 = KEP_SC_5_1_1.Main.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq1);

		// ��ͬ��KEP_SC_5_1��Dֵ
		System.out.println("L0,L1��ֵ");
		long[] L_seq = KEP_SC_5_1_1.Main.create_L_seq(A_seq, delta, P_V, E_seq);
		KEP_SC_5_1.Main.print_seq(L_seq);
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		long[] create_source = new long[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = Long.parseLong(str.split(" ")[i]);
			}
			KEP_SC_5_1.Main.print_seq(create_source);
			long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
			long create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq1, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(create_K + "");
			System.out.println(
					KEP_SC_5_1.Main.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum, L_seq));
			System.out.println("---------------------------------");
		}
	}

	public static void Test_KEP_SC_5_1_2(Set<String> set, int N, String methodName) throws Exception {

		long[] A_seq = KEP_SC_5_1.Main.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		long[] M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		long[] B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		long[] P_V = KEP_SC_5_1.Main.createP_V(N);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		long[] delta = KEP_SC_5_1.Main.create_delta(N);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		long[] C_seq = KEP_SC_5_1.Main.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		// ��ͬ��KEP_SC_5_1��Dֵ
		long[] D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);
		long[] U_K_V = KEP_SC_5_1_2.Main.create_U_K_V(create_M_ni);
		long[] R_seq = KEP_SC_5_1_2.Main.create_R_seq(N, create_M_ni, U_K_V);
		System.out.println("���ɵ�Rֵ");
		KEP_SC_5_1.Main.print_seq(R_seq);
		System.out.println("���ɵ�Dֵ");
		long[] D_seq1 = KEP_SC_5_1_2.Main.create_D_seq(D_seq, R_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq1);

		// ��ͬ��KEP_SC_5_1��Lֵ
		System.out.println("L0,L1��ֵ");
		long[] L_seq = KEP_SC_5_1_2.Main.create_L_seq(A_seq, delta, P_V, R_seq,create_M_ni,U_K_V);
		KEP_SC_5_1.Main.print_seq(L_seq);
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		long[] create_source = new long[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = Long.parseLong(str.split(" ")[i]);
			}
			KEP_SC_5_1.Main.print_seq(create_source);
			long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
			long create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq1, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(create_K + "");
			// �ı�ݹ���ⷽ��
			System.out.println(KEP_SC_5_1_2.Main.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K,
					create_B_sum, L_seq, U_K_V[0], U_K_V[1]));
			System.out.println("---------------------------------");

		}
	}

	public static void Test_KEP_SC_5_1_3(Set<String> set, int N, String methodName) throws Exception {
		long[] A_seq = KEP_SC_5_1.Main.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		long[] M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		long[] B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		long[] P_V = KEP_SC_5_1.Main.createP_V(N);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		long[] delta = KEP_SC_5_1.Main.create_delta(N);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		long[] C_seq = KEP_SC_5_1.Main.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		// ��ͬ��KEP_SC_5_1��Dֵ
		long[] D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);

		long[] E_seq = KEP_SC_5_1_1.Main.create_E_seq(N);
		System.out.println("���ɵ�Dֵ");
		long[] D_seq_1 = KEP_SC_5_1_1.Main.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq_1);

		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.Main.print_seq(E_seq);

		System.out.println("L0,L1��ֵ");
		long[] L_seq = KEP_SC_5_1_1.Main.create_L_seq(A_seq, delta, P_V, E_seq);
		KEP_SC_5_1.Main.print_seq(L_seq);
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		long[] create_source = new long[N];
		boolean flag = false;
		long create_K = 0;
		long L = 0;
		long R = 0;
		long Key = 0;
		
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = Long.parseLong(str.split(" ")[i]);
			}

			KEP_SC_5_1.Main.print_seq(create_source);

			create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq_1, create_M_ni);
			System.out.println("���ɵ�K��ֵΪ" + create_K);
			L = KEP_SC_5_1_3.Main.create_L(L_seq, N);
			System.out.println("���ɵ�L��ֵ" + L);
			R = (long) (create_K % (Math.pow(2, L)));
			System.out.println("���ɵ�R��ֵ" + R);
			Key = (long) (create_K / (Math.pow(2, L)));
			if (R >= L_seq[1] && R + L_seq[0] < Math.pow(2, L)&&Key!=0) {
				flag = true;
			}
			
			if(flag){
			long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(Key + "");
			System.out.println("Keyֵ  "+Key+"  Hash(Key)"+encoderByMD5);
			// �ı�ݹ���ⷽ��
			System.out.println(
					KEP_SC_5_1_3.Main.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, Key, L, create_B_sum));
			System.out.println("---------------------------------");
			}else{
				System.out.println("�������������������"+create_source+",������һ��ѡȡ");
			}
		}

	}

	public static void Test_KEP_SC_5_1_4(Set<String> set, int N, String methodName) throws Exception {

		long[] A_seq = KEP_SC_5_1.Main.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		long[] M_W = KEP_SC_5_1.Main.createM_W(A_seq);
		KEP_SC_5_1.Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		long[] B_seq = KEP_SC_5_1.Main.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		long[] P_V = KEP_SC_5_1.Main.createP_V(N);
		KEP_SC_5_1.Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		long[] delta = KEP_SC_5_1.Main.create_delta(N);
		KEP_SC_5_1.Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		long[] C_seq = KEP_SC_5_1.Main.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = KEP_SC_5_1.Main.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		long[] D_seq = KEP_SC_5_1.Main.create_D_seq(create_M_ni, C_seq);
		
		long[] U_K_V = KEP_SC_5_1_2.Main.create_U_K_V(create_M_ni);
		System.out.println("u��ֵ"+U_K_V[0] + ", k��ֵ"+U_K_V[1]+ ", v��ֵ"+U_K_V[2]);
		
		long[] R_seq =KEP_SC_5_1_2.Main.create_R_seq(N,create_M_ni,U_K_V);
		System.out.println("���ɵ�R����");
		KEP_SC_5_1.Main.print_seq(R_seq);
		
		long[] E_seq = KEP_SC_5_1_1.Main.create_E_seq(N);
		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.Main.print_seq(E_seq);
		long w_ni = KEP_SC_5_1.Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		System.out.println("���ɵ�Dֵ");
		long[] D_seq_1=KEP_SC_5_1_4.Main.create_D_seq(D_seq, R_seq,E_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq_1);
		System.out.println("L0,L1��ֵ");
		long [] L_seq = KEP_SC_5_1_4.Main.create_L_seq(A_seq, delta, P_V,R_seq,E_seq,create_M_ni,U_K_V);
		KEP_SC_5_1.Main.print_seq(L_seq);
		
		boolean flag = false;
		long create_K = 0;
		long L = 0;
		long R = 0;
		long Key=0;
		long[] create_source = new long[N];
		for(String str:set){
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = Long.parseLong(str.split(" ")[i]);
			}

			KEP_SC_5_1.Main.print_seq(create_source);
			create_K = KEP_SC_5_1.Main.create_K(create_source, D_seq_1, create_M_ni);
			System.out.println("���ɵ�K��ֵΪ"+create_K);
			L = KEP_SC_5_1_3.Main.create_L(L_seq,N);
			System.out.println("���ɵ�L��ֵ"+L);
			R = (long) (create_K % (Math.pow(2, L)));
			System.out.println("���ɵ�R��ֵ"+R);
			Key=(long)(create_K/(Math.pow(2, L)));
			if (R >= L_seq[1] && R + L_seq[0] < Math.pow(2, L)&&Key!=0) {
				flag = true;
			}
			if(flag){
				long create_B_sum = KEP_SC_5_1.Main.create_B_sum(create_source, B_seq);
				System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
				String encoderByMD5 = KEP_SC_5_1.Main.encoderByMD5(Key + "");
				System.out.println("Keyֵ  "+Key+"  Hash(Key)"+encoderByMD5);
				// �ı�ݹ���ⷽ��
				System.out.println(
						KEP_SC_5_1_4.Main.get_K_ni(w_ni,M_W[0], P_V[1], P_V[0], create_M_ni,  Key,create_B_sum, L_seq,  U_K_V[0],
								U_K_V[1] , R,  L));
				System.out.println("---------------------------------");
				}else{
					System.out.println("�������������������"+KEP_SC_5_1.Main.toValue(create_source) +",������һ��ѡȡ");
					System.out.println();
				}
			
			
		}
		
		
	
	}

}