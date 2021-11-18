package com.baimsg;


import com.baimsg.bean.User;
import com.baimsg.dictionary.DictionaryContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Test {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(
                            Objects.requireNonNull(Test.class.getResource("/password.ini")).getFile()
                    )
            );
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(new DictionaryContext(new User("探Mi", "baimsg", s)).login().getUserName());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
