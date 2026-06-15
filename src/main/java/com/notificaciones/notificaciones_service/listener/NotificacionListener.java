package com.notificaciones.notificaciones_service.listener;

import com.notificaciones.notificaciones_service.config.RabbitMQConfig;
import com.notificaciones.notificaciones_service.model.Notificacion;
import com.notificaciones.notificaciones_service.model.TipoNotificacion;
import com.notificaciones.notificaciones_service.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificacionListener {

    private final NotificacionService notificacionService;
    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = RabbitMQConfig.COLA_NOTIFICACIONES)
    public void procesarNuevaPublicacion(Map<String, Object> evento) {
        try {
            System.out.println("Evento recibido desde Publicaciones: " + evento);

            Long usuarioId = Long.valueOf(evento.get("usuarioId").toString());
            Long publicacionId = Long.valueOf(evento.get("publicacionId").toString());
            String mensaje = evento.get("mensaje") != null
                    ? evento.get("mensaje").toString()
                    : "Nueva publicación creada: " + publicacionId;

            Notificacion notificacion = new Notificacion();
            notificacion.setIdUsuarioDestino(usuarioId);
            notificacion.setTitulo("Nueva publicación");
            notificacion.setMensaje(mensaje);
            notificacion.setTipoNotificacion(TipoNotificacion.NUEVA_PUBLICACION);
            notificacion.setLeida(false);
            notificacion.setFechaCreacion(LocalDateTime.now());

            Notificacion guardada = notificacionService.guardar(notificacion);
            System.out.println("Notificacion guardada para usuario: " + usuarioId);

            messagingTemplate.convertAndSend(
                    "/topic/notificaciones/" + guardada.getIdUsuarioDestino(),
                    guardada);

        } catch (Exception e) {
            System.err.println("Error procesando mensaje RabbitMQ: " + e.getMessage());
        }
    }
}