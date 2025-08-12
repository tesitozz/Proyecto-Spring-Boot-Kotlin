package pi.vetapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  private String usuario;
  private String password;
}
