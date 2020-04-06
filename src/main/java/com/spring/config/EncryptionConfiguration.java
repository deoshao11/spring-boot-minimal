package com.spring.config;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfiguration {

    private static final String charSet = "UTF-8";

    private static final String paddingType = "AES/CBC/PKCS5Padding";

    private static final String algorithm = "AES";

    @Value("${encryption.secretKey}")
    String secretKey;

    @Value("${encryption.ivKey}")
    String ivKey;

    @Bean
    public Cipher getCipher() throws Exception {
        Cipher cipher = Cipher.getInstance(paddingType);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(charSet), algorithm);
        IvParameterSpec ivspec = new IvParameterSpec(ivKey.getBytes(charSet));
        cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        return cipher;
    }
}
