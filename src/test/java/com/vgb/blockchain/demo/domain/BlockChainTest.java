package com.vgb.blockchain.demo.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BlockChainTest {

    @Test
    void testGenerateBlock() {
        BlockChain<String> blockChain = new BlockChain(4);
        blockChain.add("hello world");
        blockChain.add("hello again");
        System.err.println(blockChain);


    }

}