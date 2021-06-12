package dev.tobiadegbuji.personality_research.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PersonalityCSVProcessor {


    public static void main(String[] args) throws IOException {
        List<List<String>> arrayList = test();

        List<String> allMbtiTypes = arrayList.stream()
                .map(i -> i.get(0))
                .collect(Collectors.toList());

        //How many introverts, how many extroverts from csv data?

        long countI = allMbtiTypes.stream()
                .filter(mbti -> mbti.contains("I"))
                .count();

        long countE = allMbtiTypes.stream()
                .filter(mbti -> mbti.contains("E"))
                .count();

        System.out.println(allMbtiTypes);
        System.out.println("Amount of introverts: " + countI);
        System.out.println("Amount of extroverts: " + countE);

        //Find which MBTI occurs the most

        Map<String, Long> occurrenceMap = new HashMap<>();

        allMbtiTypes.forEach(personalityType -> occurenceMapLogic(personalityType,occurrenceMap));


      Optional<Map.Entry<String, Long>> mostOccuringType =  occurrenceMap.entrySet().stream()
                .reduce((mostOccurred, personalityType) -> mostOccurred.getValue() <= personalityType.getValue() ? personalityType : mostOccurred);


        System.out.println("Most occurring type is: " + mostOccuringType.get().getKey() + " with " + mostOccuringType.get().getValue() + " submissions");

//        System.out.println(arrayList.get(1));

    }


    public static void occurenceMapLogic(String mbti, Map<String, Long> map){

        if(map.containsKey(mbti))
            map.put(mbti, map.get(mbti) + 1);
        else
            map.put(mbti, 1L);

    }

    public static List test() throws IOException {

        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/csv/mbti_1.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {

        }

        records.remove(0);

        return records;

    }


}