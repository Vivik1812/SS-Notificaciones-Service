package com.notificaciones.notificaciones_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String COLA_NOTIFICACIONES = "cola.notificaciones";
    public static final String EXCHANGE = "exchange.publicaciones";
    public static final String CLAVE_ENRUTAMIENTO = "publicacion.creada";

    @Bean
    public Queue cola() {
        return new Queue(COLA_NOTIFICACIONES, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding enlace(Queue cola, DirectExchange exchange) {
        return BindingBuilder.bind(cola).to(exchange).with(CLAVE_ENRUTAMIENTO);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
