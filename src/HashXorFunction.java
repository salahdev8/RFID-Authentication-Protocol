import java.awt.Point;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;







public class HashXorFunction {
	
	public static String getHash(byte [] inputBytes, String algorithm)
	{
		String hashValue="";
		try {
			MessageDigest messageDigest=MessageDigest.getInstance(algorithm);
			messageDigest.update(inputBytes);
		
			byte[] digestedBytes = messageDigest.digest();
				hashValue =DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
			
			
		}catch(Exception e) {
			
		}
		
		return hashValue;
	}
	
	public static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i%key.length]);
        }
        return out;
    }
	
	
	

}