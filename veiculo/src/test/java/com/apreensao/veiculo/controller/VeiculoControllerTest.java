package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.Veiculo;
import com.apreensao.veiculo.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VeiculoControllerTest {

    @InjectMocks
    private VeiculoController veiculoController;

    @Mock
    private VeiculoService veiculoService;

    private Veiculo veiculo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC-1234");
        // Preencher outros campos conforme necessário
    }

    @Test
    public void testCreateVeiculo() {
        when(veiculoService.save(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity<Veiculo> response = veiculoController.createVeiculo(veiculo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
        verify(veiculoService, times(1)).save(veiculo);
    }

    @Test
    public void testGetAllVeiculos() {
        when(veiculoService.findAll()).thenReturn(Collections.singletonList(veiculo));

        ResponseEntity<List<Veiculo>> response = (ResponseEntity<List<Veiculo>>) veiculoController.getAllVeiculos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(veiculoService, times(1)).findAll();
    }

    @Test
    public void testGetVeiculoById_Found() {
        when(veiculoService.findById(1L)).thenReturn(Optional.of(veiculo));

        ResponseEntity<Veiculo> response = veiculoController.getVeiculoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
        verify(veiculoService, times(1)).findById(1L);
    }

    @Test
    public void testGetVeiculoById_NotFound() {
        when(veiculoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Veiculo> response = veiculoController.getVeiculoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(veiculoService, times(1)).findById(1L);
    }

    @Test
    public void testUpdateVeiculo_Found() {
        when(veiculoService.findById(1L)).thenReturn(Optional.of(veiculo));
        when(veiculoService.save(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity<Veiculo> response = veiculoController.updateVeiculo(1L, veiculo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
        verify(veiculoService, times(1)).findById(1L);
        verify(veiculoService, times(1)).save(veiculo);
    }

    @Test
    public void testUpdateVeiculo_NotFound() {
        when(veiculoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Veiculo> response = veiculoController.updateVeiculo(1L, veiculo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(veiculoService, times(1)).findById(1L);
        verify(veiculoService, never()).save(any(Veiculo.class));
    }

    @Test
    public void testDeleteVeiculo_Found() {
        when(veiculoService.findById(1L)).thenReturn(Optional.of(veiculo));

        ResponseEntity<Void> response = veiculoController.deleteVeiculo(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(veiculoService, times(1)).findById(1L);
        verify(veiculoService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteVeiculo_NotFound() {
        when(veiculoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = veiculoController.deleteVeiculo(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(veiculoService, times(1)).findById(1L);
        verify(veiculoService, never()).deleteById(anyLong());
    }
}
