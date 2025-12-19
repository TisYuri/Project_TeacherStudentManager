package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Professor extends User{
    @Column(nullable = false)
    private String departamento;

    public Professor(String name, String cpf, String email, String telefone, Endereco endereco) {
        super(name, cpf, email, telefone, endereco);
    }
}
