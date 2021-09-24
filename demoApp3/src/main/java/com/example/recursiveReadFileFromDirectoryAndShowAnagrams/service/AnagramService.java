package com.example.recursiveReadFileFromDirectoryAndShowAnagrams.service;

import com.example.recursiveReadFileFromDirectoryAndShowAnagrams.mapper.AnagramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

@Service
public class AnagramService {

    final int CONST = 1;

    @Value("${file-reader}")
    private String pathFile;

    @Value("${file-name}")
    private String prefix;

    @Autowired
    private AnagramMapper anagramMapper;

    public void write() {
        anagramMapper.createFunction();
        anagramMapper.createTable();
        recursive(new File(pathFile));
    }
    public void displayAnagram(){
        Set<String> anagrams = anagramMapper.showAll();
        for (String anagram : anagrams) {
            TreeSet<String> anagramsByValue = anagramMapper.anagrams(anagram);
            if (anagramsByValue.size() > CONST ) System.out.println(anagramsByValue);
        }
    }

    public void recursive(File file) {
        if (file.isFile() && file.getName().startsWith(prefix)) {
            anagramMapper.write(file.getPath());
        }

        File[] files = file.listFiles();

        if (files == null) return;

        for (File fileDir : files) {
            recursive(fileDir);
        }
    }
}
