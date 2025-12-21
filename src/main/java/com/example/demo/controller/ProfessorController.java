package com.example.demo.controller;

import com.example.demo.exception.CpfDuplicadoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.model.Professor;
import com.example.demo.service.ProfessorService;
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
@RequestMapping("/api/professores")
public class ProfessorController {
    @Autowired
    ProfessorService professorService;

    @Operation(summary = "Lista todos os professores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de professores retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos(){
        List<Professor> professores = professorService.listarTodos();
        return ResponseEntity.ok(professores);
    }

    @Operation(summary = "Busca um professor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(
            @Parameter(description = "ID do professor a ser buscado") @PathVariable Long id){
        try{
            Professor professor = professorService.buscarPorId(id);
            return ResponseEntity.ok(professor);
        }catch (RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca um professor pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Professor> buscarPorCpf(
            @Parameter(description = "CPF do professor a ser buscado") @PathVariable String cpf){
        try{
            Professor professor = professorService.buscaPorCpf(cpf);
            return ResponseEntity.ok(professor);
        }catch(RecursoNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Busca professores por departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum professor encontrado para o departamento")
    })
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<Professor>> buscarPorDepartamento(
            @Parameter(description = "Departamento a ser buscado") @PathVariable String departamento){
        try{
            List<Professor> professores = professorService.buscarPorDepartamento(departamento);
            return ResponseEntity.ok(professores);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Cria um novo professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: CPF duplicado)")
    })
    @PostMapping
    public ResponseEntity<Professor> criar(
            @RequestBody(description = "Dados do professor a ser criado") @org.springframework.web.bind.annotation.RequestBody Professor professor){
        try{
            Professor professorSalvo = professorService.salvar(professor);
            return ResponseEntity.ok(professorSalvo);
        }catch(CpfDuplicadoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Atualiza um professor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: CPF duplicado)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(
            @Parameter(description = "ID do professor a ser atualizado") @PathVariable Long id,
            @RequestBody(description = "Dados do professor para atualização") @org.springframework.web.bind.annotation.RequestBody Professor professor){
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

    @Operation(summary = "Deleta um professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do professor a ser deletado") @PathVariable Long id){
        boolean deletado = professorService.deletar(id);
        if(deletado){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
