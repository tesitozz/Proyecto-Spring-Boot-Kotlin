package pi.vetapp.service;

import pi.vetapp.entity.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoService {
  List<Medico> getAll();

  Medico create(Medico medico);

  Optional<Medico> update(Medico medico);

  Optional<Medico> updateNombres(Long id, String nombres);

  Optional<Medico> updateApellidos(Long id, String apellidos);

  Optional<Medico> updateEspecialidad(Long id, String especialidad);

  Optional<Medico> updateDni(Long id, String dni);

  Optional<Medico> findByID(Long id);

  boolean delete(Long id);

  List<Medico> findByNombres(String nombres);

  List<Medico> findByApellidos(String apellidos);

  List<Medico> findByEspecialidad(String especialidad);

  Optional<Medico> findByDni(String dni);
}
