package Tool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import KEP_SC_5_1.Main;

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
	}

	public static void Test_KEP_SC_5_1(Set<String> set, int N, String methodName) throws Exception {

		long[] A_seq = Main.create_A_seq(N);
		System.out.println("����A����");
		Main.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(Main.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		long[] M_W = Main.createM_W(A_seq);
		Main.print_seq(M_W);
		System.out.println("���ɵ�B����");
		long[] B_seq = Main.create_B_seq(A_seq, M_W);
		Main.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		long[] P_V = Main.createP_V(N);
		Main.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		long[] delta = Main.create_delta(N);
		Main.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		long[] C_seq = Main.create_C_seq(N, A_seq, delta, P_V);
		Main.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		long create_M_ni = Main.create_M_ni(N, C_seq);
		System.out.println(create_M_ni);
		System.out.println("���ɵ�Dֵ");
		long[] D_seq = Main.create_D_seq(create_M_ni, C_seq);
		Main.print_seq(D_seq);
		System.out.println("L0,L1��ֵ");
		long[] L_seq = Main.create_L_seq(A_seq, delta, P_V);
		Main.print_seq(L_seq);
		long w_ni = Main.getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		Thread.sleep(3000);
		long[] create_source = new long[N];
		for (String str : set) {
			for (int i = 0; i < str.split(" ").length; i++) {
				create_source[i] = Long.parseLong(str.split(" ")[i]);
			}
			Main.print_seq(create_source);
			long create_B_sum = Main.create_B_sum(create_source, B_seq);
			long create_K = Main.create_K(create_source, D_seq, create_M_ni);
			System.out.println("SBֵ" + create_B_sum + ", Kֵ" + create_K);
			String encoderByMD5 = Main.encoderByMD5(create_K + "");
			System.out.println(Main.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum));
			System.out.println("---------------------------------");
		}

	}

}
