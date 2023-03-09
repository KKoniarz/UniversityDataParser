package com.example.StudentParse;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Oceny")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ocena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ocena;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Ocena(int ocena, Student student) {
        this.ocena = ocena;
        this.student = student;
    }
}
