package pi.vetapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.vetapp.entity.Animal;
import pi.vetapp.service.AnimalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animales")
public class AnimalController {
  private final AnimalService animalService;

  public AnimalController(AnimalService animalService) {
    this.animalService = animalService;
  }

  @GetMapping("/listar")
  public ResponseEntity<List<Animal>> getAllAni() {
    List<Animal> animales = animalService.getAll();
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @PostMapping("/crear")
  public ResponseEntity<Animal> createAni(@RequestBody Animal animal) {
    Animal nuevoAnimal = animalService.create(animal);
    return new ResponseEntity<>(nuevoAnimal, HttpStatus.CREATED);
  }

  @PutMapping("/actualizar")
  public ResponseEntity<Animal> updateAni(@RequestBody Animal animal) {
    Optional<Animal> updatedAnimal = animalService.update(animal);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-nombre-de/{id}")
  public ResponseEntity<Animal> updateNombreAni(@PathVariable("id") Long id, @RequestBody String nombre) {
    Optional<Animal> updatedAnimal = animalService.updateNombre(id, nombre);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-tipo-de/{id}")
  public ResponseEntity<Animal> updateTipoAni(@PathVariable("id") Long id, @RequestBody String tipo) {
    Optional<Animal> updatedAnimal = animalService.updateTipo(id, tipo);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-genero-de/{id}")
  public ResponseEntity<Animal> updateGeneroAni(@PathVariable("id") Long id, @RequestBody String genero) {
    Optional<Animal> updatedAnimal = animalService.updateGenero(id, genero);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-edad-de/{id}")
  public ResponseEntity<Animal> updateEdadAni(@PathVariable("id") Long id, @RequestBody Integer edad) {
    Optional<Animal> updatedAnimal = animalService.updateEdad(id, edad);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-peso-de/{id}")
  public ResponseEntity<Animal> updatePesoAni(@PathVariable("id") Long id, @RequestBody Double peso) {
    Optional<Animal> updatedAnimal = animalService.updatePeso(id, peso);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-raza-de/{id}")
  public ResponseEntity<Animal> updateRazaAni(@PathVariable("id") Long id, @RequestBody String raza) {
    Optional<Animal> updatedAnimal = animalService.updateRaza(id, raza);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @PutMapping("/actualizar-color-de/{id}")
  public ResponseEntity<Animal> updateColorAni(@PathVariable("id") Long id, @RequestBody String color) {
    Optional<Animal> updatedAnimal = animalService.updateColor(id, color);
    if (updatedAnimal.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(updatedAnimal.get(), HttpStatus.OK);
  }

  @GetMapping("/buscar-por-id/{id}")
  public ResponseEntity<Animal> findByIDAni(@PathVariable("id") Long id) {
    Optional<Animal> optAni = animalService.findByID(id);
    if (optAni.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(optAni.get(), HttpStatus.OK);
  }

  @DeleteMapping("/eliminar-por-id/{id}")
  public ResponseEntity<Void> deleteAni(@PathVariable("id") Long id) {
    boolean isDeleted = animalService.delete(id);
    if (!isDeleted) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/buscar-por-nombre/{nombre}")
  public ResponseEntity<List<Animal>> findByNombreAni(@PathVariable("nombre") String nombre) {
    List<Animal> animales = animalService.findByNombre(nombre);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-tipo/{tipo}")
  public ResponseEntity<List<Animal>> findByTipoAni(@PathVariable("tipo") String tipo) {
    List<Animal> animales = animalService.findByTipo(tipo);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-genero/{genero}")
  public ResponseEntity<List<Animal>> findByGeneroAni(@PathVariable("genero") String genero) {
    List<Animal> animales = animalService.findByGenero(genero);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-edad/{edad}")
  public ResponseEntity<List<Animal>> findByEdadAni(@PathVariable("edad") Integer edad) {
    List<Animal> animales = animalService.findByEdad(edad);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-peso/{peso}")
  public ResponseEntity<List<Animal>> findByPesoAni(@PathVariable("peso") Double peso) {
    List<Animal> animales = animalService.findByPeso(peso);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-raza/{raza}")
  public ResponseEntity<List<Animal>> findByRazaAni(@PathVariable("raza") String raza) {
    List<Animal> animales = animalService.findByRaza(raza);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-color/{color}")
  public ResponseEntity<List<Animal>> findByColorAni(@PathVariable("color") String color) {
    List<Animal> animales = animalService.findByColor(color);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }

  @GetMapping("/buscar-por-id-de-cliente/{clienteId}")
  public ResponseEntity<List<Animal>> findByClienteAni(@PathVariable("clienteId") Long clienteId) {
    List<Animal> animales = animalService.findByClienteId(clienteId);
    return animales.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(animales, HttpStatus.OK);
  }
}
