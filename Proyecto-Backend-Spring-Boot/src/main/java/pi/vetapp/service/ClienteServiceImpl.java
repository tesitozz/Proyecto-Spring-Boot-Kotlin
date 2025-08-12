package pi.vetapp.service;

import org.springframework.stereotype.Service;

import pi.vetapp.entity.Cliente;
import pi.vetapp.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
  private final ClienteRepository clienteRepository;

  public ClienteServiceImpl(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  @Override
  public List<Cliente> getAll() {
    return clienteRepository.findAll();
  }

  @Override
  public Cliente create(Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @Override
  public Optional<Cliente> update(Cliente cliente) {
    if (cliente == null || cliente.getId() == null) {
      return Optional.empty();
    }

    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateNombres(Long id, String nombres) {
    if (id == null || nombres == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setNombres(nombres);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateApellidos(Long id, String apellidos) {
    if (id == null || apellidos == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setApellidos(apellidos);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateDni(Long id, String dni) {
    if (id == null || dni == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setDni(dni);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateGenero(Long id, String genero) {
    if (id == null || genero == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setGenero(genero);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateCorreo(Long id, String correo) {
    if (id == null || correo == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setCorreo(correo);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateCelular(Long id, String celular) {
    if (id == null || celular == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setCelular(celular);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> updateDireccion(Long id, String direccion) {
    if (id == null || direccion == null) {
      return Optional.empty();
    }

    Optional<Cliente> optCli = clienteRepository.findById(id);
    if (optCli.isEmpty()) {
      return Optional.empty();
    }

    Cliente cliente = optCli.get();
    cliente.setDireccion(direccion);
    return Optional.of(clienteRepository.save(cliente));
  }

  @Override
  public Optional<Cliente> findByID(Long id) {
    if (id == null) {
      return Optional.empty();
    }

    return clienteRepository.findById(id);
  }

  @Override
  public boolean delete(Long id) {
    if (!clienteRepository.existsById(id)) {
      return false;
    }

    clienteRepository.deleteById(id);
    return true;
  }

  @Override
  public Optional<Cliente> findByDni(String dni) {
    return dni == null ? Optional.empty() : clienteRepository.findByDni(dni);
  }

  @Override
  public Optional<Cliente> findByCorreo(String correo) {
    return correo == null ? Optional.empty() : clienteRepository.findByCorreo(correo);
  }

  @Override
  public List<Cliente> findByNombres(String nombres) {
    return nombres == null ? List.of() : clienteRepository.findByNombresContainingIgnoreCase(nombres);
  }

  @Override
  public List<Cliente> findByApellidos(String apellidos) {
    return apellidos == null ? List.of() : clienteRepository.findByApellidosContainingIgnoreCase(apellidos);
  }

  @Override
  public List<Cliente> findByGenero(String genero) {
    return genero == null ? List.of() : clienteRepository.findByGeneroContainingIgnoreCase(genero);
  }

  @Override
  public List<Cliente> findByDireccion(String direccion) {
    return direccion == null ? List.of() : clienteRepository.findByDireccionContainingIgnoreCase(direccion);
  }

  @Override
  public List<Cliente> findByCelular(String celular) {
    return celular == null ? List.of() : clienteRepository.findByCelularContainingIgnoreCase(celular);
  }
}
