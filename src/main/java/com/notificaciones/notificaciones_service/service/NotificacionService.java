package com.notificaciones.notificaciones_service.service;

import com.notificaciones.notificaciones_service.model.Notificacion;
import com.notificaciones.notificaciones_service.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerPorUsuario(Long usuarioId) {
        return notificacionRepository.findByIdUsuarioDestino(usuarioId);
    }

    public List<Notificacion> obtenerNoLeidas(Long usuarioId) {
        return notificacionRepository.findByIdUsuarioDestinoAndLeidaFalse(usuarioId);
    }

    public long contarNoLeidas(Long usuarioId) {
        return notificacionRepository.countByIdUsuarioDestinoAndLeidaFalse(usuarioId);
    }

    public Notificacion marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    public void marcarTodasComoLeidas(Long usuarioId) {
        List<Notificacion> noLeidas = notificacionRepository
                .findByIdUsuarioDestinoAndLeidaFalse(usuarioId);
        noLeidas.forEach(n -> n.setLeida(true));
        notificacionRepository.saveAll(noLeidas);
    }

    public Notificacion guardar(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }
}