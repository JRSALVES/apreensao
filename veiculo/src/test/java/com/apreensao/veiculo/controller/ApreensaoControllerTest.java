package com.apreensao.veiculo.controller;

import com.apreensao.veiculo.entity.Apreensao;
import com.apreensao.veiculo.service.ApreensaoService;
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

public class ApreensaoControllerTest {

    @InjectMocks
    private ApreensaoController apreensaoController;

    @Mock
    private ApreensaoService apreensaoService;

    private Apreensao apreensao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        apreensao = new Apreensao();
        apreensao.setId(1L);
        // Preencher outros campos conforme necessário
    }

    @Test
    public void testCreateApreensao() {
        when(apreensaoService.save(any(Apreensao.class))).thenReturn(apreensao);

        ResponseEntity<?> response = apreensaoController.createApreensao(apreensao);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(apreensao, response.getBody());
        verify(apreensaoService, times(1)).save(apreensao);
    }

    @Test
    public void testGetAllApreensoes() {
        when(apreensaoService.findAll()).thenReturn(Collections.singletonList(apreensao));

        ResponseEntity<List<Apreensao>> response = apreensaoController.getAllApreensoes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(apreensaoService, times(1)).findAll();
    }

    @Test
    public void testGetApreensaoById_Found() {
        when(apreensaoService.findById(1L)).thenReturn(Optional.of(apreensao));

        ResponseEntity<Apreensao> response = apreensaoController.getApreensaoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apreensao, response.getBody());
        verify(apreensaoService, times(1)).findById(1L);
    }

    @Test
    public void testGetApreensaoById_NotFound() {
        when(apreensaoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Apreensao> response = apreensaoController.getApreensaoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(apreensaoService, times(1)).findById(1L);
    }

    @Test
    public void testUpdateApreensao_Found() {
        when(apreensaoService.findById(1L)).thenReturn(Optional.of(apreensao));
        when(apreensaoService.save(any(Apreensao.class))).thenReturn(apreensao);

        ResponseEntity<Apreensao> response = apreensaoController.updateApreensao(1L, apreensao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apreensao, response.getBody());
        verify(apreensaoService, times(1)).findById(1L);
        verify(apreensaoService, times(1)).save(apreensao);
    }

    @Test
    public void testUpdateApreensao_NotFound() {
        when(apreensaoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Apreensao> response = apreensaoController.updateApreensao(1L, apreensao);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(apreensaoService, times(1)).findById(1L);
        verify(apreensaoService, never()).save(any(Apreensao.class));
    }

    @Test
    public void testDeleteApreensao_Found() {
        when(apreensaoService.findById(1L)).thenReturn(Optional.of(apreensao));

        ResponseEntity<HttpStatus> response = apreensaoController.deleteApreensao(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(apreensaoService, times(1)).findById(1L);
        verify(apreensaoService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteApreensao_NotFound() {
        when(apreensaoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = apreensaoController.deleteApreensao(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(apreensaoService, times(1)).findById(1L);
        verify(apreensaoService, never()).deleteById(anyLong());
    }
}
