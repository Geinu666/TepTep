package com.example.splashdemo.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class HuCryptoUtil {

    public static String encryptData(String data, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(data, KeyType.PublicKey);
    }

    public static String decryptData(String decodedData, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(decodedData, KeyType.PrivateKey);
    }

}
