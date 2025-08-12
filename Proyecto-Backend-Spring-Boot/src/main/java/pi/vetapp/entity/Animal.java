package pi.vetapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_animales")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_ani")
  private Long id;

  @Column(name = "nombre_ani", nullable = false, length = 64)
  private String nombre;

  @Column(name = "tipo_ani", nullable = false, length = 32) // Tipo (especie: perro, gato, etc.)
  private String tipo;

  @Column(name = "genero_ani", nullable = false, length = 16) // Género (macho/hembra)
  private String genero;

  @Column(name = "edad_ani", nullable = false)
  private Integer edad;

  @Column(name = "peso_ani", nullable = false)
  private Double peso;

  @Column(name = "raza_ani", length = 64) // Raza (puede ser opcional)
  private String raza;

  @Column(name = "color_ani", length = 32)
  private String color;
}
