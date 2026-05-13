package com.notificaciones.notificaciones_service.repository;

import com.notificaciones.notificaciones_service.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByIdUsuarioDestino(Long idUsuarioDestino);

    List<Notificacion> findByIdUsuarioDestinoAndLeidaFalse(Long idUsuarioDestino);

    long countByIdUsuarioDestinoAndLeidaFalse(Long idUsuarioDestino);
}