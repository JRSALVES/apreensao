package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.Agente;
import com.apreensao.veiculo.service.AgenteService;
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

public class AgenteControllerTest {

    @InjectMocks
    private AgenteController agenteController;

    @Mock
    private AgenteService agenteService;

    private Agente agente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        agente = new Agente();
        agente.setId(1L);
        agente.setNome("Agente 1");
        // Preencher outros campos conforme necessário
    }

    @Test
    public void testCreateAgente() {
        when(agenteService.save(any(Agente.class))).thenReturn(agente);

        ResponseEntity<Agente> response = agenteController.createAgente(agente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(agente, response.getBody());
        verify(agenteService, times(1)).save(agente);
    }

    @Test
    public void testGetAllAgentes() {
        when(agenteService.findAll()).thenReturn(Collections.singletonList(agente));

        ResponseEntity<List<Agente>> response = (ResponseEntity<List<Agente>>) agenteController.getAllAgentes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(agenteService, times(1)).findAll();
    }

    @Test
    public void testGetAgenteById_Found() {
        when(agenteService.findById(1L)).thenReturn(Optional.of(agente));

        ResponseEntity<Agente> response = agenteController.getAgenteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agente, response.getBody());
        verify(agenteService, times(1)).findById(1L);
    }

    @Test
    public void testGetAgenteById_NotFound() {
        when(agenteService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Agente> response = agenteController.getAgenteById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(agenteService, times(1)).findById(1L);
    }

    @Test
    public void testUpdateAgente_Found() {
        when(agenteService.findById(1L)).thenReturn(Optional.of(agente));
        when(agenteService.save(any(Agente.class))).thenReturn(agente);

        ResponseEntity<Agente> response = agenteController.updateAgente(1L, agente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agente, response.getBody());
        verify(agenteService, times(1)).findById(1L);
        verify(agenteService, times(1)).save(agente);
    }

    @Test
    public void testUpdateAgente_NotFound() {
        when(agenteService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Agente> response = agenteController.updateAgente(1L, agente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(agenteService, times(1)).findById(1L);
        verify(agenteService, never()).save(any(Agente.class));
    }

    @Test
    public void testDeleteAgente_Found() {
        when(agenteService.findById(1L)).thenReturn(Optional.of(agente));

        ResponseEntity<Void> response = agenteController.deleteAgente(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(agenteService, times(1)).findById(1L);
        verify(agenteService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAgente_NotFound() {
        when(agenteService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = agenteController.deleteAgente(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(agenteService, times(1)).findById(1L);
        verify(agenteService, never()).deleteById(anyLong());
    }
}
