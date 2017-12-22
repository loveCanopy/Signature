package Design_Max;


import java.util.Random;
import java.math.BigInteger;
public class KEP_SC_5_1_3 {

	
	public static BigInteger[] M_W = new BigInteger[2];
	public static BigInteger[] P_V = new BigInteger[2];
	public static BigInteger[] L_seq = new BigInteger[2];
	public static Random random=new Random();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int Number = 500;
		BigInteger[] A_seq = new BigInteger[Number];
		BigInteger[] B_seq = new BigInteger[Number];
		BigInteger[] C_seq = new BigInteger[Number];
		BigInteger[] D_seq = new BigInteger[Number];
		BigInteger[] D_seq_1 = new BigInteger[Number];
		BigInteger[] delta = new BigInteger[Number];
		// TODO Auto-generated method stub
		System.out.println("密钥生成");
		Thread.sleep(5000);
		A_seq = KEP_SC_5_1.create_A_seq(Number);
		System.out.println("生成A序列");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A背包向量的密度");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("生成的M值,W值");
		M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("生成的B序列");
		B_seq = KEP_SC_5_1.create_B_seq(A_seq, M_W);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("生成的P值,V值");
		P_V = KEP_SC_5_1.createP_V(Number);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("随机生成的delta序列");
		delta = KEP_SC_5_1.create_delta(Number);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("生成的C值");
		C_seq = KEP_SC_5_1.create_C_seq(Number, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("生成的M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(Number, C_seq);
		System.out.println(create_M_ni);
		D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);

		BigInteger[] E_seq = KEP_SC_5_1_1.create_E_seq(Number);
		System.out.println("生成的D值");
		D_seq_1 = KEP_SC_5_1_1.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq_1);

		System.out.println("生成的E值");
		KEP_SC_5_1.print_seq(E_seq);

		System.out.println("L0,L1的值");
		L_seq = KEP_SC_5_1_1.create_L_seq(A_seq, delta, P_V, E_seq);
		KEP_SC_5_1.print_seq(L_seq);

		boolean flag = true;
		BigInteger create_K = BigInteger.ZERO;
		long L = 0l;
		BigInteger R = BigInteger.ZERO;
		BigInteger Key=BigInteger.ZERO;
		BigInteger[] create_source = new BigInteger[Number];
		while (flag) {
			create_source = KEP_SC_5_1.create_source(Number);
			KEP_SC_5_1.print_seq(create_source);
			create_K = KEP_SC_5_1.create_K(create_source, D_seq_1, create_M_ni);
			System.out.println("生成的K的值为"+create_K);
			L = create_L(L_seq, Number);
			System.out.println("生成的L的值"+L);
			long pow = (long)Math.pow(2, L);
			R = create_K.mod(new BigInteger(pow+""));
			System.out.println("生成的R的值"+R);
			Key=create_K.divide(new BigInteger(pow+""));
			if (R.longValue() >= L_seq[1].longValue() && R.longValue() + L_seq[0].longValue() < pow) {
				flag = false;
			}else{
				flag=true;
				continue;
			}
			if(Key.intValue()!=0){
				flag=false;
			}else{
				flag=true;
			}
		}
		BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
		System.out.println("公钥为(" + KEP_SC_5_1.toValue(B_seq) + "," + KEP_SC_5_1.toValue(D_seq_1) + ","
				+ KEP_SC_5_1.toValue(L_seq) + L +","+ create_M_ni + ")");
		System.out.println("私钥为(" + KEP_SC_5_1.toValue(M_W) + KEP_SC_5_1.toValue(P_V) + ")");
		Thread.sleep(1000);
		System.out.println("密钥交换");
		System.out.println("Alice操作部分");
		System.out.println("Alice获得Bob的公钥(" + KEP_SC_5_1.toValue(B_seq) + "," + KEP_SC_5_1.toValue(D_seq_1)
				+ "," + KEP_SC_5_1.toValue(L_seq) + L +","+ create_M_ni + ")");
		System.out.println("Alice随机选择(x1,x2,...,xn)并计算Sb和K的值");
		String encoderByMD5 = KEP_SC_5_1.encoderByMD5(Key + "");
		System.out.println("将" + Key + "作为会话密钥,将(" + create_B_sum + "," + encoderByMD5 + ")发送给Bob");
		System.out.println("Bob操作部分");
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' 满足 使得 (W*W')MOD M=1] --- " + w_ni);
		System.out.println(get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, Key, L, create_B_sum));
		
		
	}

	
	
	public static Long create_L(BigInteger[] L_seq, int Number) {

		int Max = Number;
		long temp = Math.max(L_seq[0].longValue(), L_seq[1].longValue());
		int min = (int)Math.ceil((Math.log(temp) / Math.log(2)));
		//long L = (long) (random.nextInt(Max - min) + min);
		long L=0l;
		//long L = (long)(random.nextInt(3) + min+1);
		//设置概率 
		int number=random.nextInt(100);
		if(number<95){
			L=min;
		}else if(number>=95){
			L=(long) (random.nextInt(Max - min) + min);
		}
		return L;
	}

	public static String get_K_ni(BigInteger W_ni, BigInteger M, BigInteger V, BigInteger P, BigInteger M_ni, BigInteger R, long L, BigInteger Sb)
			throws Exception {

		BigInteger Sa =W_ni.multiply(Sb).mod(M); 
		//(W_ni * Sb) % M;
		System.out.println("Sa的值为   "+Sa);
		BigInteger T = V.multiply(Sa).divide(P).mod(M_ni);
		System.out.println("T的值为" + T);
		BigInteger K1 =T.divide(new BigInteger((long)(Math.pow(2, L))+"")); 
		if (KEP_SC_5_1.encoderByMD5(K1 + "").equals(KEP_SC_5_1.encoderByMD5(R + ""))) {
			System.out.println("还原的Key的值  "+K1);
			return "还原密钥结束 ";

		}
		return "error";
	}
}
