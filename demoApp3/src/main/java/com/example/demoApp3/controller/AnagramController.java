package com.example.demoApp3.controller;

import com.example.demoApp3.Mapper.AnagramMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.TreeSet;


@Api
@RestController
@RequestMapping("/anagrams")
@Slf4j
public class AnagramController {

    @Value("${file-reader}")
    String pathFile;

    @Value("${file-name}")
    String prefix;

    @EventListener(ApplicationReadyEvent.class)
    @RequestMapping(value = "/getAnagrams", method = RequestMethod.GET)
    public void AnagramController() throws IOException {
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
                for (File file : arr) anagramMapper.write(new File(file.getPath()));
            }
            Cursor<String> anagrams = anagramMapper.showAll();
            for (String anagram : anagrams) {
                TreeSet<String> anagramsByValue = anagramMapper.anagrams(anagram);
                if (anagramsByValue.size() > 1) {
                    log.info(anagramsByValue.toString().replaceAll("\\[", " ")
                            .replaceAll(",", " ")
                            .replaceAll("]", " ").replaceAll("\\{", " ")
                            .replaceAll("\\}", " "));

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


