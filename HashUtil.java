import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;



public final  class HashUtils {
	  // 分区长度:数据段分布定义，其中取模的数一定要是2^n， 因为这里使用x % 2^n == x & (2^n - 1)等式，来优化性能。
    private static final int PARTITION_LENGTH = 16;

    // %转换为&操作的换算数值
    private static final long AND_VALUE = PARTITION_LENGTH - 1;


	/** 
     * 用MD5压缩算法，生成hashmap的value值 
     *  
     * @param source 
     * @return 
     * @throws NoSuchAlgorithmException 
     */  
    public static String hash(String key) {  
        String s = null;  
  
        MessageDigest md;  
        try {  
            md = MessageDigest.getInstance("MD5");  
            md.update(key.getBytes());  
            // MD5的结果：128位的长整数，存放到tmp中  
            byte tmp[] = md.digest();  
            s = toHex(tmp);  
  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return s;  
    }  
  
    /** 
     * 将二进制的长整数转换为16进制的数字，以字符串表示 
     *  
     * @param bytes 
     * @return 
     */  
    private static String toHex(byte[] bytes) {  
        // hexDigits的用处是：将任意字节转换为十六进制的数字  
        char hexDigits[] = "0123456789abcdef".toCharArray();  
        // MD5的结果：128位的长整数，用字节表示就是16个字节，用十六进制表示的话，使用两个字符，所以表示成十六进制需要32个字符  
        char str[] = new char[16 * 2];  
        int k = 0;  
        for (int i = 0; i < 16; i++) {  
            byte b = bytes[i];  
            // 逻辑右移4位，与0xf（00001111）相与，为高四位的值，然后再hexDigits数组中找到对应的16进制值  
            str[k++] = hexDigits[b >>> 4 & 0xf];  
            // 与0xf（00001111）相与，为低四位的值，然后再hexDigits数组中找到对应的16进制值  
            str[k++] = hexDigits[b & 0xf];  
  
        }  
        String s = new String(str);  
        return s;   
    }  
    
    public static long hash2(String s, int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end > s.length()) {
            end = s.length();
        }
        long h = 0;
        for (int i = start; i < end; ++i) {
            h = (h << 5) - h + s.charAt(i);
        }
        return h;
    }
    
    public static void main(String[] args) throws UnknownHostException, SocketException {
    	/*System.out.println(hash2("-ssser.0",0,"-ssser.0".length())&AND_VALUE);
    	System.out.println(hash2("1234",0,"1234".length())&AND_VALUE);
    	System.out.println(hash2("0",0,"0".length())&AND_VALUE);
    	System.out.println(hash2("sk",0,"sk".length())&AND_VALUE);
    	
    	System.out.println("============================");
    	System.out.println(hash2("-ssser.0",0,"-ssser.0".length())%PARTITION_LENGTH);
    	System.out.println(hash2("1234",0,"1234".length())%PARTITION_LENGTH);
    	System.out.println(hash2("0",0,"0".length()));
    	System.out.println(hash2("",0,"".length()));
    	System.out.println(hash2(" ",0," ".length()));
    	System.out.println(hash2(new String("123"),0,new String("123").length()));
    	System.out.println(hash2(new String("1230"),0,new String("1230").length()));
    	System.out.println(hash2(new String("alipay123456781256001"),0,new String("alipay123456781256001").length())&AND_VALUE);
    	
    	System.out.println(2^9);
    	System.out.println(Math.pow(2, 12));
    	System.out.println(-1L ^ (-1L << 10L));
    	System.out.println(Integer.toBinaryString((-1 ^ (-1 << 10))));
    	System.out.println((-1 ^ (-1 << 10)));
    	
    	
    	InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] b=network.getHardwareAddress();
       
        displayMac(b);*/
        
        Map<String,ChildLister> listers=new HashMap<String,ChildLister>();
        
        if(listers.get("pengxianwei")==null)
        	
        listers.put("pengxianwei", new ChildLister(){

			@Override
			public void handle() {
				System.out.println("hadle....");
				
			}
        	
        } );
        
        listers.get("pengxianwei").handle();
       
	}
    

    public static void displayMac(byte[] mac)  
  
     {  
  
        for(int i=0;i<mac.length;i++)  
  
         {  
  
            byte b=mac[i];  
  
            int intValue=0;  
  
            if(b>=0)intValue=b;  
  
            else intValue=256+b;  
  
             System.out.print(Integer.toHexString(intValue));  
  
            if(i!=mac.length-1)  
  
             System.out.print("-");  
  
         }  
  
     }     
}
