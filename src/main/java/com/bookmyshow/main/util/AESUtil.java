package com.bookmyshow.main.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	public static String decrypt(String encryptedBase64, String secretKey) throws Exception {
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
		byte[] keyBytes = Base64.getDecoder().decode(secretKey); // this becomes 32 bytes
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);

		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, "UTF-8");
	}

	public static String encrypt(String plainText, String secretKey) throws Exception {
	    byte[] keyBytes = Base64.getDecoder().decode(secretKey); // FIXED
	    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

	    Cipher cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, keySpec);

	    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
	    return Base64.getEncoder().encodeToString(encryptedBytes);
	}

}
