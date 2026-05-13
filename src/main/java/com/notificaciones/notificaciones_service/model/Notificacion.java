package com.notificaciones.notificaciones_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuarioDestino;
    private String titulo;
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipoNotificacion;

    private boolean leida;
    private LocalDateTime fechaCreacion;
}