package com.apreensao.veiculo.service;

import com.apreensao.veiculo.entity.Apreensao;
import com.apreensao.veiculo.entity.Proprietario;
import com.apreensao.veiculo.entity.Veiculo;
import com.apreensao.veiculo.repository.ProprietarioRepository;
import com.apreensao.veiculo.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApreensaoServiceIntegrationTest {

    @Autowired
    private ApreensaoService apreensaoService;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    private Proprietario proprietario;
    private Veiculo veiculo;

    @BeforeEach
    public void setup() {
        proprietario = new Proprietario();
        proprietario.setCpf("12345678901"); // CPF único
        // Preencha outros campos do proprietário
        proprietarioRepository.save(proprietario);

        veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234"); // Placa única
        veiculo.setModelo("Fusca");
        veiculo.setFabricante("Volkswagen");
        veiculo.setAno(1970);
        veiculo.setCor("Azul");
        veiculo.setProprietario(proprietario);
        veiculoRepository.save(veiculo);
    }

    @Test
    public void testSaveApreensao_Success() {
        Apreensao apreensao = new Apreensao();
        apreensao.setVeiculo(veiculo);
        // Preencha outros campos da apreensão
        apreensaoService.save(apreensao);

        assertNotNull(apreensao.getId());
    }

    @Test
    public void testSaveApreensao_DuplicatePlaca() {
        Apreensao apreensao1 = new Apreensao();
        apreensao1.setVeiculo(veiculo);
        // Preencha outros campos da apreensão
        apreensaoService.save(apreensao1);

        Apreensao apreensao2 = new Apreensao();
        apreensao2.setVeiculo(veiculo);
        // Preencha outros campos da apreensão

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            apreensaoService.save(apreensao2);
        });

        assertEquals("Placa do veículo já cadastrada.", exception.getMessage());
    }

    @Test
    public void testSaveApreensao_DuplicateCpf() {
        Proprietario proprietario2 = new Proprietario();
        proprietario2.setCpf("12345678901"); // CPF duplicado
        // Preencha outros campos do proprietário
        proprietarioRepository.save(proprietario2);

        Apreensao apreensao = new Apreensao();
        apreensao.setVeiculo(veiculo);
        // Preencha outros campos da apreensão

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            apreensaoService.save(apreensao);
        });

        assertEquals("CPF do proprietário já cadastrado.", exception.getMessage());
    }

    @Test
    public void testSaveApreensao_VeiculoAlreadyApreendido() {
        Apreensao apreensao1 = new Apreensao();
        apreensao1.setVeiculo(veiculo);
        // Preencha outros campos da apreensão
        apreensaoService.save(apreensao1);

        Apreensao apreensao2 = new Apreensao();
        apreensao2.setVeiculo(veiculo);
        // Preencha outros campos da apreensão

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            apreensaoService.save(apreensao2);
        });

        assertEquals("Veículo já está apreendido.", exception.getMessage());
    }
}
