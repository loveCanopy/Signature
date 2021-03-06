package KEP_SC_5_1_3;

import java.util.Random;

/**
 * 在KEP_SC_5_1_1的基础上 进行操作 忽略掉低位
 * 
 */

public class Main {

	public static Random random = KEP_SC_5_1.Main.random;
	public static long[] L_seq = new long[2];
	public static long[] M_W = new long[2];
	public static long[] P_V = new long[2];

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int Number = 20;
		long[] A_seq = new long[Number];
		long[] B_seq = new long[Number];
		long[] C_seq = new long[Number];
		long[] D_seq = new long[Number];
		long[] D_seq_1 = new long[Number];
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

		long[] E_seq = KEP_SC_5_1_1.Main.create_E_seq(Number);
		System.out.println("生成的D值");
		D_seq_1 = KEP_SC_5_1_1.Main.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.Main.print_seq(D_seq_1);

		System.out.println("生成的E值");
		KEP_SC_5_1.Main.print_seq(E_seq);

		System.out.println("L0,L1的值");
		L_seq = KEP_SC_5_1_1.Main.create_L_seq(A_seq, delta, P_V, E_seq);
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
			L = create_L(L_seq, Number);
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
		System.out.println(get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, Key, L, create_B_sum));

	}

	public static Long create_L(long[] L_seq, int Number) {

		int Max = Number;
		long temp = Math.max(L_seq[0], L_seq[1]);
		int min = (int) (Math.log(temp) / Math.log(2))+1;
		long L = (long) (random.nextInt(Max - min) + min);
		return L;
	}

	public static String get_K_ni(long W_ni, long M, long V, long P, long M_ni, long R, long L, long Sb)
			throws Exception {

		long Sa = (W_ni * Sb) % M;
		long T = (V * Sa / P) % M_ni;
		System.out.println("T的值为" + T);
		long K1 = (long) ((long) T / Math.pow(2, L));
		
		if (KEP_SC_5_1.Main.encoderByMD5(K1 + "").equals(KEP_SC_5_1.Main.encoderByMD5(R + ""))) {
			System.out.println("还原的Key的值  "+K1);
			return "还原密钥结束 ";

		}
		return "error";
	}

}
