package com.unisoma.Salary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class FuncionarioListDTO implements Serializable {
    private String          nome;
    private String          cpf;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate       dataNascimento;
    private String          telefone;
    private String          endereco;
    private Double          salario;
    private Double          reajuste;
    private Double          percentual;
    private LocalDateTime   ultimaAltSalarial;

}
