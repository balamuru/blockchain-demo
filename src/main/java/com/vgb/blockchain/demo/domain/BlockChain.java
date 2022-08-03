package com.vgb.blockchain.demo.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ToString
public class BlockChain<T> {
    private List<Block<T>> chain;
    @Getter
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

    public void add(Block<T> block) {
        this.chain.add(block);
    }

    public Iterator<Block<T>> iterator() {
        return chain.iterator();
    }


    public T lastData() {
        return chain.isEmpty() ? null : lastBlock().getData();
    }

    public String lastHash() {
        return chain.isEmpty() ? null : lastBlock().getHash();
    }

    private Block<T> lastBlock() {
        return chain.get(chain.size() - 1);
    }

}
