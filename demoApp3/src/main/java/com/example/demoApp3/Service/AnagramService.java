package com.example.demoApp3.Service;

import com.example.demoApp3.Mapper.AnagramMapper;
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
import java.util.Objects;
import java.util.TreeSet;


@Service
public class AnagramService {

    @Value("${file-reader}")
    String pathFile;

    @Value("${file-name}")
    String prefix;


    public void showAnagram() throws IOException {
        Reader reader;

        reader = Resources.getResourceAsReader("mybatis-config.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        AnagramMapper anagramMapper = session.getMapper(AnagramMapper.class);

        anagramMapper.createFunction();
        anagramMapper.createTable();

        File main = new File(pathFile);

        if (main.exists() && main.isDirectory()) {
            File[] arr = main.listFiles();
            if (arr != null) {
                recursiveShowFile(arr, 0, 0);
            }
            if (arr != null) {
                for (File file : arr) {
                    if (file.getName().startsWith(prefix))
                        anagramMapper.write(new File(file.getPath()));
                }
            }
        }
        Cursor<String> anagrams = anagramMapper.showAll();

        for (String anagram : anagrams) {

            TreeSet<String> anagramsByValue = anagramMapper.anagrams(anagram);

            if (anagramsByValue.size() > 1) {

                System.out.println((anagramsByValue.toString().replaceAll("\\[", " ")
                        .replaceAll(",", " ")
                        .replaceAll("]", " ").replaceAll("\\{", " ")
                        .replaceAll("}", " ")));
            }
        }
    }


    private void recursiveShowFile(File[] arr, int index, int level) {
        if (index == arr.length)
            return;

        for (int i = 0; i < level; i++)
            System.out.print("\t");

        if (arr[index].isFile()) {

            if (arr[index].getName().startsWith(prefix)) {

                System.out.println(arr[index].getName());
            }
        } else if (arr[index].isDirectory()) {

            recursiveShowFile(Objects.requireNonNull(arr[index].listFiles()), 0, level + 1);
        }

        recursiveShowFile(arr, ++index, level);
    }
}