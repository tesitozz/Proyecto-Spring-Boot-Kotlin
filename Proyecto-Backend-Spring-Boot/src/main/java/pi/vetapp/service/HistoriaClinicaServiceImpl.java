package pi.vetapp.service;

import org.springframework.stereotype.Service;

import pi.vetapp.entity.Animal;
import pi.vetapp.entity.Cliente;
import pi.vetapp.entity.HistoriaClinica;
import pi.vetapp.entity.Medico;
import pi.vetapp.repository.HistoriaClinicaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {
  private final HistoriaClinicaRepository historiaClinicaRepository;

  public HistoriaClinicaServiceImpl(HistoriaClinicaRepository historiaClinicaRepository) {
    this.historiaClinicaRepository = historiaClinicaRepository;
  }

  @Override
  public List<HistoriaClinica> getAll() {
    return historiaClinicaRepository.findAll();
  }

  @Override
  public HistoriaClinica create(HistoriaClinica historiaClinica) {
    return historiaClinicaRepository.save(historiaClinica);
  }

  @Override
  public Optional<HistoriaClinica> update(HistoriaClinica historiaClinica) {
    if (historiaClinica == null || historiaClinica.getId() == null) {
      return Optional.empty();
    }

    return Optional.of(historiaClinicaRepository.save(historiaClinica));
  }

  @Override
  public Optional<HistoriaClinica> updateFechaDeRegistro(Long id, LocalDate fechaDeRegistro) {
    if (id == null || fechaDeRegistro == null) {
      return Optional.empty();
    }

    Optional<HistoriaClinica> optHistoriaClinica = historiaClinicaRepository.findById(id);
    if (optHistoriaClinica.isEmpty()) {
      return Optional.empty();
    }

    HistoriaClinica historiaClinica = optHistoriaClinica.get();
    historiaClinica.setFechaDeRegistro(fechaDeRegistro);
    return Optional.of(historiaClinicaRepository.save(historiaClinica));
  }

  @Override
  public Optional<HistoriaClinica> updateHoraDeRegistro(Long id, LocalTime horaDeRegistro) {
    if (id == null || horaDeRegistro == null) {
      return Optional.empty();
    }

    Optional<HistoriaClinica> optHistoriaClinica = historiaClinicaRepository.findById(id);
    if (optHistoriaClinica.isEmpty()) {
      return Optional.empty();
    }

    HistoriaClinica historiaClinica = optHistoriaClinica.get();
    historiaClinica.setHoraDeRegistro(horaDeRegistro);
    return Optional.of(historiaClinicaRepository.save(historiaClinica));
  }

  @Override
  public Optional<HistoriaClinica> updateAnimal(Long id, Animal animal) {
    if (id == null || animal == null) {
      return Optional.empty();
    }

    Optional<HistoriaClinica> optHistoriaClinica = historiaClinicaRepository.findById(id);
    if (optHistoriaClinica.isEmpty()) {
      return Optional.empty();
    }

    HistoriaClinica historiaClinica = optHistoriaClinica.get();
    historiaClinica.setAnimal(animal);
    return Optional.of(historiaClinicaRepository.save(historiaClinica));
  }

  @Override
  public Optional<HistoriaClinica> updateCliente(Long id, Cliente cliente) {
    if (id == null || cliente == null) {
      return Optional.empty();
    }

    Optional<HistoriaClinica> optHistoriaClinica = historiaClinicaRepository.findById(id);
    if (optHistoriaClinica.isEmpty()) {
      return Optional.empty();
    }

    HistoriaClinica historiaClinica = optHistoriaClinica.get();
    historiaClinica.setCliente(cliente);
    return Optional.of(historiaClinicaRepository.save(historiaClinica));
  }

  @Override
  public Optional<HistoriaClinica> updateMedico(Long id, Medico medico) {
    if (id == null || medico == null) {
      return Optional.empty();
    }

    Optional<HistoriaClinica> optHistoriaClinica = historiaClinicaRepository.findById(id);
    if (optHistoriaClinica.isEmpty()) {
      return Optional.empty();
    }

    HistoriaClinica historiaClinica = optHistoriaClinica.get();
    historiaClinica.setMedico(medico);
    return Optional.of(historiaClinicaRepository.save(historiaClinica));
  }

  @Override
  public Optional<HistoriaClinica> findByID(Long id) {
    if (id == null) {
      return Optional.empty();
    }

    return historiaClinicaRepository.findById(id);
  }

  @Override
  public boolean delete(Long id) {
    if (!historiaClinicaRepository.existsById(id)) {
      return false;
    }

    historiaClinicaRepository.deleteById(id);
    return true;
  }

  @Override
  public List<HistoriaClinica> findByAnimalId(Long animalId) {
    if (animalId == null) {
      return List.of();
    }

    return historiaClinicaRepository.findByAnimalId(animalId);
  }

  @Override
  public List<HistoriaClinica> findByClienteId(Long clienteId) {
    if (clienteId == null) {
      return List.of();
    }

    return historiaClinicaRepository.findByClienteId(clienteId);
  }

  @Override
  public List<HistoriaClinica> findByMedicoId(Long medicoId) {
    if (medicoId == null) {
      return List.of();
    }

    return historiaClinicaRepository.findByMedicoId(medicoId);
  }

  @Override
  public List<HistoriaClinica> findByYearOfFechaDeRegistro(Integer year) {
    if (year == null) {
      return List.of();
    }

    return historiaClinicaRepository.findByYearOfFechaDeRegistro(year);
  }

  @Override
  public List<HistoriaClinica> findByMonthOfFechaDeRegistro(Integer month) {
    if (month == null || month < 1 || month > 12) {
      return List.of();
    }

    return historiaClinicaRepository.findByMonthOfFechaDeRegistro(month);
  }

  @Override
  public List<HistoriaClinica> findByHourOfHoraDeRegistro(Integer hour) {
    if (hour == null || hour < 0 || hour > 23) {
      return List.of();
    }

    return historiaClinicaRepository.findByHourOfHoraDeRegistro(hour);
  }

  @Override
  public List<HistoriaClinica> findByMinuteOfHoraDeRegistro(Integer minute) {
    if (minute == null || minute < 0 || minute > 59) {
      return List.of();
    }

    return historiaClinicaRepository.findByMinuteOfHoraDeRegistro(minute);
  }

  @Override
  public List<HistoriaClinica> findByFechaDeRegistro(LocalDate fechaDeRegistro) {
    if (fechaDeRegistro == null) {
      return List.of();
    }

    return historiaClinicaRepository.findByFechaDeRegistro(fechaDeRegistro);
  }

  @Override
  public List<HistoriaClinica> findByHoraDeRegistro(LocalTime horaDeRegistro) {
    if (horaDeRegistro == null) {
      return List.of();
    }

    return historiaClinicaRepository.findByHoraDeRegistro(horaDeRegistro);
  }
}
