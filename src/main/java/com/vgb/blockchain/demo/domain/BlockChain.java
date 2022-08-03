package com.vgb.blockchain.demo.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vgb.blockchain.demo.domain.BlockUtils.mineBlock;

@ToString
public class BlockChain <T>{
    private List<Block<T>> chain;
    @Getter
    private int difficulty = 4;

    private BlockChain() {
        this(1);
    }
    public BlockChain(int difficulty) {
        this.difficulty = difficulty;
        this.chain = new ArrayList<>();
    }

    public void add(String data) {
        Block block = new Block(data, lastHash(), new Date().getTime());
        mineBlock(getDifficulty(), block);
        chain.add(block);
    }

    private void add(Block block) {
        this.chain.add(block);
    }


    public Block lastBlock() {
        return chain.isEmpty() ? null : getLastBlock();
    }

    public String lastHash() {
        return chain.isEmpty() ? null : getLastBlock().getHash();
    }

    private Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

}
