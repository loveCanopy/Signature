package KEP_SC_5_1;

public class Test {

	
	public static long[] L_seq=new long[2];
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		// A.22460,20026,18241,25157,26552,23027,16397,26607,28754,23741,17094,15907,19618,14420
		//生成的P值,V值
		//8,129
		//随机生成的delta序列
		//1,1,1,1,1,0,1,1,0,0,1,1,1,1
		long[] A_seq=new long[14];
		long[] delta=new long[14];
		long[] P_V=new long[2];
		A_seq=new long[] {22460,20026,18241,25157,26552,23027,16397,26607,28754,23741,17094,15907,19618,14420};
		delta=new long[]{1,1,1,1,1,0,1,1,0,0,1,1,1,1};
		P_V[0]=8;
		P_V[1]=129;
	
	   create_L_seq(A_seq, delta, P_V);
	 
	}

	
	
	public static void create_L_seq(long[] A_seq, long[] delta, long[] P_V) {
		long P = P_V[0];
		long V = P_V[1];
		double temp_l1 = 0.0;
		double temp_l2=0.0;
		double temp_l3=0.0;
		for (int i = 0; i < A_seq.length; i++) {
			// temp_l1+=(double)(delta[i]*(P-(V*A_seq[i]%P))%P)/P;
			temp_l2=((P - ((V * A_seq[i]) % P)) % P);
			temp_l3=delta[i] * temp_l2/ P;
			temp_l1 +=temp_l3 ;
		}
		System.out.println(temp_l1);
		long L1 = (long) temp_l1 + 1;
		
		
	}
}
