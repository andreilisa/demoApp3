package com.example.demoApp3.Service;

import com.example.demoApp3.Mapper.AnagramMapper;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Objects;
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

        if (main.exists() && main.isDirectory()) {
            File[] arr = main.listFiles();
            if (arr != null) {
                displayAll(main);
            }
            if (arr != null) {
                for (File file : arr) {
                    if (file.getName().startsWith(prefix))
                        anagramMapper.write(file.getPath());
                }
            }
        }
        Set<String> anagrams = anagramMapper.showAll();
        for (String anagram : anagrams) {
            TreeSet<String> anagramsByValue = anagramMapper.anagrams(anagram);
            if (anagramsByValue.size() > 1) {
                System.out.println((anagramsByValue.toString()
                        .replaceAll("\\[", " ")
                        .replaceAll(",", " ")
                        .replaceAll("]", " ")
                        .replaceAll("\\{", " ")
                        .replaceAll("}", " ")));
            }
        }
    }

    public static void displayAll(File path) {
        if (path.isFile()) {
            if (path.getName().startsWith("A-"))
                System.out.println(path.getName());
        } else {
            System.out.println(path.getName());
            File files[] = path.listFiles();
            for (File dirOrFile : files) {
                displayAll(dirOrFile);
            }
        }
    }
}
