package com.example.demoApp3.entity;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Anagram {

    private String word;
    private  String wordSort;

    @Override
    public String toString() {
        return word;
    }

}
