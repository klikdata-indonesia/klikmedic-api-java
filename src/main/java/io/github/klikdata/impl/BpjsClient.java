package io.github.klikdata.impl;

import io.github.klikdata.dto.bpjs.AesKeySpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class BpjsClient {

    /**
     * X-timestamp, merupakan waktu yang akan di-generate oleh client saat ingin memanggil setiap service.
     * Format waktu ini ditulis dengan format unix-based-time (berisi angka, tidak dalam format tanggal sebagaimana mestinya).
     * Format waktu menggunakan Coordinated Universal Time ( UTC), dalam penggunaannya untuk mendapatkan timestamp,
     * rumus yang digunakan adalah (local time in UTC timezone in seconds) - (1970-01-01 in seconds).
     */
    public long getTimes(){
        long millis = System.currentTimeMillis();
        return millis/1000;
    }

    /**
     * Metode signature yang digunakan adalah menggunakan HMAC-SHA256, dimana paramater saat generate signature dibutuhkan parameter message dan key.
     * Berikut contoh hasil generate HMAC-SHA256
     * message : aaa
     * key : bbb
     */
    public String generateHmacSHA256Signature(String data, String key) throws GeneralSecurityException {
        byte[] hmacData = null;
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacData);
    }


    public AesKeySpec generateKey(String key) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] _hashKey = digest.digest(key.getBytes(StandardCharsets.UTF_8));
        byte[] _hashIv = new byte[16];
        for (int i = 0; i < 16; i++) {
            _hashIv[i] = _hashKey[i];
        }

        AesKeySpec aesKeySpec = new AesKeySpec();
        SecretKeySpec _key = new SecretKeySpec(_hashKey, "AES");
        IvParameterSpec _iv = new IvParameterSpec(_hashIv);
        aesKeySpec.setKey(_key);
        aesKeySpec.setIv(_iv);
        return aesKeySpec;
    }


    /**
     * X-cons-id	743627386	consumer ID dari BPJS Kesehatan
     * X-timestamp	234234234	generated unix-based timestamp
     * X-signature	DogC5UiQurNcigrBdQ3QN5oYvXeUF5E82I/LHUcI9v0=	generated signature dengan pola HMAC-256
     * X-authorization	MDkwMzA0MDI6UXdlcnR5MSE6MDk1	generated signature dengan pola Base64
     * user_key	d795b04f4a72d74fae727be9da0xxxxx	user_key untuk akses webservice
     */
    public HttpHeaders getHeaderPcare() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(""))
                .headers("X-cons-id", "",
                        "X-timestamp", "",
                        "X-signature", "",
                        "X-authorization", "",
                        "user_key", "")
                .build();
        return null;
    }

    /**
     * X-cons-id	743627386	consumer ID dari BPJS Kesehatan
     * X-timestamp	234234234	generated unix-based timestamp
     * X-signature	DogC5UiQurNcigrBdQ3QN5oYvXeUF5E82I/LHUcI9v0=	generated signature dengan pola HMAC-256
     * user_key	d795b04f4a72d74fae727be9da0xxxxx	user_key untuk akses webservice
     */
    public HttpHeaders getHeaderVClaim() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(""))
                .headers("X-cons-id", "",
                        "X-timestamp", "",
                        "X-signature", "",
                        "user_key", "")
                .build();
        return null;
    }

    /**
     * Response kembalian dari web service vclaim sudah dalam bentuk compres dan terenkripsi.
     *
     * Kompresi service menggunakan metode : Lz-string
     * Enkripsi menggunakan metode : AES 256 (mode CBC) - SHA256 dan key enkripsi: consid + conspwd + timestamp request (concatenate string)
     *
     * Langkah proses dalam melakukan decrypt data response sebagai berikut :
     * 1. Dekripsi : AES 256 (mode CBC) - SHA256
     * 2. Dekompresi : Lz-string (decompressFromEncodedURIComponent)
     */
    public String decrypt(String cipherText, SecretKeySpec key, IvParameterSpec iv)
            throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(java.util.Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public String encrypt(String cipherText, SecretKeySpec key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(cipherText.getBytes(StandardCharsets.UTF_8)));
    }
}
