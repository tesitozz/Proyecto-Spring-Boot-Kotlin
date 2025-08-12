package pi.vetapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_medico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_med")
  private Long id;

  @Column(name = "nombres_med", nullable = false, length = 64)
  private String nombres;

  @Column(name = "apellidos_med", nullable = false, length = 64)
  private String apellidos;

  @Column(name = "dni_med", unique = true, nullable = false, length = 8)
  private String dni;

  @Column(name = "genero_med", length = 32)
  private String genero;

  @Column(name = "correo_med", unique = true, nullable = false, length = 80)
  private String correo;

  @Column(name = "celular_med", nullable = false, length = 16)
  private String celular;

  @Column(name = "especialidad_med", nullable = false, length = 64)
  private String especialidad;
}
