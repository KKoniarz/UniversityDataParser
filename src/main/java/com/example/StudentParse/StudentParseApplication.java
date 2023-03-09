package com.example.StudentParse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.util.Arrays;
import java.util.regex.*;

import java.util.List;

@SpringBootApplication
public class StudentParseApplication implements CommandLineRunner {

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	UniwersytetRepository uniwersytetRepository;
	@Autowired
	OcenaRepository ocenaRepository;

	//For pattern matching
	private static final String UNI_PATTERN = "(^Uniwersytet \\d+) liczy (\\d+) studentow";
	private static final String STUDENT_PATTERN = "student\\d+ nazywa sie (\\S+) (\\S+) ma wiek" +
			" (\\d+) lat i oceny ([\\d ]*)";

	public static void main(String[] args)
	{
		SpringApplication.run(StudentParseApplication.class, args);
	}

	@Override
	public void run(String... args) {

		try {
			File data = new File("C:\\Users\\Kacper\\Desktop\\Studia\\Semestr_II_pliki\\Metody_opisu_i_przetwarzania_danych_semistrukturalnych\\dane_t.txt");
			BufferedReader br = new BufferedReader(new FileReader(data));
			String line;
			while ((line = br.readLine()) != null) {
				parseLine(line);
			}
		} catch (FileNotFoundException fnfex) {
			System.err.println("File not found. " + fnfex.getMessage());
		} catch (IOException ioex) {
			System.err.println("Error while reading line. " + ioex.getMessage());
		}
		uniwersytetRepository.findAll().forEach(System.out::println);
		//studentRepository.findAll().forEach(System.out::println);
		//ocenaRepository.findAll().forEach(System.out::println);
	}

	private void parseLine(String line) {
		Pattern uniPattern = Pattern.compile(UNI_PATTERN);
		Matcher uniMatcher = uniPattern.matcher(line);

		if (!uniMatcher.find()) return;

		String uniName = uniMatcher.group(1);
		int numOfStudents = Integer.parseInt(uniMatcher.group(2));
		Uniwersytet newUni = uniwersytetRepository.save(new Uniwersytet(uniName, numOfStudents));

		Pattern studentPattern = Pattern.compile(STUDENT_PATTERN);
		Matcher studentMatcher = studentPattern.matcher(line);

		while (studentMatcher.find()) {
			String imie = studentMatcher.group(1);
			String nazwisko = studentMatcher.group(2);
			int wiek = Integer.parseInt(studentMatcher.group(3));

			Student newStudent = studentRepository.save(new Student(imie, nazwisko, wiek, newUni));

			//zapisz oceny stuenta
			Arrays.stream(studentMatcher.group(4).split(" "))
					.forEach(o -> ocenaRepository.save(new Ocena(Integer.parseInt(o), newStudent)));
		}
	}
}

