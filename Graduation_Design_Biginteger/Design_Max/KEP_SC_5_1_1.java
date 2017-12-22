package Design_Max;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * KEP_SC_5.1.1   大整数版
 * */
public class KEP_SC_5_1_1 {

	
	public static BigInteger[] M_W = KEP_SC_5_1.M_W;
	public static BigInteger[] P_V = KEP_SC_5_1.P_V;
	public static BigInteger[] L_seq = KEP_SC_5_1.L_seq;
	public static Random random=KEP_SC_5_1.random;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int Number = 100;
//		BigDecimal a=new BigDecimal(BigInteger.valueOf(178),3);
//		BigDecimal b=new BigDecimal(BigInteger.valueOf(3),3);
//		System.out.println(a.divide(b,5));
//		Thread.sleep(20000);
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
		BigInteger[] E_seq = create_E_seq(Number);
		System.out.println("生成的D值");
		D_seq = KEP_SC_5_1.create_D_seq(create_M_ni, C_seq);
		D_seq_1=create_D_seq_1(D_seq, E_seq, create_M_ni);
		KEP_SC_5_1.print_seq(D_seq_1);
		
		System.out.println("生成的E值");
		KEP_SC_5_1.print_seq(E_seq);
		
	
		System.out.println("L0,L1的值");
		L_seq = create_L_seq(A_seq, delta, P_V,E_seq);
		KEP_SC_5_1.print_seq(L_seq);
		
		
		System.out.println("公钥为(" + KEP_SC_5_1.toValue(B_seq) + "," + KEP_SC_5_1.toValue(D_seq_1) + "," + create_M_ni + ")");
		System.out
				.println("私钥为(" + KEP_SC_5_1.toValue(A_seq) + KEP_SC_5_1.toValue(delta) + KEP_SC_5_1.toValue(M_W) + KEP_SC_5_1.toValue(P_V) + KEP_SC_5_1.toValue(L_seq) + ")");
		Thread.sleep(1000);
		System.out.println("密钥交换");
		System.out.println("Alice操作部分");
		System.out.println("Alice获得Bob的公钥(" + KEP_SC_5_1.toValue(B_seq) + "," + KEP_SC_5_1.toValue(D_seq_1) + "," + create_M_ni + ")");
		System.out.println("Alice随机选择(x1,x2,...,xn)并计算Sb和K的值");
		BigInteger[] create_source = KEP_SC_5_1.create_source(Number);
		KEP_SC_5_1.print_seq(create_source);
		BigInteger create_B_sum = KEP_SC_5_1.create_B_sum(create_source, B_seq);
		BigInteger create_K = KEP_SC_5_1.create_K(create_source, D_seq_1, create_M_ni);
		String encoderByMD5 = KEP_SC_5_1.encoderByMD5(create_K + "");
		System.out.println("将" + create_K + "作为会话密钥,将(" + create_B_sum + "," + encoderByMD5 + ")发送给Bob");
		System.out.println("Bob操作部分");
		BigInteger w_ni = KEP_SC_5_1.get_w_ni(M_W[1], M_W[0]);
		System.out.println("[W' 满足 使得 (W*W')MOD M=1] --- " + w_ni);
		System.out.println(KEP_SC_5_1.get_K_ni(w_ni, M_W[0], P_V[1], P_V[0], create_M_ni, create_K, create_B_sum,L_seq));
		
		
	}

	/**
	 * 
	 * */
	public static BigInteger[] create_E_seq(int n){
		BigInteger[] E_seq=new BigInteger[n];
		int e=random.nextInt(2)*3+1;
		System.out.println("e的值为 "+e);
		int min=-e;
		int max=e;
		for(int i=0;i<n;i++){
			E_seq[i]=new BigInteger((random.nextInt(max - min ) + min)+"");
		}
		return E_seq;
	}
	/**
	 * 
	 * */
	public static BigInteger[] create_D_seq_1(BigInteger[] D_seq,BigInteger[] E_seq,BigInteger M_ni){
		
		BigInteger[] D_seq_1=new BigInteger[D_seq.length];
		for(int i=0;i<D_seq_1.length;i++){
			D_seq_1[i]=(D_seq[i].add(E_seq[i])).mod(M_ni);
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
	public static BigInteger[] create_L_seq(BigInteger[] A_seq, BigInteger[] delta, BigInteger[] P_V,BigInteger[] E_seq) {
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
		
		BigInteger e0=BigInteger.ZERO,e1=BigInteger.ZERO;
		for(int i=0;i<E_seq.length;i++){
			if(E_seq[i].compareTo(BigInteger.ZERO)>=0){
				e1=e1.add(E_seq[i]);
			}else{
				e0=e0.add(E_seq[i]);
			}
		}
		L_seq[0] = L0.subtract(e0);
		L_seq[1] = L1.add(e1);
		return L_seq;
	}
	
	
	
}
