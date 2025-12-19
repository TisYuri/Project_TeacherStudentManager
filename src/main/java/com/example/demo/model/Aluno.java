package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Aluno extends User{
    @Column(nullable = false)
    private String matricula;

    public Aluno(String name, String cpf, String email, String telefone, Endereco endereco) {
        super(name, cpf, email, telefone, endereco);
    }
}
