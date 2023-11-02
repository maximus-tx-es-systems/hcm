package com.maximus.hcm.utils;

import java.util.Base64;

public class EncryptionUtils {
	
	public static String getBase64Cipher(String plainText){
		return Base64.getEncoder().encodeToString(plainText.getBytes());
	}

	public static String getBase64Decoder(String cipherText){
		return new String(Base64.getDecoder().decode(cipherText));
	}
	
	public static String getCipherText(String plainText){
		return Base58.encode(plainText.getBytes());
	}

	public static String getPlainText(String cipherText){
		byte[] decodedBytes= null;
		try {
			decodedBytes = Base58.decode(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decodedBytes);
	}
	
	public static void main(String[] args) {
		EncryptionUtils.base64();
		EncryptionUtils.base58();
	}
	
	public static void base64() {
		String password ="hkjdsgTwfsta1sf11";
		String encodedString=Base64.getEncoder().encodeToString(password.getBytes());
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		System.out.println("Base64::password : " + password + " encodedString: " + encodedString + " decodedString: " + decodedString);
	}
	
	public static void base58() {
		String password ="hkjdsgTwfsta1sf11";
		String encodedString=Base58.encode(password.getBytes());
		byte[] decodedBytes;
		try {
			decodedBytes = Base58.decode(encodedString);
			String decodedString = new String(decodedBytes);
			System.out.println("Base58::password : " + password + " encodedString: " + encodedString + "  decodedString: " + decodedString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
