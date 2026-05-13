package com.notificaciones.notificaciones_service.listener;

import com.notificaciones.notificaciones_service.config.RabbitMQConfig;
import com.notificaciones.notificaciones_service.model.EventoPublicacion;
import com.notificaciones.notificaciones_service.model.Notificacion;
import com.notificaciones.notificaciones_service.model.TipoNotificacion;
import com.notificaciones.notificaciones_service.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificacionListener {

    private final NotificacionService notificacionService;

    @RabbitListener(queues = RabbitMQConfig.COLA_NOTIFICACIONES)
    public void procesarNuevaPublicacion(EventoPublicacion evento) {
        System.out.println("Nueva publicacion recibida: " + evento.getTitulo());

        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuarioDestino(evento.getUsuarioId());
        notificacion.setTitulo("Nueva publicación cerca de ti");
        notificacion.setMensaje("Se publicó: " + evento.getTitulo());
        notificacion.setTipoNotificacion(TipoNotificacion.NUEVA_PUBLICACION);
        notificacion.setLeida(false);
        notificacion.setFechaCreacion(LocalDateTime.now());

        notificacionService.guardar(notificacion);
    }
}