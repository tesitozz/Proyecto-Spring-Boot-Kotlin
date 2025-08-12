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
@Table(name = "tb_cliente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cli")
  private Long id;

  @Column(name = "nombres_cli", nullable = false, length = 64)
  private String nombres;

  @Column(name = "apellidos_cli", nullable = false, length = 64)
  private String apellidos;

  @Column(name = "dni_cli", unique = true, nullable = false, length = 8)
  private String dni;

  @Column(name = "genero_cli", length = 32)
  private String genero;

  @Column(name = "correo_cli", unique = true, nullable = false, length = 80)
  private String correo;

  @Column(name = "celular_cli", nullable = false, length = 16)
  private String celular;

  @Column(name = "direccion_cli", length = 256)
  private String direccion;
}
