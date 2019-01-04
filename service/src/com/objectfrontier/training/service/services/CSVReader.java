package com.objectfrontier.training.service.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.objectfrontier.training.service.pojo.Address;
import com.objectfrontier.training.service.pojo.Person;

public class CSVReader {

    public static Object[][] readCSVFile(String location) throws Exception {

        Path path = Paths.get(location);
        Stream<String> in = Files.lines(path);

        List<String[]> inputList = in.map(a -> a.split(",")).collect(Collectors.toList());
        List<Person> actualPersonList = new ArrayList<>();
        List<Person> expectedPersonList =  new ArrayList<>();
        in.close();

        for (String[] personData : inputList) {

        	Address address = new Address(Integer.parseInt(personData[6]), personData[4], personData[5]);
        	Person person = new Person(personData[0], personData[1],personData[2], personData[3], address);
        	Person expectedPerson = person;
        	actualPersonList.add(person);
        	expectedPersonList.add(expectedPerson);
		}

        Object[][] personArray = new Object[actualPersonList.size()][2];
        for(int i = 0; i< actualPersonList.size(); i++) {

        	personArray[i][0] = actualPersonList.get(i);
        	personArray[i][1] = expectedPersonList.get(i);
        }
        return personArray;
    }

    public static void main(String[] args) throws Exception {

        String location = "D:\\dev\\training\\koyi.raghavarao\\service\\dataFiles\\example.csv";
        readCSVFile(location);

    }

}
