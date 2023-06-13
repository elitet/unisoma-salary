package com.unisoma.Salary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class FuncionarioDTO implements Serializable {
    private String    nome;
    private String    cpf;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNascimento;
    private String    telefone;
    private String    endereco;
    private Double    salario;

}
