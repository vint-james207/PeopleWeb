package com.james;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Person> personList = new ArrayList<>();

        File f = new File("people.txt");
        Scanner scanner = new Scanner(f);
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] column = line.split(",");
            Person person = new Person(column[0], column[1], column[2], column[3], column[4], column[5]);
            personList.add(person);
        }
        Spark.init();
        Spark.get(
                "/",
                (request, response) -> {
                    int offset = 0;
                    String offSetStr = request.queryParams("offset");
                    if (offSetStr != null) {
                        offset = Integer.valueOf(offSetStr);
                    }
                    HashMap m = new HashMap();
                    ArrayList<Person> tempList = new ArrayList<>(personList.subList(offset, 20+offset));
                    m.put("personList", tempList);
                    return new ModelAndView(m, "home.html");
                },
                new MustacheTemplateEngine()
        );
    }
}