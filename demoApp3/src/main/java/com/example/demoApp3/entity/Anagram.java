package com.example.demoApp3.entity;


import lombok.*;

@Data
@Setter
@Getter
public class Anagram {

    private final String word;

    public Anagram(String word, String wordSort) {
        this.word = word;
    }

    @Override
    public String toString() {
        return
                "" +
                        word;
    }

}
