package com.example.demoApp3.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

import java.util.Set;
import java.util.TreeSet;

@Mapper
public interface AnagramMapper {

    Set<String> showAll();

    TreeSet<String> anagrams(String value);

    void createFunction();

    void createTable();

    void write(String path);
}