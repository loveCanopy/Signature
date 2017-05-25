package Signature_test;


import java.security.*;


import org.apache.commons.codec.binary.Base64;

/**
 * Created by yujie on 17-5-19.
 */
public class Signature1 {
    public static String Digest="";

    public  static Tool_A toolA =new Tool_A();

    public  static Tool_B toolB=new Tool_B();
    public static void main(String[] args) throws Exception{


        String message="hello";
        //得到信息Alice得到信息摘要
        get_Digest(message);
        System.out.println(Digest);
        //Alice的数字签名
        String signature=PrivateA(Digest);
        System.out.println(signature);
        createCommon();
        String encrypt_message=Encrypt_Message(message+"\001"+signature,toolB.K);
        System.out.println(encrypt_message);
        toolB.T=Long.parseLong(get_K1());
        System.out.println(toolB.T);
        /**
         * Bob收到的密文,进行解密用随机密钥对收到的密文进行解密，得到明文的数字信息，然后将随机密钥
    抛弃；
         * */
        String decrypt_message= Decrypt_Message(encrypt_message,toolB.T);
       /**
        * source_message为解密后的明文
        * */
        String source_message=decrypt_message.split("\001")[0];
        System.out.println("Bob收到的Message: "+source_message);
        /**
         * Bob 然后用随机密钥对收到的密文进行解密 得到source_signature数字签名
         * */
        String source_Digest=get_Message(decrypt_message.split("\001")[1]);
        System.out.println("Bob然后用随机密钥对收到的密文进行解密 得到信息摘要"+source_Digest);


        /**
         *Bob 将收到的信息摘要和新产生的信息摘要进行比较，如果一致，说明收到的信息> 没有被修改过。
         * */
        compareSignatrue(source_message,source_Digest);



    }

    /**
     * 1.Alice准备好信息 Message
     */


    /**
     * 2.Alice 对数字信息进行哈希运算 得到信息摘要
     * */
    public static String get_Digest(String str) throws Exception{
        Digest=toolB.encoderByMD5(str);
        return Digest;

    }

    /**
     *3.Alice用自己的私钥对信息摘要进行加密得到 Alice 的数字签名，并将其附在数字信息上；
     */
    public static String PrivateA(String str) throws Exception{
        toolA.keyMap = toolA. generateKeyBytes();
        // 加密
        PublicKey publicKey = toolA. restorePublicKey(toolA.keyMap.get(toolA.PUBLIC_KEY));
        toolA.encodedText = toolA. RSAEncode(publicKey, str.getBytes());
        String Signature=Base64.encodeBase64String(toolA.encodedText);
        return Signature;
    }


    /**
     * 4.Alice随机产生一个加密密钥，并用此密码对要发送的信息进行加密，形成密文
     *①获得Bob的公钥；（B，D，M'）
     *②随机选择X1,...,Xn 属于（0,1），并计算Sb,K；
     * */

    public static  String Encrypt_Message(String message,Long K){
        /**
         * 考虑k为一个数 ,将str变成一个字符数组，通过ASCII码来操作字符
         * */
        char[] array=message.toCharArray();
        for(int i=0;i<array.length;i++){
            array[i]=toolB.byteAsciiToChar(toolB.charToByteAscii2(array[i])+K);
        }
        String result="";
        for(int i=0;i<array.length;i++){
            result+=array[i];
        }

        return result;
    }



    /**
     * 5.Alice 用 Bob 的公钥对刚才随机产生的加密密钥进行加密，
     * 将加密后的 DES 密钥连同密文一起传送给Bob；
     *Bob的公钥=（B，D,M'）
     *DES密钥=（Sb,H(K)）
     *最后将DES密钥  和密文发送给Bob
     * */
    public static void createCommon() throws Exception{
          int number=5;
        toolB. A=toolB.createA(number);
          System.out.println("A："+toolB.A);

        toolB.MW=toolB.createMW(toolB.A);
        toolB.M=toolB.MW.get(0);
          System.out.println("M："+toolB.M);

        toolB.W=toolB.MW.get(1);
          System.out.println("W："+toolB.W);

        toolB.B=toolB.createB(toolB.A,toolB.M,toolB.W);
          System.out.println("B序列： "+toolB.B);

        toolB.PV=toolB.createPV(number);
        toolB.P=toolB.PV.get(0);
          System.out.println("P："+toolB.P);

        toolB.V=toolB.PV.get(1);
          System.out.println("V："+toolB.V);

        toolB.Q=toolB.createQ(number);
          System.out.println("∆序列:"+toolB.Q);

        toolB.C=toolB.createC(toolB.Q,toolB.A,toolB.PV.get(0),toolB.PV.get(1));
          System.out.println("C序列:"+toolB.C);

        toolB. m1=toolB.createM1(number,toolB.C);
          System.out.println("M':"+toolB.m1);

        toolB.D=toolB.createD(toolB.m1,toolB.C);
          System.out.println("D序列:"+toolB.D);

          String message="10110";
        toolB.SB=toolB.encrypt(message,toolB.B);
          System.out.println( "SB:"+toolB.SB);

        toolB.K=toolB.createK(message,toolB.D,toolB.m1);
          System.out.println("K:"+toolB.K);

          System.out.println(toolB.encoderByMD5(toolB.K+""));


    }

    /**
     *
     * 6.Bob 收到 Alice 传送来的密文和加密过的 DES 密钥，
     * 先用自己的私钥对加密的 DES 密钥进行解密，得到 Alice随机产生的加密密钥；
     * Bob的私钥为（A，▵，M，W，V，P，L0,L1）通过Bob的私钥 我们可以得到K的值
     */
    public static String get_K1() throws Exception{

        toolB.L=toolB.createL(toolB.A,toolB.Q,toolB.PV);
        toolB.W1=toolB.getW1(toolB.W,toolB.M);
        //System.out.println(toolB.W1+""+toolB.M+""+ toolB.V+"" +toolB.P+""+ toolB.m1+""+ toolB.K);
        return toolB.getK1( toolB.W1, toolB.M, toolB.V, toolB.P, toolB.m1, toolB.K);


    }




    /**
     *7.Bob 然后用随机密钥对收到的密文进行解密，得到明文的数字信息，然后将随机密钥抛弃
     *因为密文=K（Alice的数字签名+Message），我们通过逆运算得到Alice的数字签名+Message
     * */
    public static String Decrypt_Message(String message,Long K1){
        /**
         * 考虑k为一个数 ,将str变成一个字符数组，通过ASCII码来操作字符
         * */
        char[] array=message.toCharArray();
        for(int i=0;i<array.length;i++){
            array[i]=toolB.byteAsciiToChar(toolB.charToByteAscii2(array[i])-K1);
        }
        String result="";
        for(int i=0;i<array.length;i++){
            result+=array[i];
        }

        return result;
    }



    /**
     *8.Bob 用 Alice 的公钥对 Alice 的数字签名进行解密，得到信息摘要；
     * PublicA为Alice的公钥 因为Alice的数字签名=PrivateA(信息摘要)
     * 所以 信息摘要=PublicA(Alice的数字签名)=PublicA(PrivateA(信息摘要))
     * */

    public static String get_Message(String message){

        // 解密
        PrivateKey privateKey = toolA.restorePrivateKey(toolA.keyMap.get(toolA.PRIVATE_KEY));
        System.out.println("RSA decoded: "
                + toolA.RSADecode(privateKey, toolA.encodedText));

        return toolA.RSADecode(privateKey, toolA.encodedText);
    }




    /**
     * 9.Bob 用相同的哈希算法对收到的明文再进行一次哈希运算，得到一个新的信息摘要；
     * 信息摘要‘=Hash(Message）
     * */

    public static boolean compareSignatrue(String from_message,String decrypt_Digest) throws Exception{

        if(toolB.encoderByMD5(from_message).equals(decrypt_Digest)){
            System.out.println("收到的信息没有被修改");
            return true;
        }else{
            System.out.println("收到的信息被修改过");
            return  false;
        }

    }



}



