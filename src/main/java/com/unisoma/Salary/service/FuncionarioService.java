package com.unisoma.Salary.service;

import com.unisoma.Salary.dto.FuncionarioDTO;
import com.unisoma.Salary.dto.FuncionarioImpostoDTO;
import com.unisoma.Salary.dto.FuncionarioListDTO;
import com.unisoma.Salary.dto.FuncionarioSalaryDTO;
import com.unisoma.Salary.exception.ApiException;
import com.unisoma.Salary.model.Funcionario;
import com.unisoma.Salary.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioDTO createFuncionario(FuncionarioDTO funcionarioDTO) {

        if(funcionarioRepository.findByCpf(funcionarioDTO.getCpf()).isPresent()){
            throw new ApiException("Employer already registered");
        }

        Funcionario funcionario = Funcionario.builder()
                .nome(funcionarioDTO.getNome())
                .cpf(funcionarioDTO.getCpf())
                .dataNascimento(funcionarioDTO.getDataNascimento())
                .telefone(funcionarioDTO.getTelefone())
                .endereco(funcionarioDTO.getEndereco())
                .salario(funcionarioDTO.getSalario())
                .build();

        funcionarioRepository.save(funcionario);

        return funcionarioDTO;
    }

    @Transactional
    public FuncionarioSalaryDTO updateSalary(String cpf) {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new ApiException("Employer with CPF " + cpf + " not found!"));

        double oldSalary = funcionario.getSalario();
        double percentual = calculateRaisePercentual(oldSalary);

        double raiseAmount = oldSalary * percentual;
        double newSalary = Math.round((oldSalary + raiseAmount) * 100.0) / 100.0;

        funcionario.setSalario(newSalary);
        funcionario.setReajuste(Math.round(raiseAmount * 100.0) / 100.0);
        funcionario.setPercentual(percentual * 100);
        funcionario.setDataUltimaAtualizacao(LocalDateTime.now());

        funcionarioRepository.save(funcionario);

        return FuncionarioSalaryDTO.builder()
                .cpf(cpf)
                .novoSalario(newSalary)
                .valorReajuste(Math.round(raiseAmount * 100.0) / 100.0)
                .percentualReajuste(Math.round(percentual * 100))
                .build();
    }

    public FuncionarioImpostoDTO calculaImposto(String cpf) {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new ApiException("Employer with cpf " + cpf + " not found"));

        double salario = funcionario.getSalario();
        String imposto;

        if (salario <= 2000.00) {
            imposto = "ISENTO";
        } else if (salario <= 3000.00) {
            imposto = String.format("R$ %.2f", salario * 0.08);
        } else if (salario <= 4500.00) {
            imposto = String.format("R$ %.2f", salario * 0.18);
        } else {
            imposto = String.format("R$ %.2f", salario * 0.28);
        }

        return FuncionarioImpostoDTO.builder()
                .cpf(cpf)
                .imposto(imposto)
                .build();
    }

    public List<FuncionarioListDTO> getAllFuncionarios() {
        return funcionarioRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private FuncionarioListDTO convertToDto(Funcionario funcionario) {
        return FuncionarioListDTO.builder()
                .nome(funcionario.getNome())
                .cpf(funcionario.getCpf())
                .dataNascimento(funcionario.getDataNascimento())
                .telefone(funcionario.getTelefone())
                .endereco(funcionario.getEndereco())
                .salario(funcionario.getSalario())
                .reajuste(funcionario.getReajuste())
                .percentual(funcionario.getPercentual())
                .ultimaAltSalarial(funcionario.getDataUltimaAtualizacao())
                .build();
    }

    private double calculateRaisePercentual(double oldSalary) {
        if (oldSalary <= 400.00) {
            return 0.15;
        } else if (oldSalary <= 800.00) {
            return 0.12;
        } else if (oldSalary <= 1200.00) {
            return 0.10;
        } else if (oldSalary <= 2000.00) {
            return 0.07;
        } else {
            return 0.04;
        }
    }



}


