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
    private Date date;
    @Getter
    private int difficulty;

    private BlockChain() {
        this(1);
    }
    public BlockChain(int difficulty) {
        this.difficulty = difficulty;
        this.chain = new ArrayList<>();
        this.date = new Date();
    }

    public void add(T data) {
        final Block block = new Block(data, lastHash(), date.getTime());
        if (mineBlock(getDifficulty(), block)) {
            chain.add(block);
        } else {
            System.err.println("TODO: error mining block");
        }
    }

    public boolean validate() {
        //TODO
        throw new UnsupportedOperationException("not yet impl");
    }

    private String lastHash() {
        return chain.isEmpty() ? null : lastBlock().getHash();
    }

    private Block lastBlock() {
        return chain.get(chain.size() - 1);
    }

}
