package com.example.demo.service;

import com.example.demo.dto.ViaCepResponse;
import com.example.demo.exception.CpfDuplicadoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.model.Aluno;
import com.example.demo.model.Endereco;
import com.example.demo.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    ViaCepService viaCepService;

    public List<Aluno> listarTodos(){
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if(aluno.isPresent()){
            return aluno.get();
        }
        else{
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
    }

    public Aluno buscarPorCpf(String cpf){
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);
        if(aluno.isPresent()){
            return aluno.get();
        }
        else{
            throw new RecursoNaoEncontradoException("Aluno não encontrado com CPF: " + cpf);
        }
    }

    public Aluno buscarPorMatricula(String matricula){
        Optional<Aluno> aluno = alunoRepository.findByMatricula(matricula);
        if(aluno.isPresent()){
            return aluno.get();
        }
        else{
            throw new RecursoNaoEncontradoException("Aluno não encontrado com Matricula: " + matricula);
        }
    }

    public Aluno salvar(Aluno aluno){
        Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());
        if(alunoExistente.isPresent() && !alunoExistente.get().getId().equals(aluno.getId())){
            throw new CpfDuplicadoException("CPF já cadastrado: " + aluno.getCpf());
        }
        Endereco endereco = aluno.getEndereco();
        if(endereco != null && endereco.getCep() != null && !endereco.getCep().isEmpty()){
            try{
                ViaCepResponse dadosCep = viaCepService.bucarEnderecoPorCep(aluno.getEndereco().getCep());

                endereco.setBairro(dadosCep.getBairro());
                endereco.setCidade(dadosCep.getCidade());
                endereco.setEstado(dadosCep.getEstado());
                endereco.setLogradouro(dadosCep.getLogradouro());

            }catch(Exception e){
                throw new RuntimeException("Erro ao buscar endereço para o CEP: " + aluno.getEndereco().getCep(), e);
            }
        }
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return alunoSalvo;
    }

    public Aluno atualizar(Long id, Aluno alunoAtualizado){
        Optional<Aluno> alunoExistente = alunoRepository.findById(id);
        if(alunoExistente.isPresent()){
            alunoAtualizado.setId(id);

            Optional<Aluno> alunoComMesmoCpf = alunoRepository.findByCpf(alunoAtualizado.getCpf());
            if(alunoComMesmoCpf.isPresent() && !alunoComMesmoCpf.get().getId().equals(id)){
                throw new CpfDuplicadoException("CPF já cadastrado: " + alunoAtualizado.getCpf());
            }
            return alunoRepository.save(alunoAtualizado);
        }
        else{
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
    }

    public boolean detelar(Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if(aluno.isPresent()){
            alunoRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }


}
