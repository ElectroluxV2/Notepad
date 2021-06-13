package com.github.electroluxv2.utils;

import com.github.electroluxv2.components.ErrorAlert;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Crypt {
    private static final KeyGenerator keyGenerator;
    private static final Cipher desCipher;
    private static final String algorithm = "AES";

    static {
       KeyGenerator kg = null;
       Cipher dc = null;

       try {
           kg = KeyGenerator.getInstance(algorithm);
           kg.init(256);
           dc = Cipher.getInstance(algorithm);
       } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) { ErrorAlert.Show(e); } finally {
           keyGenerator = kg;
           desCipher = dc;
       }
    }

    public static String encrypt(final String string, final SecretKey secretKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final byte[] text = string.getBytes(StandardCharsets.UTF_8);
        desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return Base64.getEncoder().encodeToString(desCipher.doFinal(text));
    }

    public static String decrypt(final String string, final SecretKey secretKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final byte[] text = string.getBytes(StandardCharsets.UTF_8);
        desCipher.init(Cipher.DECRYPT_MODE, secretKey);

        return new String(desCipher.doFinal(Base64.getDecoder().decode(text)));
    }

    public static SecretKey generateKey() {
        return keyGenerator.generateKey();
    }

    public static String encodeKey(final SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey decodeKey(final String encodedKey) throws IllegalArgumentException, IndexOutOfBoundsException {
        final var decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
    }
}
