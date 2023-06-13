package com.unisoma.Salary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuncionarioSalaryDTO {
    private String cpf;
    private double novoSalario;
    private double valorReajuste;
    private double percentualReajuste;
}

