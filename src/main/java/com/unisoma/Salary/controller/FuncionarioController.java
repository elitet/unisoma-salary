package com.unisoma.Salary.controller;

import com.unisoma.Salary.dto.FuncionarioDTO;
import com.unisoma.Salary.dto.FuncionarioImpostoDTO;
import com.unisoma.Salary.dto.FuncionarioSalaryDTO;
import com.unisoma.Salary.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<?> listFuncionario() {
        return new ResponseEntity<>(funcionarioService.getAllFuncionarios(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        return new ResponseEntity<>(funcionarioService.createFuncionario(funcionarioDTO), HttpStatus.OK);
    }

    @PutMapping("/{cpf}/salary")
    public FuncionarioSalaryDTO updateSalary(@PathVariable String cpf) {
        return funcionarioService.updateSalary(cpf);
    }

    @GetMapping("/{cpf}/tax")
    public FuncionarioImpostoDTO calculaImposto(@PathVariable String cpf) {
        return funcionarioService.calculaImposto(cpf);
    }
}

