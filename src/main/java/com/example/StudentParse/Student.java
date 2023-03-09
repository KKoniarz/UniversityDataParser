package com.example.StudentParse;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imie;
    private String nazwisko;
    private int wiek;

    @ManyToOne
    @JoinColumn(name = "uniwersytet_id")
    private Uniwersytet uniwersytet;

    public Student(String imie, String nazwisko, int wiek, Uniwersytet uniwersytet) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
        this.uniwersytet = uniwersytet;
    }
}
