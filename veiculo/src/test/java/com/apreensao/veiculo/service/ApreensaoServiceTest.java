package com.apreensao.veiculo.service;

import com.apreensao.veiculo.entity.Apreensao;
import com.apreensao.veiculo.entity.Proprietario;
import com.apreensao.veiculo.entity.Veiculo;
import com.apreensao.veiculo.repository.ApreensaoRepository;
import com.apreensao.veiculo.repository.ProprietarioRepository;
import com.apreensao.veiculo.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApreensaoServiceTest {

    @InjectMocks
    private ApreensaoService apreensaoService;

    @Mock
    private ApreensaoRepository apreensaoRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ProprietarioRepository proprietarioRepository;

    private Proprietario proprietario;
    private Veiculo veiculo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        proprietario = new Proprietario();
        proprietario.setCpf("12345678901");

        veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setProprietario(proprietario);
    }

    @Test
    public void testSaveApreensao_Success() {
        Apreensao apreensao = new Apreensao();
        apreensao.setVeiculo(veiculo);

        when(veiculoRepository.existsByPlaca(veiculo.getPlaca())).thenReturn(false);
        when(proprietarioRepository.existsByCpf(proprietario.getCpf())).thenReturn(false);
        when(apreensaoRepository.save(apreensao)).thenReturn(apreensao);

        Apreensao result = apreensaoService.save(apreensao);

        assertNotNull(result);
        verify(apreensaoRepository, times(1)).save(apreensao);
    }

    @Test
    public void testSaveApreensao_DuplicatePlaca() {
        Apreensao apreensao = new Apreensao();
        apreensao.setVeiculo(veiculo);

        when(veiculoRepository.existsByPlaca(veiculo.getPlaca())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            apreensaoService.save(apreensao);
        });

        assertEquals("Placa do veículo já cadastrada.", exception.getMessage());
        verify(apreensaoRepository, never()).save(apreensao);
    }

    @Test
    public void testSaveApreensao_DuplicateCpf() {
        Proprietario proprietario2 = new Proprietario();
        proprietario2.setCpf("12345678901");

        when(veiculoRepository.existsByPlaca(veiculo.getPlaca())).thenReturn(false);
        when(proprietarioRepository.existsByCpf(proprietario.getCpf())).thenReturn(true);

        Apreensao apreensao = new Apreensao();
        apreensao.setVeiculo(veiculo);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            apreensaoService.save(apreensao);
        });

        assertEquals("CPF do proprietário já cadastrado.", exception.getMessage());
        verify(apreensaoRepository, never()).save(apreensao);
    }

    @Test
    public void testSaveApreensao_VeiculoAlreadyApreendido() {
        Apreensao apreensao1 = new Apreensao();
        apreensao1.setVeiculo(veiculo);

        when(veiculoRepository.existsByPlaca(veiculo.getPlaca())).thenReturn(false);
        when(proprietarioRepository.existsByCpf(proprietario.getCpf())).thenReturn(false);
        when(apreensaoRepository.save(apreensao1)).thenReturn(apreensao1);

        apreensaoService.save(apreensao1);

        Apreensao apreensao2 = new Apreensao();
        apreensao2.setVeiculo(veiculo);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            apreensaoService.save(apreensao2);
        });

        assertEquals("Veículo já está apreendido.", exception.getMessage());
        verify(apreensaoRepository, never()).save(apreensao2);
    }
}
