package TOOL_MAX;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Design_Max.KEP_SC_5_1;
import Design_Max.KEP_SC_5_1_1;
import Design_Max.KEP_SC_5_1_2;
import Design_Max.KEP_SC_5_1_3;
import Design_Max.KEP_SC_5_1_4;

public class Tool {

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

		BigInteger[] A_seq = KEP_SC_5_1.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		BigInteger[] M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("���ɵ�B����");
		BigInteger[] B_seq = KEP_SC_5_1.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		BigInteger[] P_V = KEP_SC_5_1.createP_V(N);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		BigInteger[] delta = KEP_SC_5_1.create_delta(N);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		BigInteger[] C_seq = KEP_SC_5_1.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);
		System.out.println("���ɵ�Dֵ");
		BigInteger[] D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);
		KEP_SC_5_1.print_seq(D_seq);
		System.out.println("L0,L1��ֵ");
		BigInteger[] L_seq = KEP_SC_5_1.create_L_seq(A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(L_seq);
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		BigInteger[] create_source = new BigInteger[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = new BigInteger(str.split(" ")[i]);
			}
			KEP_SC_5_1.print_seq(create_source);
			BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
			BigInteger create_K = KEP_SC_5_1.create_K(create_source, D_seq, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.encoderByMD5(create_K + "");
			System.out.println(
					KEP_SC_5_1.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum, L_seq));
			System.out.println("---------------------------------");
		}

	}

	public static void Test_KEP_SC_5_1_1(Set<String> set, int N, String methodName) throws Exception {
		BigInteger[] A_seq = KEP_SC_5_1.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		BigInteger[] M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("���ɵ�B����");
		BigInteger[] B_seq = KEP_SC_5_1.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		BigInteger[] P_V = KEP_SC_5_1.createP_V(N);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		BigInteger[] delta = KEP_SC_5_1.create_delta(N);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		BigInteger[] C_seq = KEP_SC_5_1.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		// ��ͬ��KEP_SC_5_1��Dֵ
		BigInteger[] D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);
		BigInteger[] E_seq = KEP_SC_5_1_1.create_E_seq(N);
		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.print_seq(E_seq);
		System.out.println("���ɵ�Dֵ");
		BigInteger[] D_seq1 = KEP_SC_5_1_1.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq1);

		// ��ͬ��KEP_SC_5_1��Dֵ
		System.out.println("L0,L1��ֵ");
		BigInteger[] L_seq = KEP_SC_5_1_1.create_L_seq(A_seq, delta, P_V, E_seq);
		KEP_SC_5_1.print_seq(L_seq);
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		BigInteger[] create_source = new BigInteger[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = new BigInteger(str.split(" ")[i]);
			}
			KEP_SC_5_1.print_seq(create_source);
			BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
			BigInteger create_K = KEP_SC_5_1.create_K(create_source, D_seq1, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.encoderByMD5(create_K + "");
			System.out.println(
					KEP_SC_5_1.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum, L_seq));
			System.out.println("---------------------------------");
		}
	}

	public static void Test_KEP_SC_5_1_2(Set<String> set, int N, String methodName) throws Exception {

		BigInteger[] A_seq = KEP_SC_5_1.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		BigInteger[] M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("���ɵ�B����");
		BigInteger[] B_seq = KEP_SC_5_1.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		BigInteger[] P_V = KEP_SC_5_1.createP_V(N);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		BigInteger[] delta = KEP_SC_5_1.create_delta(N);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		BigInteger[] C_seq = KEP_SC_5_1.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		// ��ͬ��KEP_SC_5_1��Dֵ
		BigInteger[] D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);
		BigInteger[] U_K_V = KEP_SC_5_1_2.create_U_K_V(create_M_ni);
		BigInteger[] R_seq = KEP_SC_5_1_2.create_R_seq(N, U_K_V);
		System.out.println("���ɵ�Rֵ");
		KEP_SC_5_1.print_seq(R_seq);
		System.out.println("���ɵ�Dֵ");
		BigInteger[] D_seq1 = KEP_SC_5_1_2.create_D_seq(D_seq, R_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq1);

		// ��ͬ��KEP_SC_5_1��Lֵ
		System.out.println("L0,L1��ֵ");
		BigInteger[] L_seq = KEP_SC_5_1_2.create_L_seq(A_seq, delta, P_V, R_seq,create_M_ni,U_K_V);
		KEP_SC_5_1.print_seq(L_seq);
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		BigInteger[] create_source = new BigInteger[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = new BigInteger(str.split(" ")[i]);
			}
			KEP_SC_5_1.print_seq(create_source);
			BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
			BigInteger create_K = KEP_SC_5_1.create_K(create_source, D_seq1, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.encoderByMD5(create_K + "");
			// �ı�ݹ���ⷽ��
			System.out.println(KEP_SC_5_1_2.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K,
					create_B_sum, L_seq, U_K_V[0], U_K_V[1]));
			System.out.println("---------------------------------");

		}
	}

	public static void Test_KEP_SC_5_1_3(Set<String> set, int N, String methodName) throws Exception {
		BigInteger[] A_seq = KEP_SC_5_1.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		BigInteger[] M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("���ɵ�B����");
		BigInteger[] B_seq = KEP_SC_5_1.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		BigInteger[] P_V = KEP_SC_5_1.createP_V(N);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		BigInteger[] delta = KEP_SC_5_1.create_delta(N);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		BigInteger[] C_seq = KEP_SC_5_1.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		// ��ͬ��KEP_SC_5_1��Dֵ
		BigInteger[] D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);

		BigInteger[] E_seq = KEP_SC_5_1_1.create_E_seq(N);
		System.out.println("���ɵ�Dֵ");
		BigInteger[] D_seq_1 = KEP_SC_5_1_1.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq_1);

		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.print_seq(E_seq);

		System.out.println("L0,L1��ֵ");
		BigInteger[] L_seq = KEP_SC_5_1_1.create_L_seq(A_seq, delta, P_V, E_seq);
		KEP_SC_5_1.print_seq(L_seq);
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		BigInteger[] create_source = new BigInteger[N];
		boolean flag = false;
		BigInteger create_K = BigInteger.ZERO;
		long L = 0;
		BigInteger R = BigInteger.ZERO;
		BigInteger Key = BigInteger.ZERO;
		
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = new BigInteger(str.split(" ")[i]);
			}

			KEP_SC_5_1.print_seq(create_source);

			create_K = KEP_SC_5_1.create_K(create_source, D_seq_1, create_M_ni);
			System.out.println("���ɵ�K��ֵΪ" + create_K);
			L = KEP_SC_5_1_3.create_L(L_seq, N);
			System.out.println("���ɵ�L��ֵ" + L);
			
			long pow = (long)Math.pow(2, L);
			R = create_K.mod(new BigInteger(pow+""));
			System.out.println("���ɵ�R��ֵ"+R);
			Key=create_K.divide(new BigInteger(pow+""));
			
			if (R.longValue() >= L_seq[1].longValue() && R.longValue() + L_seq[0].longValue() < Math.pow(2, L)&&Key.longValue()!=0) {
				flag = true;
			}
			
			if(flag){
			BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = KEP_SC_5_1.encoderByMD5(Key + "");
			System.out.println("Keyֵ  "+Key+"  Hash(Key)"+encoderByMD5);
			// �ı�ݹ���ⷽ��
			System.out.println(
					KEP_SC_5_1_3.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, Key, L, create_B_sum));
			System.out.println("---------------------------------");
			}else{
				System.out.println("�������������������"+create_source+",������һ��ѡȡ");
			}
		}

	}

	public static void Test_KEP_SC_5_1_4(Set<String> set, int N, String methodName) throws Exception {

		BigInteger[] A_seq = KEP_SC_5_1.create_A_seq(N);
		System.out.println("����A����");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		BigInteger[] M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("���ɵ�B����");
		BigInteger[] B_seq = KEP_SC_5_1.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		BigInteger[] P_V = KEP_SC_5_1.createP_V(N);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		BigInteger[] delta = KEP_SC_5_1.create_delta(N);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		BigInteger[] C_seq = KEP_SC_5_1.create_C_seq(N, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);

		BigInteger[] D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);
		
		BigInteger[] U_K_V = KEP_SC_5_1_2.create_U_K_V(create_M_ni);
		System.out.println("u��ֵ"+U_K_V[0] + ", k��ֵ"+U_K_V[1]+ ", v��ֵ"+U_K_V[2]);
		
		BigInteger[] R_seq =KEP_SC_5_1_2.create_R_seq(N,U_K_V);
		System.out.println("���ɵ�R����");
		KEP_SC_5_1.print_seq(R_seq);
		
		BigInteger[] E_seq = KEP_SC_5_1_1.create_E_seq(N);
		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.print_seq(E_seq);
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		System.out.println("���ɵ�Dֵ");
		BigInteger[] D_seq_1=KEP_SC_5_1_4.create_D_seq(D_seq, R_seq,E_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq_1);
		System.out.println("L0,L1��ֵ");
		BigInteger [] L_seq = KEP_SC_5_1_4.create_L_seq(A_seq, delta, P_V,R_seq,E_seq,create_M_ni,U_K_V);
		KEP_SC_5_1.print_seq(L_seq);
		
		boolean flag = false;
		BigInteger create_K = BigInteger.ZERO;
		long L = 0;
		BigInteger R = BigInteger.ZERO;
		BigInteger Key=BigInteger.ZERO;
		BigInteger[] create_source = new BigInteger[N];
		for(String str:set){
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = new BigInteger(str.split(" ")[i]);
			}

			KEP_SC_5_1.print_seq(create_source);
			create_K = KEP_SC_5_1.create_K(create_source, D_seq_1, create_M_ni);
			System.out.println("���ɵ�K��ֵΪ"+create_K);
			L = KEP_SC_5_1_3.create_L(L_seq, N);
			System.out.println("���ɵ�L��ֵ" + L);
			
			long pow = (long)Math.pow(2, L);
			R = create_K.mod(new BigInteger(pow+""));
			System.out.println("���ɵ�R��ֵ"+R);
			Key=create_K.divide(new BigInteger(pow+""));
			
			if (R.longValue() >= L_seq[1].longValue() && R.longValue() + L_seq[0].longValue() < Math.pow(2, L)&&Key.longValue()!=0) {
				flag = true;
			}
			if(flag){
				BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
				System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
				String encoderByMD5 = KEP_SC_5_1.encoderByMD5(Key + "");
				System.out.println("Keyֵ  "+Key+"  Hash(Key)"+encoderByMD5);
				// �ı�ݹ���ⷽ��
				System.out.println(
						KEP_SC_5_1_4.get_K_ni(w_ni,M_W[0], P_V[1], P_V[0], create_M_ni,  Key,create_B_sum, L_seq,  U_K_V[0],
								U_K_V[1] , R,  L));
				System.out.println("---------------------------------");
				}else{
					System.out.println("�������������������"+KEP_SC_5_1.toValue(create_source) +",������һ��ѡȡ");
					System.out.println();
				}
			
			
		}
		
		
	
	}

}