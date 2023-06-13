package com.unisoma.Salary.service;

import com.unisoma.Salary.dto.FuncionarioDTO;
import com.unisoma.Salary.dto.FuncionarioImpostoDTO;
import com.unisoma.Salary.dto.FuncionarioSalaryDTO;
import com.unisoma.Salary.exception.ApiException;
import com.unisoma.Salary.model.Funcionario;
import com.unisoma.Salary.repository.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFuncionario() {
        // Given
        FuncionarioDTO dto = FuncionarioDTO.builder()
                .cpf("12345678901")
                .nome("Test")
                .dataNascimento(LocalDate.now())
                .telefone("1111-1111")
                .endereco("Test Street, 123")
                .salario(2000.0)
                .build();

        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        // When
        FuncionarioDTO result = funcionarioService.createFuncionario(dto);

        // Then
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
        Assertions.assertEquals(dto, result);
    }

    @Test
    void testCreateFuncionarioAlreadyRegistered() {
        // Given
        FuncionarioDTO dto = FuncionarioDTO.builder()
                .cpf("12345678901")
                .nome("Test")
                .dataNascimento(LocalDate.now())
                .telefone("1111-1111")
                .endereco("Test Street, 123")
                .salario(2000.0)
                .build();

        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.of(new Funcionario()));

        // When/Then
        Assertions.assertThrows(ApiException.class, () -> funcionarioService.createFuncionario(dto));
        verify(funcionarioRepository, never()).save(any(Funcionario.class));
    }

    @Test
    void testUpdateSalary() {
        // Given
        String cpf = "12345678901";
        Funcionario funcionario = Funcionario.builder()
                .salario(1000.0)
                .build();

        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.of(funcionario));

        // When
        FuncionarioSalaryDTO result = funcionarioService.updateSalary(cpf);

        // Then
        verify(funcionarioRepository, times(1)).save(funcionario);
        Assertions.assertEquals(cpf, result.getCpf());
        // Adicione aqui as demais verificações do resultado esperado
    }

    @Test
    void testUpdateSalaryNotFound() {
        // Given
        String cpf = "12345678901";

        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        // When/Then
        Assertions.assertThrows(ApiException.class, () -> funcionarioService.updateSalary(cpf));
        verify(funcionarioRepository, never()).save(any(Funcionario.class));
    }

    @Test
    void testCalculaImposto() {
        // Given
        String cpf = "12345678901";
        double salario = 2500.0;
        Funcionario funcionario = Funcionario.builder()
                .salario(salario)
                .build();

        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.of(funcionario));

        // When
        FuncionarioImpostoDTO result = funcionarioService.calculaImposto(cpf);

        // Then
        verify(funcionarioRepository, never()).save(any(Funcionario.class));
        Assertions.assertEquals(cpf, result.getCpf());
        // Adicione aqui as demais verificações do resultado esperado
    }

    @Test
    void testCalculaImpostoNotFound() {
        // Given
        String cpf = "12345678901";

        when(funcionarioRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        // When/Then
        Assertions.assertThrows(ApiException.class, () -> funcionarioService.calculaImposto(cpf));
        verify(funcionarioRepository, never()).save(any(Funcionario.class));
    }
}
