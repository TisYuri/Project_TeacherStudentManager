package com.example.demo.controller;

import com.example.demo.exception.CpfDuplicadoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.model.Professor;
import com.example.demo.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {
    @Autowired
    ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos(){
        List<Professor> professores = professorService.listarTodos();
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id){
        try{
            Professor professor = professorService.buscarPorId(id);
            return ResponseEntity.ok(professor);
        }catch (RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint: GET /api/professores/cpf/{cpf}
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Professor> buscarPorCpf(@PathVariable String cpf){
        try{
            Professor professor = professorService.buscaPorCpf(cpf);
            return ResponseEntity.ok(professor);
        }catch(RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint: GET /api/professores/departamento/{departamento}
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<Professor>> buscarPorDepartamento(@PathVariable String departamento){
        try{
            List<Professor> professores = professorService.buscarPorDepartamento(departamento);
            return ResponseEntity.ok(professores);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody Professor professor){
        try{
            Professor professorSalvo = professorService.salvar(professor);
            return ResponseEntity.ok(professorSalvo);
        }catch(CpfDuplicadoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody Professor professor){
        try{
            Professor professorAtualizado = professorService.atualizar(id, professor);
            return ResponseEntity.ok(professorAtualizado);
        }catch(CpfDuplicadoException e){
            return ResponseEntity.badRequest().build();
        }
        catch (RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        boolean deletado = professorService.deletar(id);
        if(deletado){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
