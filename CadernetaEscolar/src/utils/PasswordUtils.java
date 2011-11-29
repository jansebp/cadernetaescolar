package utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * M�todo que calcula um MD5 Hash de um determinado String.
 * 
 * @param s em String
 * 
 * @return 	<code>calculatedHash</code> O Hash calculado;</br>
 * 			<code>null</code> Se algum problema ocorrer
 */
public class PasswordUtils {
	public static String calculateMD5Hash (String s){
		if (s == null)
			return null;
		
		String calculatedHash = null;
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			calculatedHash = new BigInteger(1, md5.digest(s.getBytes("UTF-8"))).toString(16);
		}catch(Exception ex) {}
		
		return calculatedHash;
	}
}
