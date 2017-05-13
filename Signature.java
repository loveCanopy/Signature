package Signature_test;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by yujie on 17-4-24.
 */
public class Signature {

   /**A序列,难解的背包向量
    *  **/
   static ArrayList<Long> A=new ArrayList<Long>();
   /**
    * 找到M,W使其满足条件
    * */
   static ArrayList<Long> MW=new ArrayList<Long>();
   static long M,W=0;
   static long P,V=0;
   static long W1=0;   //W的逆
   /**
    * B序列  B=W×A（MOD M）
    * */
   static ArrayList<Long> B= new ArrayList<Long>();

   /**
    * 找到P,V使其满足条件
    * */
   static ArrayList<Long> PV=new ArrayList<Long>();
    /**
     * 找到∆序列
     * */
   static  ArrayList<Integer> Q=new ArrayList<Integer>();
    /**
     * 找到C序列
     * */
   static  ArrayList<Long> C=new ArrayList<Long>();

   /**
    * 找到M’
    * */
   static  long m1=0;
    /**
     * 找到D序列
     * */
   static ArrayList<Long> D=new ArrayList<Long>();
   /*
   * 得到L0,L1
   * */
   static long L0,L1;
    static long SB,K;
   static Random r=new Random();
   public static void main(String[] args) throws Exception{
          Scanner scanner = new Scanner(System.in);
          System.out.println("请输入长度：");
          int number = scanner.nextInt();

          A=createA(number);
          System.out.println("A序列： "+A);

          MW=createMW(A);
          M=MW.get(0);
          System.out.println("M："+M);
          W=MW.get(1);
          System.out.println("W："+W);

          B=createB(A,M,W);
          System.out.println("B序列： "+B);

          PV=createPV(number);
          P=PV.get(0);
          System.out.println("P："+P);
          V=PV.get(1);
          System.out.println("V："+V);

          Q=createQ(number);
          System.out.println("∆序列:"+Q);

          C=createC(Q,A,PV.get(0),PV.get(1));
          System.out.println("C序列:"+C);

          m1=createM1(number,C);
          System.out.println("M':"+m1);


           D=createD(m1,C);
          System.out.println("D序列:"+D);

          createL(A,Q,PV);
          System.out.println("L0:" +L0);
          System.out.println("L1:"+L1);
          System.out.println("请输入"+number+"位0或1");
          Scanner scanner1 = new Scanner(System.in);
          String message=scanner1.nextLine();
          SB=encrypt(message,B);
          System.out.println( "SB:"+SB);
          K=createK(message,D,m1);
          System.out.println("K:"+K);
          System.out.println(encoderByMD5(K+""));
          W1=getW1(W,M);
          System.out.println("W':"+W1);
        System.out.println(getK1( W1, M, V, P, m1, K));

    }
    //A序列,生成一个难解的背包序列
    public static ArrayList<Long> createA(int n){
        long sum = 0;
        int i=0;
        long[]a=new long[n];

        for(a[0]=(long)(Math.random()*10)+1, i=1;i<a.length;i++){
            sum+=a[i-1]+1;
            a[i]=sum;
        }
        for(int j=0;j<a.length;j++){
            A.add(a[j]);
        }
        return A;
    }

    //随机生成M 使得M>sum(A[i]) 并找到W使得 gcd(W,M)=1 且W<M
    public static ArrayList<Long> createMW(ArrayList<Long> A){
        long result=0;
        for(int i=0;i<A.size();i++){
            result+=A.get(i);
        }
        M=result+(long)Math.random()*10;
        long R=(long)(Math.random()*100)+2;
        W=(gcd(M,R));
        while(W!=1){
            R++;
            W=(gcd(M,R));
        }
        ArrayList<Long> MW=new ArrayList<Long>();
        MW.add(M);
        MW.add(R);
        return MW;
    }

    //生成B序列
    public static ArrayList<Long> createB(ArrayList<Long> A,long M,long W){

        for(int i=0;i<A.size();i++){
            long temp=(W*A.get(i))%M;
            B.add(temp);
        }
        return B;
    }


    //gcd 求最大公约数
    public static long gcd(long a,long b){
        long temp=0;
        while(a%b!=0){
            temp=a%b;
            a=b;
            b=temp;
        }
        return b;
    }

    //int n4 = r.nextInt(8) + 2;
    //生成V,P 满足gcd(V,P)=1 且 (n+2)P<V<2的n次P
    public static ArrayList<Long> createPV(int n){

        long V=0,P=r.nextInt(8)+2;
        for(V=(n+2)*P;V<(2<<n)*P;V++){
            if(gcd(V,P)==1&&V!=P){
                break;
            }

        }
        ArrayList<Long> PV=new ArrayList<Long>();
        PV.add(P);
        PV.add(V);
        return PV;
    }

    //随机生成（0,1）的序列 Q C=V×A/P  当Qi=0时,C向下取整   当Qi=1时,C向上取整
    public static  ArrayList<Integer> createQ(int n){
        ArrayList<Integer> Q=new ArrayList<Integer>();
        for(int i=0;i<n;i++){
            int  k=r.nextInt(2);
            Q.add(k);
        }
        return Q;
    }

    //生成C序列 C=V×A/P  当Qi=0时,C向下取整   当Qi=1时,C向上取整
    public static ArrayList<Long> createC(ArrayList<Integer> Q,ArrayList<Long> A,long P,long V){
        ArrayList<Long> C=new ArrayList<Long>();
        long k=0;
        for(int i=0;i<A.size();i++){
            if(Q.get(i)==0){
                 k=V*A.get(i)/P;
            }else if(Q.get(i)==1){
                 k=(long)Math.ceil((double)V*A.get(i)/P);
                 //k=V*A.get(i)/P;
            }
            C.add(k);
        }

        return  C;
    }



    //寻找M' 满足条件 2<<n <M'< 2<<(n+1) 且 gcd(ci,M')=1
    public static long createM1(int n , ArrayList<Long> C){
        long M1=0;
        for(M1=(2<<n)+1;M1<(2<<(n+1));M1++){
            for(int i=0;i<C.size()&&gcd(M1,C.get(i))==1;i++){
                if(i==C.size()-1){
                    return M1;
                }
            }
        }
        return 0;
    }

    //计算D D=CMOD(M')
    public static ArrayList<Long> createD(long M,ArrayList<Long> C){
        ArrayList<Long> D=new ArrayList<Long>();
        for(int i=0;i<C.size();i++){
            long k=C.get(i)%M;
            D.add(k);
        }
        return D;
    }

    //计算L0 L1的值
    public static void createL(ArrayList<Long> A,ArrayList<Integer> Q,ArrayList<Long> PV){
        long P=PV.get(0);
        long V=PV.get(1);
        double temp_l0=0.0;
        for(int i=0;i<A.size();i++){
            temp_l0+=(double)(1-Q.get(i))*((V*A.get(i))%P)/P;
        }
        L0=(long)temp_l0+1;
        double temp_l1=0.0;
        for(int i=0;i<A.size();i++){
            temp_l1+=(double)(Q.get(i)*(P-(V*A.get(i)%P))%P)/P;
        }
        L1=(long)temp_l1+1;
    }

    //密钥交换 Alice（B,D,M'）,随机选取（x1,x2,x3,...）
    public static Long encrypt(String message,ArrayList<Long> B){
        char[] message_info=message.toCharArray();
        long Sb=0;
        for(int i=0;i<B.size();i++){
            Sb+=Integer.parseInt(message_info[i]+"")*B.get(i);
        }
        return Sb;
    }
    //得到K
    public static Long createK(String message,ArrayList<Long> D,long m1){

        char[] message_info=message.toCharArray();
        long K=0;
        for(int i=0;i<B.size();i++){
            K+=Integer.parseInt(message_info[i]+"")*D.get(i);
        }
        return K%m1;

    }


   //找到Hash函数  Alice将（SB,H(k))发送给Bob
    public static String encoderByMD5(String str) throws Exception{
        //选用MD5
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;

    }


    //Bob (A,∆,M,W,V,P,L0,L1)
    /**
     * 第一步  计算Sa =W的逆×SB（mod M）
     * 第二步  T=V×Sa/P 向下取整 mod M'
     *
     */
    public static long getW1(long W,long M){

        long W1=0;
        while((W1*W)%M!=1){
            W1++;
        }

        return W1;
    }
    //递归求导 求K’
    public static String getK1(long W1,long M,long V,long P,long m1,long K) throws Exception{
        long Sa=(W1*SB)%M;
        long T=(V*Sa/P)%m1;
        //System.out.println(T);
        int l0=0,l1=1;
        long K1=0;
        int i=1;
        while(l0<=L0){
            K1=(T-l0)%m1;
            l0++;
            if(encoderByMD5(K1+"").equals(encoderByMD5(K+""))){
                System.out.println("比较次数"+i);
                return K1+"";
            }else{
                i++;
            }

        }

        while (l1<=L1){
            K1=(T+l1)%m1;
            l1++;
            if(encoderByMD5(K1+"").equals(encoderByMD5(K+""))){
                System.out.println("比较次数"+i);
                return K1+"";
            }else {
                i++;
            }
        }

        return "有问题";
    }


}




