package com.notificaciones.notificaciones_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoPublicacion {

    private Long id;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String estado;
    private Long usuarioId;
}
