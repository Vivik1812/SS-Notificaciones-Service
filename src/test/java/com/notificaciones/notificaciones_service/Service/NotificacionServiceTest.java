package com.notificaciones.notificaciones_service.service;

import com.notificaciones.notificaciones_service.model.Notificacion;
import com.notificaciones.notificaciones_service.model.TipoNotificacion;
import com.notificaciones.notificaciones_service.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setIdUsuarioDestino(1L);
        notificacion.setTitulo("Nueva publicación");
        notificacion.setMensaje("Se creó una publicación");
        notificacion.setTipoNotificacion(TipoNotificacion.NUEVA_PUBLICACION);
        notificacion.setLeida(false);
        notificacion.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void obtenerPorUsuario_debeRetornarLista() {
        when(notificacionRepository.findByIdUsuarioDestino(1L))
                .thenReturn(Arrays.asList(notificacion));

        List<Notificacion> resultado = notificacionService.obtenerPorUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(notificacionRepository).findByIdUsuarioDestino(1L);
    }

    @Test
    void obtenerNoLeidas_debeRetornarSoloNoLeidas() {
        when(notificacionRepository.findByIdUsuarioDestinoAndLeidaFalse(1L))
                .thenReturn(Arrays.asList(notificacion));

        List<Notificacion> resultado = notificacionService.obtenerNoLeidas(1L);

        assertNotNull(resultado);
        resultado.forEach(n -> assertFalse(n.isLeida()));
    }

    @Test
    void contarNoLeidas_debeRetornarConteo() {
        when(notificacionRepository.countByIdUsuarioDestinoAndLeidaFalse(1L))
                .thenReturn(3L);

        long resultado = notificacionService.contarNoLeidas(1L);

        assertEquals(3L, resultado);
    }

    @Test
    void marcarComoLeida_debeActualizarEstado() {
        when(notificacionRepository.findById(1L))
                .thenReturn(Optional.of(notificacion));
        when(notificacionRepository.save(any(Notificacion.class)))
                .thenReturn(notificacion);

        Notificacion resultado = notificacionService.marcarComoLeida(1L);

        assertTrue(resultado.isLeida());
        verify(notificacionRepository).save(notificacion);
    }

    @Test
    void guardar_debeGuardarNotificacion() {
        when(notificacionRepository.save(notificacion))
                .thenReturn(notificacion);

        Notificacion resultado = notificacionService.guardar(notificacion);

        assertNotNull(resultado);
        assertEquals("Nueva publicación", resultado.getTitulo());
    }
}