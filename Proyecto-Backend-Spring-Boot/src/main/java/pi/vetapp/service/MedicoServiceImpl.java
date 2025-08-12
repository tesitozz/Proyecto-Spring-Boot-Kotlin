package pi.vetapp.service;

import org.springframework.stereotype.Service;

import pi.vetapp.entity.Medico;
import pi.vetapp.repository.MedicoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements MedicoService {
  private final MedicoRepository doctorRepository;

  public MedicoServiceImpl(MedicoRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  @Override
  public List<Medico> getAll() {
    return doctorRepository.findAll();
  }

  @Override
  public Medico create(Medico medico) {
    return doctorRepository.save(medico);
  }

  @Override
  public Optional<Medico> update(Medico medico) {
    if (medico == null || medico.getId() == null) {
      return Optional.empty();
    }
    return Optional.of(doctorRepository.save(medico));
  }

  @Override
  public Optional<Medico> updateNombres(Long id, String nombres) {
    if (id == null || nombres == null) {
      return Optional.empty();
    }

    Optional<Medico> optMedico = doctorRepository.findById(id);
    if (optMedico.isEmpty()) {
      return Optional.empty();
    }

    Medico medico = optMedico.get();
    medico.setNombres(nombres);
    return Optional.of(doctorRepository.save(medico));
  }

  @Override
  public Optional<Medico> updateApellidos(Long id, String apellidos) {
    if (id == null || apellidos == null) {
      return Optional.empty();
    }

    Optional<Medico> optMedico = doctorRepository.findById(id);
    if (optMedico.isEmpty()) {
      return Optional.empty();
    }

    Medico medico = optMedico.get();
    medico.setApellidos(apellidos);
    return Optional.of(doctorRepository.save(medico));
  }

  @Override
  public Optional<Medico> updateEspecialidad(Long id, String especialidad) {
    if (id == null || especialidad == null) {
      return Optional.empty();
    }

    Optional<Medico> optMedico = doctorRepository.findById(id);
    if (optMedico.isEmpty()) {
      return Optional.empty();
    }

    Medico medico = optMedico.get();
    medico.setEspecialidad(especialidad);
    return Optional.of(doctorRepository.save(medico));
  }

  @Override
  public Optional<Medico> updateDni(Long id, String dni) {
    if (id == null || dni == null) {
      return Optional.empty();
    }

    Optional<Medico> optMedico = doctorRepository.findById(id);
    if (optMedico.isEmpty()) {
      return Optional.empty();
    }

    Medico medico = optMedico.get();
    medico.setDni(dni);
    return Optional.of(doctorRepository.save(medico));
  }

  @Override
  public Optional<Medico> findByID(Long id) {
    if (id == null) {
      return Optional.empty();
    }

    return doctorRepository.findById(id);
  }

  @Override
  public boolean delete(Long id) {
    if (!doctorRepository.existsById(id)) {
      return false;
    }
    doctorRepository.deleteById(id);
    return true;
  }

  @Override
  public Optional<Medico> findByDni(String dni) {
    return dni == null ? Optional.empty() : doctorRepository.findByDni(dni);
  }

  @Override
  public List<Medico> findByNombres(String nombres) {
    return nombres == null ? List.of() : doctorRepository.findByNombresContainingIgnoreCase(nombres);
  }

  @Override
  public List<Medico> findByApellidos(String apellidos) {
    return apellidos == null ? List.of() : doctorRepository.findByApellidosContainingIgnoreCase(apellidos);
  }

  @Override
  public List<Medico> findByEspecialidad(String especialidad) {
    return especialidad == null ? List.of() : doctorRepository.findByEspecialidadContainingIgnoreCase(especialidad);
  }
}
