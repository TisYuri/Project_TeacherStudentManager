package com.example.demo.service;

import com.example.demo.dto.ViaCepResponse;
import com.example.demo.exception.CpfDuplicadoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.model.Endereco;
import com.example.demo.model.Professor;
import com.example.demo.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    ViaCepService viaCepService;

    public List<Professor> listarTodos(){
        return professorRepository.findAll();
    }

    public Professor buscarPorId(Long id){
        Optional<Professor> professor = professorRepository.findById(id);

        if(professor.isPresent()){
            return professor.get();
        }
        else{
            throw new RecursoNaoEncontradoException("Professor não encontrado com ID: " + id);
        }
    }

    public Professor buscaPorCpf(String cpf){
        Optional<Professor> professor = professorRepository.findByCpf(cpf);
        if(professor.isPresent()){
            return professor.get();
        }
        else{
            throw new RecursoNaoEncontradoException("Professor não encontrado com CPF: " + cpf);
        }
    }

    public List<Professor> buscarPorDepartamento(String departamento){
        List<Professor> professores = professorRepository.findAllByDepartamento(departamento);
        if(!professores.isEmpty()){
            return professores;
        }
        else{
            throw new RecursoNaoEncontradoException("Nenhum professor encontrado no departamento: " + departamento);
        }
    }

    public Professor salvar(Professor professor){
        Optional<Professor> professorExistente = professorRepository.findByCpf(professor.getCpf());

        if(professorExistente.isPresent() && !professorExistente.get().getId().equals(professor.getId())){
            throw new CpfDuplicadoException("CPF já cadastrado: " + professor.getCpf());
        }
        
        Endereco endereco = professor.getEndereco();
        if(endereco != null && endereco.getCep() != null && !endereco.getCep().isEmpty()){
            try{
                ViaCepResponse dadosCep = viaCepService.bucarEnderecoPorCep(professor.getEndereco().getCep());

                endereco.setBairro(dadosCep.getBairro());
                endereco.setCidade(dadosCep.getCidade());
                endereco.setEstado(dadosCep.getEstado());
                endereco.setLogradouro(dadosCep.getLogradouro());

            }catch(Exception e){
                throw new RuntimeException("Erro ao buscar endereço para o CEP: " + professor.getEndereco().getCep(), e);
            }
        }
        return professorRepository.save(professor);
    }

    public Professor atualizar(Long id, Professor professorAtualizado){
        Optional<Professor> professorExistente = professorRepository.findById(id);
        if(professorExistente.isPresent()){
            professorAtualizado.setId(id);

            Optional<Professor> professorComMesmoCpf = professorRepository.findByCpf(professorAtualizado.getCpf());
            if(professorComMesmoCpf.isPresent() && !professorComMesmoCpf.get().getId().equals(id)){
                throw new CpfDuplicadoException("CPF já cadastrado: " + professorAtualizado.getCpf());
            }
            return professorRepository.save(professorAtualizado);
        }
        else{
            throw new RecursoNaoEncontradoException("Professor não encontrado com ID: " + id);
        }
    }

    public boolean deletar(Long id){
        Optional<Professor> professor = professorRepository.findById(id);
        if(professor.isPresent()){
            professorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}