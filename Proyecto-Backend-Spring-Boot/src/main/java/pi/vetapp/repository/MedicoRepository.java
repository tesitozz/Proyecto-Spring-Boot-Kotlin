package pi.vetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.vetapp.entity.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
  Optional<Medico> findByDni(String dni);

  List<Medico> findByNombresContainingIgnoreCase(String nombres);

  List<Medico> findByApellidosContainingIgnoreCase(String apellidos);

  List<Medico> findByEspecialidadContainingIgnoreCase(String especialidad);
}
