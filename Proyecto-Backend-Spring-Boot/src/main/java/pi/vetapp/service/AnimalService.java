package pi.vetapp.service;

import pi.vetapp.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalService {
  List<Animal> getAll();

  Animal create(Animal animal);

  Optional<Animal> update(Animal animal);

  Optional<Animal> updateNombre(Long id, String nombre);

  Optional<Animal> updateTipo(Long id, String tipo);

  Optional<Animal> updateGenero(Long id, String genero);

  Optional<Animal> updateEdad(Long id, Integer edad);

  Optional<Animal> updatePeso(Long id, Double peso);

  Optional<Animal> updateRaza(Long id, String raza);

  Optional<Animal> updateColor(Long id, String color);

  Optional<Animal> findByID(Long id);

  boolean delete(Long id);

  List<Animal> findByNombre(String nombre);

  List<Animal> findByTipo(String tipo);

  List<Animal> findByGenero(String genero);

  List<Animal> findByEdad(Integer edad);

  List<Animal> findByPeso(Double peso);

  List<Animal> findByRaza(String raza);

  List<Animal> findByColor(String color);

  List<Animal> findByClienteId(Long clienteId);
}
