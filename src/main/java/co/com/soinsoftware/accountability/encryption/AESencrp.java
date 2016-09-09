package co.com.soinsoftware.accountability.encryption;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Carlos Rodriguez
 * @since 08/09/2016
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class AESencrp {

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'r', 'y', 'T',
			'o', 'D', 'e', 'c', 'r', 'y', 'p', 't', 'T', 'h', 'i', 's' };

	public static String encrypt(String Data) {
		String encryptedValue = "";
		Key key = generateKey();
		try {
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (InvalidKeyException | BadPaddingException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException ex) {

		}
		return encryptedValue;
	}

	public static String decrypt(String encryptedData) {
		String decryptedValue = "";
		Key key = generateKey();
		try {
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder()
					.decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} catch (InvalidKeyException | BadPaddingException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| IOException | IllegalBlockSizeException ex) {

		}
		return decryptedValue;
	}

	private static Key generateKey() {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
}