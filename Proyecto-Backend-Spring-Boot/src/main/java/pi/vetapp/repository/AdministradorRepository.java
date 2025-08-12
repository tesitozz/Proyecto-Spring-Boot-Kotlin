package pi.vetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.vetapp.entity.Administrador;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
  Optional<Administrador> findByUsuario(String usuario);

  Optional<Administrador> findByCorreo(String correo);

  Optional<Administrador> findByUsuarioAndPassword(String usuario, String password);
}
