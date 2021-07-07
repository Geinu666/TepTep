package com.example.splashdemo.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    private static final int INITIALIZE_LENGTH = 2048;

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(INITIALIZE_LENGTH);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) throws Exception {
        String temp = "";
        byte[] rs = Base64.decodeBase64(data);
        temp = new String(RSAUtils.decryptByPrivateKey(rs, PRIVATEKEY), "UTF-8");

        return temp;
    }

    public static void main(String[] args) throws Exception {
////        后端生成密钥对并拿到公钥
//        Map<String, Object> keyPair = RSAUtils.genKeyPair();
//        String publicKey = RSAUtils.getPublicKey(keyPair);
////        将公钥发送给前端
////        ...
////        前端加密并发送给后端
//        String data = "111";
//        String encodedData = RSAEncryptedUtil.encryptedDataOnJava(data, publicKey);
//        System.out.println(encodedData);
////        ...
////        后端接收到密文后用私钥解密
//        String decodedData = RSAUtils.decryptDataOnJava(encodedData, RSAUtils.getPrivateKey(keyPair));
//        System.out.println(decodedData);
//        System.out.println(decodedData.equals(data));

//        String data = "test001";
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuR4zDJeN5BeUwXE7ZLMbNPRH/HgS/CzuxRC/umL1FZfFlfKt/wlLVBPLX76N5f1vMx2Bu1k6PLlrJY2G0sGqBsh92L0nOCbQdUDYIH1IyajHBWrKI/5aJ8U6yCmdIGVLa3UWugnj8yTd2dDxzxFByRN5V97VqYH9hIVvoGmP1WwIDAQAB";
//        System.out.println(RSAEncryptedUtil.encryptedDataOnJava(data, publicKey));

        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRjaAXe+lAW3gbYBtM8+C9od4qtzo0RvMXw/KU2blQzQBogdpFRR8LrgdRnLWMfKBk8uJu2sIorHPmQkzgqIrE6mkkf1G238jf11edZVPt722V438w246ND+hLzCOga7EVA0lEkypLApbW6udy4QqffsktU9bYHYnKYCJfkK/O5NLrtEYRyNHJ0A3HNoPW5mFHb9RTryyTZCK68HDTdqDCfRp84GgkpD4gV8YLGoS7BDqng+fQyvt33zebxooJxot0HeFMM6ChKv7lFJsTHpaZvIPpgVwK7WNS3bhZw6MFruiCRyK6qA5EP4HolLcLEVmzzBvfUCunm/MtxnZA+P1/AgMBAAECggEBAIduIp6XQfIK6dd29hAE/4ESwrg2ImcP+RKM1QLhDsf65z1WTXSUmw1wEQlD6bzdHRSw1jVVQME9rN17pTKIQA53GfvGp46j7LEyGiiuZoiGdg24Ttl0ANCKE5erSx7x9SPbGeo/kGOY9iblDRPcBtley+tsDgSqf1IZXwJSivLSlPUGITnVpMcI1mHyXzmmqTuxZBZmRt/TWKTrFngtYctP89kVlpXOGfFIDVb6X/6NeAQcWI1DhW7s9xs4VPV7vS42Dq0E52lMyNZkeOTy3JhoB8DLggc+UA5brwuao0SU+eAwjIQ/aIrR4CxEZLqO6ksJ1Uian8jAixmj3t9GgDECgYEA4srztrLNrOTN5DQr+wMaj8p9tdQb/Rp2i7aB4eUvL3Lo0e/S4cSK6XLaq25kThPS0bCRW2Gt09W7sfWxAYvbkbFA4NlF/vRbCE9HA9W6s/WgnKXlSXYrUJ0F93mUYS/Um+ODzTsWSpq7eMle828b8Oxgp9R5bZVSwDPMDhDradcCgYEApExQ8lBFbZh3Ay3JXqxmqw1oASu7WSBLo7P7yHP0GcnsWyclC18koQyTwkQMNXcfPxGWZnWFHYOOwXWSjLQSQDpFT1P1y5BBb1UEA71Z8AT8ykOO5TEJoSwbMf1q8n5IOwZjh2LP8KYViBHWI2Yjvr2nfn5yxisJdX/2d4ovpJkCgYACZez/0JI2MtEuiZKxe83rGVQM+So2mfII9iwXbGn8UXW+yo9rTb2+TDWdvHQZE0KWbwyKb9T7uXXbouJ1RrpSpxItjn6b/I993gC9PKRVxNIm9eYfyb0ZhB+NB18XS/8uitBY+jf5H/u9gjubzV/5Qzaeb7O9Aeg3GhUdTPaATwKBgHZSE5V+I5Ha1BKw/a01oTackAMP2HtGG9MIAlKI1wlfdtzqYu4+laV55ktoMBas9A66fsLI7ZWkscE3B9RWtjIVBOctQv1XpRgldJ+w1L1bCiYr4xPBbfrpRaiJpWR/d0syvai5i6D2Ktl0Zu/VVfa+pIofTE7UnZpGDlxmYws5AoGBAIojHF/XdWb6ptxqrBF75AJP1mlIThhIBiOTXUpWWKTSDbdmGcnmAJEtFXeXyZtBq94+gU4toJPSmBR5vaLASz1cpNWbfJcHYjXsr4ruopMfMzZQWtR2cUwjbdwvQlkPNjYpOeHYfWAXk+69Upe8zUbAWPygei/CC9UvEn6kAUnW";
//        String decodedData = "n7Jpx3ZFH8otNWYzIxkbSbPOXBsxunhz1L6+G6mvGFaoIL93k9V180SFulIWpH3Q5Fhdn4WwMgjb/yy8rBLSL2fAUg9gkkfu8JaoLkHtnEWNWOZsyUxNr8He7LnALwquGOETWqrRKVThjfThfBhr2k6YB99v40DiJgf/1lqnfd0=";
        String decodedData = "U18BMtKkgymr37rmoOXcWUvPcOO85ZdYMcFtLS5U0MH4tfdG8WFM3oy01bYwa4czr1zYcU6tHBvKL+TEp5wJk2DjI3eJFJbKTTQZgl1xNuuCZex1ubv3tNyR3lhlghrk/85SEpSDT4GQeo1wwfB3afgjkjc5cJBEtKyGWDTVJjDe1vkk2Wksdn+JEn4afsG0Dm4Fj0mIIWgMEnWwaUMPw9bwAkUMT7f6CUosA3YxoHJ1wxyagN7+JwCF1tM6ch0cP0YgCftieV5O3CNVuu1K2LpC8A4M1euDftDww8gcoO7m3sTGQj9w4vy+33wMU0IDwE6675fYvYn2CbD2Er5g2Q==";
        System.out.println(decryptDataOnJava(decodedData, privateKey));
    }
}