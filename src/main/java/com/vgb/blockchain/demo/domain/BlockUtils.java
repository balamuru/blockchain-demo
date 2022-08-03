package com.vgb.blockchain.demo.domain;

import com.vgb.blockchain.demo.exception.MiningException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BlockUtils {

    public static <T>boolean  mineBlock(int difficulty, Block<T> block) {
        //define a prefixString containing <difficulty> number of zeroes
        //eg if prefix = 3, prefixString = "000"
        final String prefixString = new String(new char[difficulty]).replace('\0', '0');

        //brute force the calculation of a block hash that is prefixed by prefixString
        while (block.getHash() == null || !block.getHash().substring(0, difficulty).equals(prefixString)) {
            //increment the nonce to ensure uniqueness in the hash
            block.incrementNonce();
            try {
                block.setHash(calculateBlockHash(block.getPreviousHash(),
                        block.getTimeStamp(),
                        block.getNonce(),
                        block.getData()));
            } catch (MiningException e) {
                return false;
            }
        };
        return true;
    }
    private  static <T>String calculateBlockHash(String previousHash, long timeStamp, int nonce, T data) throws MiningException {
        final String dataToHash = hash(previousHash, timeStamp, nonce, data);
        final byte[] bytes = dataToBytes(dataToHash);
        final String bytesAsString = bytesAsString(bytes);
        return bytesAsString;
    }

    private static  <T>String hash(String previousHash, long timeStamp, int nonce, T  data) {
        return previousHash + timeStamp + nonce + data;
    }

    private static byte[] dataToBytes(String dataToHash) throws MiningException {
        byte[] bytes;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new MiningException(e);
        }
        return bytes;
    }

    private static String bytesAsString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

}
