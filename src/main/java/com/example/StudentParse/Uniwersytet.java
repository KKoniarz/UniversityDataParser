package com.example.StudentParse;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Uniwersytet")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class Uniwersytet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nazwa;
    private int liczbaStudentow;

    public Uniwersytet(String nazwa, int liczbaStudentow) {
        this.nazwa = nazwa;
        this.liczbaStudentow = liczbaStudentow;
    }
}
