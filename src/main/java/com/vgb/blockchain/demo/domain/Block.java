package com.vgb.blockchain.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Block <T>{

    private T data;
    @Setter
    private String hash;
    private String previousHash;
    private long timeStamp;
    private int nonce; //aka proof-of-work aka pow

    public Block(T data, String previousHash, long timeStamp) {

        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = timeStamp;
        this.nonce = 0;
        this.hash = null;
    }

    public void incrementNonce() {
        nonce++;
    }


}
