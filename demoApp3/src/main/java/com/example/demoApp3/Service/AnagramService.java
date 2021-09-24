package com.example.demoApp3.Service;

import com.example.demoApp3.Mapper.AnagramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;


@Service
public class AnagramService {

    @Value("${file-reader}")
    String pathFile;

    @Value("${file-name}")
    String prefix;

    @Autowired
    private AnagramMapper anagramMapper;

    public void writeAndShow() {
        anagramMapper.createFunction();
        anagramMapper.createTable();

        File main = new File(pathFile);

        for (File file : showFile(main)) {
            if (file.getName().startsWith(prefix))
                anagramMapper.write(file.getPath());
        }
        Set<String> anagrams = anagramMapper.showAll();
        for (String anagram : anagrams) {
            TreeSet<String> anagramsByValue = anagramMapper.anagrams(anagram);
            if (anagramsByValue.size() > 1) {
                String[] stringArray = anagramsByValue.toString().split("");

                StringBuilder newString = new StringBuilder();

                int count = stringArray.length;

                for (int i = 0; i < count; i++) {
                    switch (stringArray[i]) {
                        case "[":
                        case "]":
                        case ",":
                            stringArray[i] = " ";
                            break;
                    }
                    newString.append(stringArray[i]);
                }
                System.out.println(newString);
            }
        }
    }

    public File[] showFile(File path) {
        if (path.isFile()) {
            if (path.getName().startsWith(prefix))
                System.out.println(path.getName());
        }
        File[] files = path.listFiles();
        if (files != null) {
            for (File f : files) {
                showFile(f);
            }
        }
        return files;
    }
}
