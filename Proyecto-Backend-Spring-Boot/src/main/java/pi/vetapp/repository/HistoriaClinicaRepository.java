package pi.vetapp.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pi.vetapp.entity.HistoriaClinica;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
  List<HistoriaClinica> findByAnimalId(Long animalId);

  List<HistoriaClinica> findByClienteId(Long clienteId);

  List<HistoriaClinica> findByMedicoId(Long medicoId);

  // Buscar por año de la fecha
  @Query("SELECT hc FROM HistoriaClinica hc WHERE YEAR(hc.fechaDeRegistro) = ?1")
  List<HistoriaClinica> findByYearOfFechaDeRegistro(Integer year);

  // Buscar por mes de la fecha
  @Query("SELECT hc FROM HistoriaClinica hc WHERE MONTH(hc.fechaDeRegistro) = ?1")
  List<HistoriaClinica> findByMonthOfFechaDeRegistro(Integer month);

  // Buscar por la hora
  @Query("SELECT hc FROM HistoriaClinica hc WHERE HOUR(hc.horaDeRegistro) = ?1")
  List<HistoriaClinica> findByHourOfHoraDeRegistro(Integer hour);

  // Buscar por minuto
  @Query("SELECT hc FROM HistoriaClinica hc WHERE MINUTE(hc.horaDeRegistro) = ?1")
  List<HistoriaClinica> findByMinuteOfHoraDeRegistro(Integer minute);

  // Buscar por fecha completa
  List<HistoriaClinica> findByFechaDeRegistro(LocalDate fechaDeRegistro);

  // Buscar por hora completa
  List<HistoriaClinica> findByHoraDeRegistro(LocalTime horaDeRegistro);
}
