package pi.vetapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pi.vetapp.entity.LoginRequest;
import pi.vetapp.entity.Administrador;
import pi.vetapp.service.AdministradorService;

import java.util.Optional;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {
  @Autowired
  private AdministradorService usuarioService;

  @PostMapping("/register")
  public ResponseEntity<?> registrarUsuario(@RequestBody Administrador usuario) {
    try {
      Administrador nuevoUsuario = usuarioService.registrarUsuario(usuario);
      return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    Optional<Administrador> usuario = usuarioService.login(loginRequest.getUsuario(), loginRequest.getPassword());
    if (usuario.isPresent()) {
      return ResponseEntity.ok(usuario.get());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
  }
}
