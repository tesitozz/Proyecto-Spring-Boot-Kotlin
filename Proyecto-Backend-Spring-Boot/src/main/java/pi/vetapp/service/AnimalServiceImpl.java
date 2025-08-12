package pi.vetapp.service;

import org.springframework.stereotype.Service;
import pi.vetapp.entity.Animal;
import pi.vetapp.repository.AnimalRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {
  private final AnimalRepository animalesRepository;

  public AnimalServiceImpl(AnimalRepository animalesRepository) {
    this.animalesRepository = animalesRepository;
  }

  @Override
  public List<Animal> getAll() {
    return animalesRepository.findAll();
  }

  @Override
  public Animal create(Animal animal) {
    return animalesRepository.save(animal);
  }

  @Override
  public Optional<Animal> update(Animal animal) {
    if (animal == null || animal.getId() == null) {
      return Optional.empty();
    }

    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updateNombre(Long id, String nombre) {
    if (id == null || nombre == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setNombre(nombre);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updateTipo(Long id, String tipo) {
    if (id == null || tipo == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setTipo(tipo);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updateGenero(Long id, String genero) {
    if (id == null || genero == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setGenero(genero);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updateEdad(Long id, Integer edad) {
    if (id == null || edad == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setEdad(edad);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updatePeso(Long id, Double peso) {
    if (id == null || peso == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setPeso(peso);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updateRaza(Long id, String raza) {
    if (id == null || raza == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setRaza(raza);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> updateColor(Long id, String color) {
    if (id == null || color == null) {
      return Optional.empty();
    }

    Optional<Animal> optAnimal = animalesRepository.findById(id);
    if (optAnimal.isEmpty()) {
      return Optional.empty();
    }

    Animal animal = optAnimal.get();
    animal.setColor(color);
    return Optional.of(animalesRepository.save(animal));
  }

  @Override
  public Optional<Animal> findByID(Long id) {
    if (id == null) {
      return Optional.empty();
    }

    return animalesRepository.findById(id);
  }

  @Override
  public boolean delete(Long id) {
    if (!animalesRepository.existsById(id)) {
      return false;
    }

    animalesRepository.deleteById(id);
    return true;
  }

  @Override
  public List<Animal> findByNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) {
      return Collections.emptyList();
    }

    return animalesRepository.findByNombreContainingIgnoreCase(nombre);
  }

  @Override
  public List<Animal> findByTipo(String tipo) {
    if (tipo == null || tipo.isBlank()) {
      return Collections.emptyList();
    }

    return animalesRepository.findByTipoContainingIgnoreCase(tipo);
  }

  @Override
  public List<Animal> findByGenero(String genero) {
    if (genero == null || genero.isBlank()) {
      return Collections.emptyList();
    }

    return animalesRepository.findByGeneroContainingIgnoreCase(genero);
  }

  @Override
  public List<Animal> findByEdad(Integer edad) {
    if (edad == null) {
      return Collections.emptyList();
    }

    return animalesRepository.findByEdad(edad);
  }

  @Override
  public List<Animal> findByPeso(Double peso) {
    if (peso == null) {
      return Collections.emptyList();
    }

    return animalesRepository.findByPeso(peso);
  }

  @Override
  public List<Animal> findByRaza(String raza) {
    if (raza == null || raza.isBlank()) {
      return Collections.emptyList();
    }

    return animalesRepository.findByRazaContainingIgnoreCase(raza);
  }

  @Override
  public List<Animal> findByColor(String color) {
    if (color == null || color.isBlank()) {
      return Collections.emptyList();
    }

    return animalesRepository.findByColorContainingIgnoreCase(color);
  }

  @Override
  public List<Animal> findByClienteId(Long clienteId) {
    if (clienteId == null) {
      return Collections.emptyList();
    }

    return animalesRepository.findByClienteId(clienteId);
  }
}
