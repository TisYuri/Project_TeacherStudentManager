package com.example.demo.controller;

import com.example.demo.exception.CpfDuplicadoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.model.Aluno;
import com.example.demo.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    // Endpoint: GET /api/alunos
    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos(){
        List<Aluno> alunos = alunoService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    // Endpoint: GET /api/alunos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id){
        try{
            Aluno aluno = alunoService.buscarPorId(id);
            return ResponseEntity.ok(aluno);
        }catch(RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint: GET /api/alunos/cpf/{cpf}
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Aluno> buscarPorCpf(@PathVariable String cpf){
        try{
            Aluno aluno = alunoService.buscarPorCpf(cpf);
            return ResponseEntity.ok(aluno);
        }catch(RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint: GET /api/alunos/matricula/{matricula}
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable String matricula){
        try{
            Aluno aluno = alunoService.buscarPorMatricula(matricula);
            return ResponseEntity.ok(aluno);
        }catch (RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint: POST /api/alunos
    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno){
        try{
            Aluno alunoSalvo = alunoService.salvar(aluno);
            return ResponseEntity.ok(alunoSalvo);
        }catch(CpfDuplicadoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint: PUT /api/alunos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno){
        try{
            Aluno alunoAtualizado = alunoService.atualizar(id, aluno);
            return ResponseEntity.ok(aluno);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint: DELETE /api/alunos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        boolean deletado = alunoService.detelar(id);
        if(deletado){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
