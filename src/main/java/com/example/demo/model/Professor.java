package com.example.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Professor extends User{
    private String departamento;

    public Professor(String name, String cpf, String email, String telefone, Endereco endereco) {
        super(name, cpf, email, telefone, endereco);
    }
}
