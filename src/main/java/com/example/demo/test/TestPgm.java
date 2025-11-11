package com.example.demo.test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


//unique words pgm
public class TestPgm {

    public static void main(String[] args) {
        
        String str = "The dog sat on mat and my dog on mat is cute";

        String[] split = str.split("\\s");

        //Set<String> collect = Arrays.stream(split).collect(Collectors.toSet());

        Set<String> collect = Arrays.stream(split)
                                    .collect(Collectors.toCollection(LinkedHashSet::new));


        System.out.println(collect);


    }

}
