package pi.vetapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pi.vetapp.entity.Animal;
import pi.vetapp.entity.Cliente;
import pi.vetapp.entity.HistoriaClinica;
import pi.vetapp.entity.Medico;
import pi.vetapp.service.HistoriaClinicaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historias-clinicas")
public class HistoriaClinicaController {
  private final HistoriaClinicaService historiaClinicaService;

  public HistoriaClinicaController(HistoriaClinicaService historiaClinicaService) {
    this.historiaClinicaService = historiaClinicaService;
  }

  @GetMapping("/listar")
  public ResponseEntity<List<HistoriaClinica>> getAllHC() {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.getAll();
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @PostMapping("/crear")
  public ResponseEntity<HistoriaClinica> createHC(@RequestBody HistoriaClinica historiaClinica) {
    HistoriaClinica nuevaHistoriaClinica = historiaClinicaService.create(historiaClinica);
    return new ResponseEntity<>(nuevaHistoriaClinica, HttpStatus.CREATED);
  }

  @PutMapping("/actualizar")
  public ResponseEntity<HistoriaClinica> updateHC(@RequestBody HistoriaClinica historiaClinica) {
    Optional<HistoriaClinica> updatedHistoriaClinica = historiaClinicaService.update(historiaClinica);
    if (updatedHistoriaClinica.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedHistoriaClinica.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-fecha-registro-de/{id}")
  public ResponseEntity<HistoriaClinica> updateFechaRegistroHC(@PathVariable("id") Long id,
      @RequestBody LocalDate fechaDeRegistro) {
    Optional<HistoriaClinica> updatedHistoriaClinica = historiaClinicaService.updateFechaDeRegistro(id,
        fechaDeRegistro);
    if (updatedHistoriaClinica.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedHistoriaClinica.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-hora-registro-de/{id}")
  public ResponseEntity<HistoriaClinica> updateHoraRegistroHC(@PathVariable("id") Long id,
      @RequestBody LocalTime horaDeRegistro) {
    Optional<HistoriaClinica> updatedHistoriaClinica = historiaClinicaService.updateHoraDeRegistro(id, horaDeRegistro);
    if (updatedHistoriaClinica.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedHistoriaClinica.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-animal-de/{id}")
  public ResponseEntity<HistoriaClinica> updateAnimalHC(@PathVariable("id") Long id, @RequestBody Animal animal) {
    Optional<HistoriaClinica> updatedHistoriaClinica = historiaClinicaService.updateAnimal(id, animal);
    if (updatedHistoriaClinica.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedHistoriaClinica.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-cliente-de/{id}")
  public ResponseEntity<HistoriaClinica> updateClienteHC(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
    Optional<HistoriaClinica> updatedHistoriaClinica = historiaClinicaService.updateCliente(id, cliente);
    if (updatedHistoriaClinica.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedHistoriaClinica.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-medico-de/{id}")
  public ResponseEntity<HistoriaClinica> updateMedicoHC(@PathVariable("id") Long id, @RequestBody Medico medico) {
    Optional<HistoriaClinica> updatedHistoriaClinica = historiaClinicaService.updateMedico(id, medico);
    if (updatedHistoriaClinica.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedHistoriaClinica.get(), HttpStatus.OK);
  }

  @GetMapping("/buscar-por-id/{id}")
  public ResponseEntity<HistoriaClinica> findByIDHC(@PathVariable("id") Long id) {
    Optional<HistoriaClinica> optAni = historiaClinicaService.findByID(id);
    if (optAni.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(optAni.get(), HttpStatus.OK);
  }

  @DeleteMapping("/eliminar-por-id/{id}")
  public ResponseEntity<Void> deleteHC(@PathVariable("id") Long id) {
    boolean isDeleted = historiaClinicaService.delete(id);
    if (!isDeleted) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/buscar-por-animal-id/{animalId}")
  public ResponseEntity<List<HistoriaClinica>> findByAnimalIdHC(@PathVariable("animalId") Long animalId) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByAnimalId(animalId);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-cliente-id/{clienteId}")
  public ResponseEntity<List<HistoriaClinica>> findByClienteIdHC(@PathVariable("clienteId") Long clienteId) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByClienteId(clienteId);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-medico-id/{medicoId}")
  public ResponseEntity<List<HistoriaClinica>> findByMedicoIdHC(@PathVariable("medicoId") Long medicoId) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByMedicoId(medicoId);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-anio-fecha/{year}")
  public ResponseEntity<List<HistoriaClinica>> findByYearOfFechaDeRegistroHC(@PathVariable("year") Integer year) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByYearOfFechaDeRegistro(year);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-mes-fecha/{month}")
  public ResponseEntity<List<HistoriaClinica>> findByMonthOfFechaDeRegistroHC(@PathVariable("month") Integer month) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByMonthOfFechaDeRegistro(month);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-hora-fecha/{hour}")
  public ResponseEntity<List<HistoriaClinica>> findByHourOfHoraDeRegistroHC(@PathVariable("hour") Integer hour) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByHourOfHoraDeRegistro(hour);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-minuto-fecha/{minute}")
  public ResponseEntity<List<HistoriaClinica>> findByMinuteOfHoraDeRegistroHC(@PathVariable("minute") Integer minute) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByMinuteOfHoraDeRegistro(minute);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-fecha/{fechaDeRegistro}")
  public ResponseEntity<List<HistoriaClinica>> findByFechaDeRegistroHC(
      @PathVariable("fechaDeRegistro") LocalDate fechaDeRegistro) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByFechaDeRegistro(fechaDeRegistro);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-hora/{horaDeRegistro}")
  public ResponseEntity<List<HistoriaClinica>> findByHoraDeRegistroHC(
      @PathVariable("horaDeRegistro") LocalTime horaDeRegistro) {
    List<HistoriaClinica> historiasClinicas = historiaClinicaService.findByHoraDeRegistro(horaDeRegistro);
    return historiasClinicas.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
  }
}
