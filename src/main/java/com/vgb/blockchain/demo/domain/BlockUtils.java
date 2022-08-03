package com.vgb.blockchain.demo.domain;

import com.vgb.blockchain.demo.exception.MiningException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BlockUtils {

    public static <T> void add(BlockChain<T> blockChain, T data) {
        final Block block = new Block(data, blockChain.lastHash(), blockChain.getDate().getTime());
        if (mineBlock(blockChain.getDifficulty(), block)) {
            blockChain.add(block);
        } else {
            System.err.println("TODO: error mining block");
        }
    }

    //TODO: use exceptions rather than boolean
    //TODO: parallelize the validation
    public static <T> boolean validate(BlockChain<T> blockChain) throws MiningException {
        final String prefixString = prefixString(blockChain.getDifficulty());
        Iterator<Block<T>> it = blockChain.iterator();
        String previousHash = null;
        while (it.hasNext()) {
            final Block<T> block = it.next();
            if (validate(prefixString, previousHash, block)) return false;

            //update previous hash
            previousHash = block.getHash();
        }

        //TODO
//        throw new UnsupportedOperationException("not yet impl");
        return true;
    }

    private static <T> boolean validate(String prefixString, String previousHash, Block<T> block) throws MiningException {
        if (!block.getHash().startsWith(prefixString)) {
            return true;
        }
        if (!block.getHash().equals(calculateBlockHash(block))) {
            return true;
        }
        if (block.getPreviousHash() != null && !block.getPreviousHash().equals(previousHash)) {
            return true;
        }
        return false;
    }

    public static <T> boolean mineBlock(int difficulty, Block<T> block) {
        //define a prefixString containing <difficulty> number of zeroes
        //eg if prefix = 3, prefixString = "000"
        final String prefixString = prefixString(difficulty);

        //brute force the calculation of a block hash that is prefixed by prefixString
        while (block.getHash() == null || !block.getHash().substring(0, difficulty).equals(prefixString)) {
            //increment the nonce to ensure uniqueness in the hash
            block.incrementNonce();
            try {
                block.setHash(
                        calculateBlockHash(
                                block.getPreviousHash(),
                                block.getTimeStamp(),
                                block.getNonce(),
                                block.getData()));
            } catch (MiningException e) {
                return false;
            }
        }

        return true;
    }

    private static String prefixString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    private static <T> String calculateBlockHash(Block block) throws MiningException {
        return calculateBlockHash(block.getPreviousHash(), block.getTimeStamp(), block.getNonce(), block.getData());
    }

    private static <T> String calculateBlockHash(String previousHash, long timeStamp, int nonce, T data) throws MiningException {
        final String dataToHash = dataToHash(previousHash, timeStamp, nonce, data);
        final byte[] bytes = dataToBytes(dataToHash);
        final String bytesAsString = bytesAsString(bytes);
        return bytesAsString;
    }

    private static <T> String dataToHash(String previousHash, long timeStamp, int nonce, T data) {
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
