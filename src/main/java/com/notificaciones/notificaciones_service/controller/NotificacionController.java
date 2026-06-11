package com.notificaciones.notificaciones_service.controller;

import com.notificaciones.notificaciones_service.model.Notificacion;
import com.notificaciones.notificaciones_service.security.JwtUtil;
import com.notificaciones.notificaciones_service.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final JwtUtil jwtUtil;

    private boolean esUsuarioAutorizado(String authHeader, Long usuarioId) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return false;
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token))
            return false;
        String idDelToken = jwtUtil.extractUserId(token);
        return idDelToken.equals(String.valueOf(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerPorUsuario(
            @PathVariable Long usuarioId,
            @RequestHeader("Authorization") String authHeader) {
        if (!esUsuarioAutorizado(authHeader, usuarioId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(notificacionService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<Notificacion>> obtenerNoLeidas(
            @PathVariable Long usuarioId,
            @RequestHeader("Authorization") String authHeader) {
        if (!esUsuarioAutorizado(authHeader, usuarioId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(notificacionService.obtenerNoLeidas(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/contador")
    public ResponseEntity<Long> contarNoLeidas(
            @PathVariable Long usuarioId,
            @RequestHeader("Authorization") String authHeader) {
        if (!esUsuarioAutorizado(authHeader, usuarioId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(notificacionService.contarNoLeidas(usuarioId));
    }

    @PutMapping("/{id}/leida")
    public ResponseEntity<Notificacion> marcarComoLeida(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }

    @PutMapping("/usuario/{usuarioId}/leer-todas")
    public ResponseEntity<Void> marcarTodasComoLeidas(
            @PathVariable Long usuarioId,
            @RequestHeader("Authorization") String authHeader) {
        if (!esUsuarioAutorizado(authHeader, usuarioId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }
}