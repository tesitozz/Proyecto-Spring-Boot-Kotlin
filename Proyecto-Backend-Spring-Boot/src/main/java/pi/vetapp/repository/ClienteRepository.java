package pi.vetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.vetapp.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findByDni(String dni);

  Optional<Cliente> findByCorreo(String correo);

  List<Cliente> findByNombresContainingIgnoreCase(String nombres);

  List<Cliente> findByApellidosContainingIgnoreCase(String apellidos);

  List<Cliente> findByGeneroContainingIgnoreCase(String genero);

  List<Cliente> findByDireccionContainingIgnoreCase(String direccion);

  List<Cliente> findByCelularContainingIgnoreCase(String celular);
}
