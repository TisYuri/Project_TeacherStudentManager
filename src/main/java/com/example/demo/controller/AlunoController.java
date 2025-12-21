package com.example.demo.controller;

import com.example.demo.exception.CpfDuplicadoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.model.Aluno;
import com.example.demo.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @Operation(summary = "Lista todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos(){
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @Operation(summary = "Busca um aluno pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(
            @Parameter(description = "ID do aluno a ser buscado") @PathVariable Long id){
        try{
            Aluno aluno = alunoService.buscarPorId(id);
            return ResponseEntity.ok(aluno);
        }catch(RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca um aluno pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Aluno> buscarPorCpf(
            @Parameter(description = "CPF do aluno a ser buscado") @PathVariable String cpf){
        try{
            Aluno aluno = alunoService.buscarPorCpf(cpf);
            return ResponseEntity.ok(aluno);
        }catch(RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca um aluno pela matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> buscarPorMatricula(
            @Parameter(description = "Matrícula do aluno a ser buscado") @PathVariable String matricula){
        try{
            Aluno aluno = alunoService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(aluno);
        }catch (RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Cria um novo aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: CPF duplicado)")
    })
    @PostMapping
    public ResponseEntity<Aluno> criar(
            @RequestBody(description = "Dados do aluno a ser criado") @org.springframework.web.bind.annotation.RequestBody Aluno aluno){
        try{
            Aluno alunoSalvo = alunoService.salvar(aluno);
            return ResponseEntity.ok(alunoSalvo);
        }catch(CpfDuplicadoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Atualiza um aluno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(
            @Parameter(description = "ID do aluno a ser atualizado") @PathVariable Long id,
            @RequestBody(description = "Dados do aluno para atualização") @org.springframework.web.bind.annotation.RequestBody Aluno aluno){
        try{
            Aluno alunoAtualizado = alunoService.atualizar(id, aluno);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Deleta um aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do aluno a ser deletado") @PathVariable Long id){
        boolean deletado = alunoService.detelar(id);
        if(deletado){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
