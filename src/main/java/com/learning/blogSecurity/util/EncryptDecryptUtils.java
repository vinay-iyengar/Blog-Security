package com.learning.blogSecurity.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class EncryptDecryptUtils {

    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException, CertificateException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        PublicKey blogPublicKey = CertificateFactory.getInstance("X.509")
                .generateCertificate(getClass().getClassLoader().getResourceAsStream("blogPublic.crt")).getPublicKey();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] cipherText = (aesCipher.doFinal(input.getBytes()));

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PUBLIC_KEY, blogPublicKey);
        byte[] encryptedKey = cipher.doFinal(secKey.getEncoded());

        String encryptedData = Base64.encodeBase64String(encryptedKey) + Base64.encodeBase64String(cipherText);
        System.out.println("Encrypted Request " + encryptedData );

        return encryptedData;
    }

    public String decrypt(String input) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyStore sslKeyStore = KeyStore.getInstance("JKS");
        sslKeyStore.load(getClass().getClassLoader().getResourceAsStream("blog-security.jks"), "blogsecurity".toCharArray());
        PrivateKey blogPrivateKey = (PrivateKey) sslKeyStore.getKey("blog.security", "blogsecurity".toCharArray());

        int length = input.length();
        byte[] encryptedKey = Base64.decodeBase64(input.substring(0, 684));
        byte[] byteCipherText = Base64.decodeBase64(input.substring(684, length));

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PRIVATE_KEY, blogPrivateKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);

        SecretKey originalKey = new SecretKeySpec(decryptedKey , 0, decryptedKey .length, "AES");
        Cipher aesCipher1 = Cipher.getInstance("AES");
        aesCipher1.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] bytePlainText = aesCipher1.doFinal(byteCipherText);
        String decryptedData = new String(bytePlainText);

        System.out.println("Decrypted Response " + decryptedData);

        return decryptedData;
    }
}
