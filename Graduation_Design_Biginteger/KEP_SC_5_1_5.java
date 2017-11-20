package Design;

import java.math.BigInteger;
import java.util.Random;

/**
 * ������������ӷ�������B����
 * B=P*ci + ai P>ai���ۼӺ�
 * 
 * ������������˷�������B����
 * B=(P*ci+1)*ai 
 * */

public class KEP_SC_5_1_5 {

	public static BigInteger[] M_W = new BigInteger[2];
	public static BigInteger[] P_V = new BigInteger[2];
	public static BigInteger[] L_seq = new BigInteger[2];
	public static Random random=new Random();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int Number = 20;
		BigInteger[] A_seq = new BigInteger[Number];
		BigInteger[] B_seq = new BigInteger[Number];
		BigInteger[] C_seq = new BigInteger[Number];
		BigInteger[] D_seq = new BigInteger[Number];
		BigInteger[] D_seq_1 = new BigInteger[Number];
		BigInteger[] delta = new BigInteger[Number];
		// TODO Auto-generated method stub
		System.out.println("��Կ����");
		Thread.sleep(5000);
		A_seq = KEP_SC_5_1.create_A_seq(Number);
		System.out.println("����A����");
		KEP_SC_5_1.print_seq(A_seq);
		System.out.println("A�����������ܶ�");
		System.out.println(KEP_SC_5_1.get_DA(A_seq));
		System.out.println("���ɵ�Mֵ,Wֵ");
		M_W = KEP_SC_5_1.createM_W(A_seq);
		KEP_SC_5_1.print_seq(M_W);
		System.out.println("���ɵ��������random_seq");
		//long[] P_Q_M_E_D = RSA.get_P_Q_M_E_D();
		BigInteger[] random_seq=create_random_seq(Number);
		KEP_SC_5_1.print_seq(random_seq);
		
		System.out.println("���ɵ�B����");
		B_seq = create_B_seq1(A_seq,M_W,random_seq);
		KEP_SC_5_1.print_seq(B_seq);
		System.out.println("���ɵ�Pֵ,Vֵ");
		P_V = KEP_SC_5_1.createP_V(Number);
		KEP_SC_5_1.print_seq(P_V);
		System.out.println("������ɵ�delta����");
		delta = KEP_SC_5_1.create_delta(Number);
		KEP_SC_5_1.print_seq(delta);
		System.out.println("���ɵ�Cֵ");
		C_seq = KEP_SC_5_1.create_C_seq(Number, A_seq, delta, P_V);
		KEP_SC_5_1.print_seq(C_seq);
		System.out.println("���ɵ�M'");
		BigInteger create_M_ni = KEP_SC_5_1.create_M_ni(Number, C_seq);
		System.out.println(create_M_ni);
		D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);

		BigInteger[] U_K_V = KEP_SC_5_1_2.create_U_K_V(create_M_ni);
		System.out.println("u��ֵ"+U_K_V[0] + ", k��ֵ"+U_K_V[1]+ ", v��ֵ"+U_K_V[2]);
		
		BigInteger[] R_seq =KEP_SC_5_1_2.create_R_seq(Number,U_K_V);
		System.out.println("���ɵ�R����");
		KEP_SC_5_1.print_seq(R_seq);
		
		
		BigInteger[] E_seq = KEP_SC_5_1_1.create_E_seq(Number);
		System.out.println("���ɵ�Dֵ");
		D_seq_1 = KEP_SC_5_1_1.create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq_1);

		System.out.println("���ɵ�Eֵ");
		KEP_SC_5_1.print_seq(E_seq);

		System.out.println("L0,L1��ֵ");
		L_seq = KEP_SC_5_1_4.create_L_seq(A_seq, delta, P_V,R_seq,E_seq,create_M_ni,U_K_V);
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
			System.out.println("���ɵ�K��ֵΪ"+create_K);
			L =  KEP_SC_5_1_3.create_L(L_seq, Number);
			System.out.println("���ɵ�L��ֵ"+L);
			long pow = (long)Math.pow(2, L);
			R = create_K.mod(new BigInteger(pow+""));
			System.out.println("���ɵ�R��ֵ"+R);
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
		System.out.println("��ԿΪ(" + KEP_SC_5_1.toValue(B_seq) + "," + KEP_SC_5_1.toValue(D_seq_1) + ","
				+ KEP_SC_5_1.toValue(L_seq) + L +","+ create_M_ni + ")");
		System.out.println("˽ԿΪ(" + KEP_SC_5_1.toValue(M_W) + KEP_SC_5_1.toValue(P_V) + ")");
		Thread.sleep(1000);
		System.out.println("��Կ����");
		System.out.println("Alice��������");
		System.out.println("Alice���Bob�Ĺ�Կ(" + KEP_SC_5_1.toValue(B_seq) + "," + KEP_SC_5_1.toValue(D_seq_1)
				+ "," + KEP_SC_5_1.toValue(L_seq) + L +","+ create_M_ni + ")");
		System.out.println("Alice���ѡ��(x1,x2,...,xn)������Sb��K��ֵ");
		String encoderByMD5 = KEP_SC_5_1.encoderByMD5(Key + "");
		System.out.println("��" + Key + "��Ϊ�Ự��Կ,��(" + create_B_sum + "," + encoderByMD5 + ")���͸�Bob");
		System.out.println("Bob��������");
		//BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		//System.out.println("[W' ���� ʹ�� (W*W')MOD M=1] --- " + w_ni);
		System.out.println(get_K_ni(M_W, P_V[1], P_V[0], create_M_ni,  Key,create_B_sum, L_seq,   new BigInteger(U_K_V[0]+""),
				 new BigInteger(U_K_V[1]+"") , R,  L));
			
	}

	public static BigInteger[] create_random_seq(int number){
		BigInteger[] random_seq=new BigInteger[number];
		for(int i=0;i<number;i++){
			random_seq[i]=new BigInteger(random.nextInt(100)+"");
		}
		return random_seq;
	}
	
	
	
	public static BigInteger[] create_B_seq(BigInteger[] A_seq,BigInteger[] M_W,BigInteger[] random_seq){
		
		BigInteger[] B_seq=new BigInteger[A_seq.length];
		for(int i=0;i<A_seq.length;i++){
			B_seq[i]=A_seq[i].add(M_W[0].multiply(random_seq[i]));
		}
		return B_seq;
	}
	
	public static BigInteger[] create_B_seq1(BigInteger[] A_seq,BigInteger[] M_W,BigInteger[] random_seq){
		BigInteger[] B_seq=new BigInteger[A_seq.length];
		for(int i=0;i<A_seq.length;i++){
			B_seq[i]=A_seq[i].multiply((M_W[0].multiply(random_seq[i])).add(BigInteger.ONE));
		}
		return B_seq;
		
	}
	
	
	
	
	public static String get_K_ni(BigInteger[] M_W,BigInteger V, BigInteger P, BigInteger M_ni, BigInteger K, BigInteger Sb, BigInteger[] L_seq, BigInteger u,
			BigInteger k ,BigInteger R, long L) throws Exception {
		
		BigInteger Sa =Sb.mod(M_W[0]);
		//(W_ni * Sb) % M;
		System.out.println("Sa��ֵΪ   "+Sa);
		BigInteger T = V.multiply(Sa).divide(P).mod(M_ni);
		//(V * Sa / P) % M_ni;
		System.out.println("T��ֵΪ" + T);
		int j=1;
		BigInteger K_ni=BigInteger.ZERO;
		for(int i=0;i<=k.subtract(BigInteger.ONE).longValue();i++){
			double pow = Math.pow(2, L);
			if(i==0) K_ni= T.divide(new BigInteger((long)pow+""));
			if(i!=0) K_ni=((T.add(new BigInteger(i+"").multiply(u))).mod(M_ni)).divide(new BigInteger((long)pow+""));
					//(long)(((T+i*u)%M_ni)/pow);
			if (KEP_SC_5_1.encoderByMD5(K_ni + "").equals(KEP_SC_5_1.encoderByMD5(K + ""))) {
					return "��ԭ��Կ�ɹ� "+K_ni+"��ԭ����  "+j;
				}
			j++;
			
		}

		return "error";
		
		
	}
	
	
	
}
