package com.example.madagascar.model;

import java.util.List;

public class Tokens {
    private List<String> tokens;

    public Tokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public Tokens() {
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public void setTokens(String nouv){
        this.getTokens().add(nouv);
    }
}
