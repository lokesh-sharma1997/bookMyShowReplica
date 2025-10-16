package com.bookmyshow.main.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

public class AESUtilTest {

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	@Test
	public void testDecrypt() throws Exception {
		// Generate AES 256-bit key
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		SecretKey secretKey = keyGen.generateKey();

		// Base64 encode the key
		String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

		String plaintext = "This is a test string";

		// Encrypt plaintext using AES/ECB/PKCS5Padding
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
		String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);

		// Calling AESUtil.decrypt method
		String decrypted = AESUtil.decrypt(encryptedBase64, base64Key);

		// Assert decrypted text equals original plaintext
		assertEquals(plaintext, decrypted);
	}
}
