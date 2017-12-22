package Design_Max;
/**
 * KEP_SC_5.1   ��������
 * */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

import sun.misc.BASE64Encoder;
public class KEP_SC_5_1 {
	public static BigInteger[] M_W = new BigInteger[2];
	public static BigInteger[] P_V = new BigInteger[2];
	public static BigInteger[] L_seq = new BigInteger[2];
	public static Random random=new Random();
	private static BigInteger M_max;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int Number = 200;
//		BigDecimal a=new BigDecimal(BigInteger.valueOf(178),3);
//		BigDecimal b=new BigDecimal(BigInteger.valueOf(3),3);
//		System.out.println(a.divide(b,5));
//		Thread.sleep(20000);
		BigInteger[] A_seq = new BigInteger[Number];
		BigInteger[] B_seq = new BigInteger[Number];
		BigInteger[] C_seq = new BigInteger[Number];
		BigInteger[] D_seq = new BigInteger[Number];
		BigInteger[] delta = new BigInteger[Number];
		// TODO Auto-generated method stub
		System.out.println("��Կ����");
		Thread.sleep(5000);
		A_seq = create_A_seq(Number);
		System.out.println("����A����");
		print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		M_W = createM_W(A_seq);
		print_seq(M_W);
		System.out.println("���ɵ�B����");
		B_seq = create_B_seq(A_seq, M_W);
		print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		P_V = createP_V(Number);
		print_seq(P_V);
		System.out.println("������ɵ�delta����");
		delta = create_delta(Number);
		print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		C_seq = create_C_seq(Number, A_seq, delta, P_V);
		print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = create_M_ni(Number, C_seq);
		System.out.println(create_M_ni);
		System.out.println("���ɵ�Dֵ");
		D_seq = create_D_seq(create_M_ni, C_seq);
		print_seq(create_D_seq(create_M_ni, C_seq));
		System.out.println("L0,L1��ֵ");
		L_seq = create_L_seq(A_seq, delta, P_V);
		print_seq(L_seq);
		System.out.println("��ԿΪ(" + toValue(B_seq) + "," + toValue(D_seq) + "," + create_M_ni + ")");
		System.out
				.println("˽ԿΪ(" + toValue(A_seq) + toValue(delta) + toValue(M_W) + toValue(P_V) + toValue(L_seq) + ")");
		Thread.sleep(1000);
		System.out.println("��Կ����");
		System.out.println("Alice��������");
		System.out.println("Alice���Bob�Ĺ�Կ(" + toValue(B_seq) + "," + toValue(D_seq) + "," + create_M_ni + ")");
		System.out.println("Alice���ѡ��(x1,x2,...,xn)������Sb��K��ֵ");
		BigInteger[] create_source = create_source(Number);
		print_seq(create_source);
		BigInteger create_B_sum = create_B_sum(create_source, B_seq);
		BigInteger create_K = create_K(create_source, D_seq, create_M_ni);
		String encoderByMD5 = encoderByMD5(create_K + "");
		System.out.println("��" + create_K + "��Ϊ�Ự��Կ,��(" + create_B_sum + "," + encoderByMD5 + ")���͸�Bob");
		System.out.println("Bob��������");
		BigInteger w_ni = get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		System.out.println(get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum,L_seq));
		System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
	}	
	
	
	public static String toValue(BigInteger[] arr) {

		String str = "";
		for (int i = 0; i < arr.length; i++) {
			if (i == 0)
				str = "[" + arr[i] + ",";
			if (i != arr.length - 1 && i != 0)
				str += arr[i] + ",";
			if (i == arr.length - 1)
				str += arr[i] + "]";
		}
		return str;

	}
	/**
	 * ��ӡ����
	 * 
	 * @param <T>
	 */
	public static void print_seq(BigInteger[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1)
				System.out.println(arr[i]);
			if (i != arr.length - 1)
				System.out.print(arr[i] + ",");
		}
	}
	
	
	
	
	/**
	 * ���㱳�����е��ܶ�
	 */
	public static double get_DA(BigInteger[] arr) {
		BigInteger max = BigInteger.ONE;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].compareTo(max)>0)
				max = arr[i];
		}
		//
		double temp=0.0;
		if(max.bitLength()<64){
		 temp = (double) (Math.log(max.longValueExact()) / Math.log(2));
		}else{
		 temp = Big_log(max);
		}
		return (double)(arr.length / temp);
	}

	/**
	 * ����A����
	 */
	public static BigInteger[] create_A_seq(int N) {
		boolean flag = true;
		BigInteger[] A_seq = new BigInteger[N];
		if(N<16){
			int min = (int) Math.pow(2, N);
			int max = (int) Math.pow(2, N / 0.9408);
			while (flag) {
				for (int i = 0; i < N; i++) {
					A_seq[i] =BigInteger.valueOf((random.nextInt(max - min + 1) + min));
				}
				if (get_DA(A_seq) > 0.9408 && get_DA(A_seq) <= 1)
					flag = false;
			}
		}else{
			int bit=(int)(N/0.9408-N);
			int numbit=Big_log(new BigInteger("2").
					pow(bit).subtract(new BigInteger("1")).multiply(new BigInteger("2").pow(N)));
			while (flag) {
				for (int i = 0; i < N; i++) {
					A_seq[i] =new BigInteger(numbit, random).add(new BigInteger("2").pow(N));
				}
				if (get_DA(A_seq) > 0.9408 && get_DA(A_seq) <= 1)
					flag = false;
			}
			
		}
		
		return A_seq;
	}
	/**
	 * ���������2�Ķ���
	 * */
	public static int Big_log(BigInteger max){
		int big_log=0;
		BigInteger max_temp=max.divide(new BigInteger("2"));
		while(max_temp.compareTo(new BigInteger("0"))>0){
			max_temp=max_temp.divide(new BigInteger("2"));
			big_log++;
		}
		return big_log;
	}
	/**
	 * �����ۼӺ�
	 * 
	 * @return sum
	 */
	public static BigInteger add_seq(BigInteger[] arr) {

		BigInteger sum = new BigInteger("0");
		for (int i = 0; i < arr.length; i++) {
			sum=sum.add(arr[i]);
		}
		return sum;
	}
	
	/**
	 * �����Լ��
	 */
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		return a.gcd(b);
	}
	/**
	 * ����M,W ���� M����A���е��ۼӺͣ���gcd(M,W)=1
	 * 
	 * @return M_W
	 * @throws Exception 
	 */
	public static BigInteger[] createM_W(BigInteger[] A_seq) throws Exception {
		BigInteger[] M_W=new BigInteger[2];
		BigInteger sum_a = add_seq(A_seq);
		BigInteger M = sum_a.add(BigInteger.valueOf((long) Math.random() * 10 + 1));
		BigInteger W=new BigInteger("20");
		while(gcd(M, W).compareTo(BigInteger.ONE)!=0){
			W=W.add(BigInteger.ONE);
		}
		M_W[0]=M;
		M_W[1]=W;
		return M_W;
	}
	/**
	 * ����V,P ����gcd(V,P)=1 �� (n+2)P<V<2��n��P
	 * 
	 * @param ���еĳ���
	 * @return P_V
	 * @throws Exception 
	 */
	public static BigInteger[] createP_V(int n)  {
		BigInteger[] P_V=new BigInteger[2];
		BigInteger P = BigInteger.valueOf(random.nextInt(8) + 2);
		BigInteger V = BigInteger.valueOf((n + 2)).multiply(P);
		while(V.compareTo((BigInteger.valueOf(2).pow(n-1)).multiply(P))<0) {
			
			if (gcd(V, P).compareTo(BigInteger.ONE)==0 && V.compareTo(P)!=0) {
				P_V[0] = P;
				P_V[1] = V;
				break;
			}
			V=V.add(new BigInteger("1"));
		}
		return P_V;

	}
	
	
	/**
	 * ����0����1 ����
	 * 
	 * @return delta
	 */
	public static BigInteger[] create_delta(int n) {
		BigInteger[] delta = new BigInteger[n];
		for (int i = 0; i < n; i++) {
			delta[i] = new BigInteger(1, random);
		}
		return delta;
	}
	/**
	 * ����C���� C=V��A/P ��Qi=0ʱ,C����ȡ�� ��Qi=1ʱ,C����ȡ��
	 * 
	 * @param n
	 *            ����
	 * @param A_seq
	 *            A����
	 * @param delta
	 *            delta����
	 * @param P_V
	 *            ��������������PVֵ
	 * @return C_seq C_seq=[V*A_seq/P] ��delta
	 */
	public static BigInteger[] create_C_seq(int n, BigInteger[] A_seq, BigInteger[] delta, BigInteger[] P_V) {
		BigInteger[] C_seq = new BigInteger[n];
		BigInteger k = BigInteger.ZERO;
		for (int i = 0; i < delta.length; i++) {
			if (delta[i].compareTo(BigInteger.ZERO)==0) {
				k = P_V[1].multiply(A_seq[i]).divide(P_V[0]);
			} else if (delta[i].compareTo(BigInteger.ONE)==0) {
				BigInteger[] divideAndRemainder = P_V[1].multiply(A_seq[i]).divideAndRemainder(P_V[0]);
				if(divideAndRemainder[1].compareTo(BigInteger.ZERO)>0){
					k=divideAndRemainder[0].add(BigInteger.ONE);
				}else{
					k=divideAndRemainder[0];
				}
				
			}
			C_seq[i] = k;
		}

		return C_seq;
	}
	/**
	 * 
	 * @param n
	 *            ���г���
	 * @param C_seq
	 *            �������ɵ�C����
	 * @return M_ni �������� 2<<n <M'< 2<<(n+1) �� gcd(ci,M')=1
	 */
	public static BigInteger create_M_ni(int n, BigInteger[] C_seq) {
		
		BigInteger M_min=(new BigInteger("2")).pow(n).add(BigInteger.ONE);
		BigInteger M_ni =M_min;
		BigInteger M_max=(new BigInteger("2")).pow(n+1);
		while( M_ni.compareTo(M_max)<0) {
			for (int i = 0; i < C_seq.length && gcd(M_ni, C_seq[i]).compareTo(BigInteger.ONE)==0; i++) {
				if (i == C_seq.length - 1) {
					return M_ni;
				}
			}
			M_ni=M_ni.add(BigInteger.ONE);
		}
		return M_ni;
	}
	
	/**
	 * @param M_ni
	 *            M'
	 * @param C_seq
	 * @return D ���� D=C(mod M')
	 */
	public static BigInteger[] create_D_seq(BigInteger M_ni, BigInteger[] C_seq) {
		BigInteger[] D_seq = new BigInteger[C_seq.length];
		for (int i = 0; i < C_seq.length; i++) {
			D_seq[i] = C_seq[i].mod(M_ni);
		}
		return D_seq;
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
	public static BigInteger[] create_L_seq(BigInteger[] A_seq, BigInteger[] delta, BigInteger[] P_V) {
		BigInteger[] L_seq=new BigInteger[2];
		BigInteger P = P_V[0];
		BigInteger V = P_V[1];
		
		BigDecimal temp_l0 = new BigDecimal(0.0);
		BigInteger temp_l0_1=BigInteger.ZERO;
		BigDecimal temp_l0_2=new BigDecimal(0.0);
		
		for (int i = 0; i < A_seq.length; i++) {
			temp_l0_1=(V.multiply(A_seq[i])).mod(P);
			//System.out.print("temp_l0_1 "+temp_l0_1+",");
			temp_l0_2=(new BigDecimal(temp_l0_1.multiply(BigInteger.ONE.subtract(delta[i])),5)
					).divide(new BigDecimal(P,5),5);
			//System.out.println("temp_l0_2 "+temp_l0_2);
			temp_l0 =temp_l0_2.add(temp_l0);
		}
		BigInteger L0 =temp_l0.toBigInteger().add(BigInteger.ONE);
		BigDecimal temp_l1 =  new BigDecimal(0.0);
		BigInteger temp_l1_1= BigInteger.ZERO;
		BigDecimal temp_l1_2= new BigDecimal(0.0);
		for (int i = 0; i < A_seq.length; i++) {
			// temp_l1+=(double)(delta[i]*(P-(V*A_seq[i]%P))%P)/P;
			//temp_l1_1=((P - ((V * A_seq[i]) % P)) % P);
			temp_l1_1=P.subtract(((V.multiply(A_seq[i]).mod(P))).mod(P));
			//System.out.print("temp_l1_1 "+temp_l1_1+",");
			temp_l1_2=new BigDecimal(temp_l1_1.multiply(delta[i]),5).divide(new BigDecimal(P,5),5);
			//System.out.println("temp_l1_2 "+temp_l1_2);
			temp_l1 =temp_l1_2.add(temp_l1);
		}
		//System.out.println("L0 "+temp_l0.toPlainString() +"L1 "+temp_l1.toPlainString());
		//System.out.println("----------------");
		BigInteger L1 =temp_l1.toBigInteger().add(BigInteger.ONE);
		L_seq[0] = L0;
		L_seq[1] = L1;
		return L_seq;
	}
	/**
	 * (B,D,M')Ϊ��Կ (A_seq,delta,M,W,P,V,L0,L1)Ϊ˽Կ
	 */

	// Alice���Bob�Ĺ�Կ(B,D,M'),���ѡ��(x1,x2,x3,...,xn) 0,1���У�����Sb=xi*bi���ۼӺ�
	// K=xi*di���ۼӺ� mod(M')

	/**
	 * @return B_seq ����B���� B=W*A(mod M)
	 */
	public static BigInteger[] create_B_seq(BigInteger[] A_seq, BigInteger[] M_W) {
		BigInteger[] B_seq = new BigInteger[A_seq.length];
		for (int i = 0; i < A_seq.length; i++) {
			B_seq[i] = M_W[1].multiply(A_seq[i]).mod(M_W[0]);
		}
		return B_seq;
	}
	
	
	/**
	 * @return source ������ɵ�0��1����
	 */
	public static BigInteger[] create_source(int n) {
		BigInteger[] source = new BigInteger[n];
		for (int i = 0; i < n; i++) {
			source[i] = new BigInteger(1, random);
		}
		return source;
	}
	
	/**
	 * @return ����Sb
	 */
	public static BigInteger create_B_sum(BigInteger[] create_source, BigInteger[] B_seq) {

		BigInteger Sb = BigInteger.ZERO;
		for (int i = 0; i < create_source.length; i++) {
			Sb=B_seq[i].multiply(create_source[i]).add(Sb);
		}
		return Sb;
	}
	
	/**
	 * @return K ��Ϊ�Ự��Կ
	 */
	public static BigInteger create_K(BigInteger[] source, BigInteger[] D_seq, BigInteger M_ni) {

		BigInteger K = BigInteger.ZERO;
		for (int i = 0; i < source.length; i++) {
			K=D_seq[i].multiply(source[i]).add(K);
		}
		return K.mod(M_ni);

	}
	
	
	/**
	 * �ҵ�Hash���� Alice����SB,H(k))���͸�Bob
	 * 
	 * @return MD5���ܺ��ַ���
	 */
	public static String encoderByMD5(String str) throws Exception {
		// ѡ��MD5
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		// ���ܺ���ַ���
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;

	}
	
	
	/**
	 * @return Ѱ��W' ʹ�� (W*W')MOD M=1
	 * @throws Exception 
	 */
	public static BigInteger get_w_ni(BigInteger W,BigInteger M){
		return W.modInverse(M);
	}
	
	
	
	/**
	 * �ݹ����K' ʹ�� H(K)=H(K')
	 */
	public static String get_K_ni(BigInteger W_ni, BigInteger M, BigInteger V, BigInteger P, BigInteger M_ni, BigInteger K, BigInteger Sb,BigInteger[] L_seq) throws Exception {
		BigInteger Sa =W_ni.multiply(Sb).mod(M); 
				//(W_ni * Sb) % M;
		System.out.println("Sa��ֵΪ   "+Sa);
		BigInteger T = V.multiply(Sa).divide(P).mod(M_ni);
				//(V * Sa / P) % M_ni;
		System.out.println("T��ֵΪ" + T);
		BigInteger l0 = BigInteger.ZERO, l1 = BigInteger.ONE;
		BigInteger K1 = BigInteger.ZERO;
		int i = 1;
		while (l0.compareTo(L_seq[0])<=0) {
			K1 = (T.subtract(l0)).mod(M_ni);
			l0 = l0.add(BigInteger.ONE);
			if (encoderByMD5(K1 + "").equals(encoderByMD5(K + ""))) {
				System.out.println("�Ƚϴ���" + i);
				return K1 + "";
			} else {
				i++;
			}

		}

		while (l1.compareTo(L_seq[1])<=0) {
			K1 = (T.add(l1)).mod(M_ni);
			
			l1 = l1.add(BigInteger.ONE);
			if (encoderByMD5(K1 + "").equals(encoderByMD5(K + ""))) {
				System.out.println("�Ƚϴ���:" + i);
				return K1 + "";
			} else {
				i++;
			}
		}

		return "������";
	}
	
	
	
}
