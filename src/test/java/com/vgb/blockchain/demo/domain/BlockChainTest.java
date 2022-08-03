package com.vgb.blockchain.demo.domain;

import com.vgb.blockchain.demo.exception.MiningException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BlockChainTest {

    @Test
    void testGenerateBlock() throws MiningException {
        BlockChain<String> blockChain = new BlockChain(4);
        BlockUtils.add(blockChain, "hello world");
        BlockUtils.add(blockChain, "hello again");

        System.err.println(blockChain);
        Assertions.assertEquals("hello again", blockChain.lastData());
        Assertions.assertTrue(BlockUtils.validate(blockChain));


    }

}