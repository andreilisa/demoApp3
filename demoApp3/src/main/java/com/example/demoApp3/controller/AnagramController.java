package com.example.demoApp3.controller;

import com.example.demoApp3.Mapper.AnagramMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.TreeSet;


@Slf4j
@Service
public class AnagramController {

    @Value("${file-reader}")
    String pathFile;

    @Value("${file-name}")
    String prefix;

//    @EventListener(ApplicationReadyEvent.class)
//    @RequestMapping(value = "/getAnagrams", method = RequestMethod.GET)
    public void anagramController() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        {
            AnagramMapper anagramMapper = session.getMapper(AnagramMapper.class);
            anagramMapper.createFunction();
            anagramMapper.createTable();
            File main = new File(pathFile);
            if (main.exists() && main.isDirectory()) {
                File[] arr = main.listFiles();
                recursiveShowFile(arr, 0, 0);
                for (File file : arr) {
                    if (file.getName().startsWith(prefix))
                    anagramMapper.write(new File(file.getPath()));

                }
            }
            Cursor<String> anagrams = anagramMapper.showAll();
            for (String anagram : anagrams) {
                TreeSet<String> anagramsByValue = anagramMapper.anagrams(anagram);
                if (anagramsByValue.size() > 1) {
                    System.out.println((anagramsByValue.toString().replaceAll("\\[", " ")
                            .replaceAll(",", " ")
                            .replaceAll("]", " ").replaceAll("\\{", " ")
                            .replaceAll("\\}", " ")));
                }
            }
        }
    }

    private void recursiveShowFile(File[] arr, int index, int level) {
        if (index == arr.length)
            return;

        for (int i = 0; i < level; i++)
            System.out.print("\t");

        if (arr[index].isFile())
            if (arr[index].getName().startsWith(prefix)) ;

            else if (arr[index].isDirectory()) {

                recursiveShowFile(arr[index].listFiles(), 0, level + 1);
            }

        recursiveShowFile(arr, ++index, level);
    }
}


