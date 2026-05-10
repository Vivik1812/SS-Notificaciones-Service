package com.notificaciones.notificaciones_service.listener;

import com.notificaciones.notificaciones_service.model.EventoPublicacion;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.notificaciones.notificaciones_service.config.RabbitMQConfig;

@Component
public class NotificacionListener {

    @RabbitListener(queues = RabbitMQConfig.COLA_NOTIFICACIONES)
    public void procesarNuevaPublicacion(EventoPublicacion evento) {
        System.out.println("Nueva publicacion recibida:");
        System.out.println("   Titulo:" + evento.getTitulo());
        System.out.println("   Tipo: " + evento.getTipo());
        System.out.println("   Estado: " + evento.getEstado());
        System.out.println("   Usuario ID: " + evento.getUsuarioId());
    }
}
