package pi.vetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pi.vetapp.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
  List<Animal> findByNombreContainingIgnoreCase(String nombre);

  List<Animal> findByTipoContainingIgnoreCase(String tipo);

  List<Animal> findByGeneroContainingIgnoreCase(String genero);

  List<Animal> findByEdad(Integer edad);

  List<Animal> findByPeso(Double peso);

  List<Animal> findByRazaContainingIgnoreCase(String raza);

  List<Animal> findByColorContainingIgnoreCase(String color);

  @Query("SELECT a FROM Animal a JOIN HistoriaClinica hc ON a.id = hc.animal.id WHERE hc.cliente.id = :clienteId")
  List<Animal> findByClienteId(@Param("clienteId") Long clienteId);
}
