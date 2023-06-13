package com.unisoma.Salary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuncionarioImpostoDTO {
    private String cpf;
    private String imposto;
}
