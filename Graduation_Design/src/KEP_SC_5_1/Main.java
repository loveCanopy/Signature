package KEP_SC_5_1;

import java.security.MessageDigest;
import java.util.Random;
import java.util.Scanner;

import sun.misc.BASE64Encoder;

/**
 * @author yujie
 * @version 5.1
 */

public class Main {

	public static long[] M_W = new long[2];
	public static long[] P_V = new long[2];
	public static long[] L_seq = new long[2];
	public static Random random = new Random();

	public static void main(String[] args) throws Exception {
		int Number = 6;
		long[] A_seq = new long[Number];
		long[] B_seq = new long[Number];
		long[] C_seq = new long[Number];
		long[] D_seq = new long[Number];
		long[] delta = new long[Number];
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
		long create_M_ni = create_M_ni(Number, C_seq);
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
		long[] create_source = create_source(Number);
		print_seq(create_source);
		long create_B_sum = create_B_sum(create_source, B_seq);
		long create_K = create_K(create_source, D_seq, create_M_ni);
		String encoderByMD5 = encoderByMD5(create_K + "");
		System.out.println("��" + create_K + "��Ϊ�Ự��Կ,��(" + create_B_sum + "," + encoderByMD5 + ")���͸�Bob");
		System.out.println("Bob��������");
		long w_ni = getW_ni(M_W[1], M_W[0]);
		System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		System.out.println(get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum,L_seq));
	}

	/**
	 * ��ӡ����
	 * 
	 * @param <T>
	 */
	public static void print_seq(long[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1)
				System.out.println(arr[i]);
			if (i != arr.length - 1)
				System.out.print(arr[i] + ",");
		}
	}

	public static String toValue(long[] arr) {

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
	 * �����ۼӺ�
	 * 
	 * @return sum
	 */
	public static long add_seq(long[] arr) {

		long sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}

	/**
	 * �����Լ��
	 */
	public static long gcd(long a, long b) {
		long temp = 0;
		while (a % b != 0) {
			temp = a % b;
			a = b;
			b = temp;
		}
		return b;
	}

	/**
	 * ���㱳�����е��ܶ�
	 */
	public static double get_DA(long[] arr) {
		long max = Long.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];
		}
		double temp = (double) (Math.log(max) / Math.log(2));
		return arr.length / temp;

	}

	/**
	 * ����A����
	 */
	public static long[] create_A_seq(int N) {
		boolean flag = true;
		long[] A_seq = new long[N];
		int min = (int) Math.pow(2, N);
		int max = (int) Math.pow(2, N / 0.9408);
		while (flag) {
			for (int i = 0; i < N; i++) {
				A_seq[i] = (long) (random.nextInt(max - min + 1) + min);
			}
			if (get_DA(A_seq) > 0.9408 && get_DA(A_seq) <= 1)
				flag = false;
		}
		return A_seq;
	}

	/**
	 * ����M,W ���� M����A���е��ۼӺͣ���gcd(M,W)=1
	 * 
	 * @return M_W
	 */
	public static long[] createM_W(long[] A_seq) {
		long sum_a = add_seq(A_seq);
		long M = sum_a + (long) Math.random() * 10 + 1;
		long R = (long) (Math.random() * 100) + 2;
		long W = (gcd(M, R));
		while (W != 1) {
			R++;
			W = (gcd(M, R));
		}
		M_W[0] = M;
		M_W[1] = R;
		return M_W;
	}

	/**
	 * ����V,P ����gcd(V,P)=1 �� (n+2)P<V<2��n��P
	 * 
	 * @param ���еĳ���
	 * @return P_V
	 */

	public static long[] createP_V(int n) {

		long V = 0, P = random.nextInt(8) + 2;
		for (V = (n + 2) * P; V < (2 << n - 1) * P; V++) {
			if (gcd(V, P) == 1 && V != P) {
				P_V[0] = P;
				P_V[1] = V;
				break;
			}

		}
		return P_V;

	}

	/**
	 * ����0����1 ����
	 * 
	 * @return delta
	 */
	public static long[] create_delta(int n) {
		long[] delta = new long[n];
		for (int i = 0; i < n; i++) {
			delta[i] = random.nextInt(2);
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
	public static long[] create_C_seq(int n, long[] A_seq, long[] delta, long[] P_V) {
		long[] C_seq = new long[n];
		long k = 0;
		for (int i = 0; i < delta.length; i++) {
			if (delta[i] == 0) {
				k = P_V[1] * A_seq[i] / P_V[0];
			} else if (delta[i] == 1) {
				k = (long) Math.ceil((double) P_V[1] * A_seq[i] / P_V[0]);

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
	public static long create_M_ni(int n, long[] C_seq) {
		long M_ni = 0;
		for (M_ni = (2 << n - 1) + 1; M_ni < (2 << n); M_ni++) {
			for (int i = 0; i < C_seq.length && gcd(M_ni, C_seq[i]) == 1; i++) {
				if (i == C_seq.length - 1) {
					return M_ni;
				}
			}

		}
		return M_ni;
	}

	/**
	 * @param M_ni
	 *            M'
	 * @param C_seq
	 * @return D ���� D=C(mod M')
	 */
	public static long[] create_D_seq(long M_ni, long[] C_seq) {
		long[] D_seq = new long[C_seq.length];
		for (int i = 0; i < C_seq.length; i++) {
			D_seq[i] = C_seq[i] % M_ni;
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
	public static long[] create_L_seq(long[] A_seq, long[] delta, long[] P_V) {
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
	public static long[] create_B_seq(long[] A_seq, long[] M_W) {
		long[] B_seq = new long[A_seq.length];
		for (int i = 0; i < A_seq.length; i++) {
			B_seq[i] = (M_W[1] * A_seq[i]) % M_W[0];
		}
		return B_seq;
	}

	/**
	 * @return source ������ɵ�0��1����
	 */
	public static long[] create_source(int n) {
		long[] source = new long[n];
		for (int i = 0; i < n; i++) {
			source[i] = random.nextInt(2);
		}
		return source;
	}

	/**
	 * @return ����Sb
	 */
	public static long create_B_sum(long[] create_source, long[] B_seq) {

		long Sb = 0;
		for (int i = 0; i < create_source.length; i++) {
			Sb += create_source[i] * B_seq[i];
		}
		return Sb;
	}

	/**
	 * @return K ��Ϊ�Ự��Կ
	 */
	public static long create_K(long[] source, long[] D_seq, long M_ni) {

		long K = 0;
		for (int i = 0; i < source.length; i++) {
			K += source[i] * D_seq[i];
		}
		return K % M_ni;

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
	 */
	public static long getW_ni(long W, long M) {

		long W1 = 0;
		while ((W1 * W) % M != 1) {
			W1++;
		}

		return W1;
	}

	/**
	 * �ݹ����K' ʹ�� H(K)=H(K')
	 */
	public static String get_K_ni(long W_ni, long M, long V, long P, long M_ni, long K, long Sb,long[] L_seq) throws Exception {
		long Sa = (W_ni * Sb) % M;
		long T = (V * Sa / P) % M_ni;
		System.out.println("T��ֵΪ" + T);
		int l0 = 0, l1 = 1;
		long K1 = 0l;
		int i = 1;
		while (l0 <= L_seq[0]) {
			K1 = (T - l0) % M_ni;
			
			l0 = l0 + 1;
			
			if (encoderByMD5(K1 + "").equals(encoderByMD5(K + ""))) {
				System.out.println("�Ƚϴ���" + i);
				return K1 + "";
			} else {
				i++;
			}

		}

		while (l1 <= L_seq[1]) {
			K1 = (T + l1) % M_ni;
			
			l1 = l1 + 1;
			if (encoderByMD5(K1 + "").equals(encoderByMD5(K + ""))) {
				System.out.println("�Ƚϴ���" + i);
				return K1 + "";
			} else {
				i++;
			}
		}

		return "������";
	}

}
