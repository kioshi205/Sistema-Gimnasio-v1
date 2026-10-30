package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import com.gimnasioteam2.sistema_gimnasio.Repository.clienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class clienteService {

    private final clienteRepository repo;

    public clienteService(clienteRepository repo) {
        this.repo = repo;
    }

    /** F24: listar y buscar clientes. */
    public List<Cliente> buscar(String texto, String estado) {
        return repo.buscar(texto, estado);
    }

    public Cliente obtener(int id) {
        return repo.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("El cliente no existe."));
    }

    /** F23: registrar cliente. */
    public void registrar(Cliente c) {
        normalizar(c);
        validar(c);
        if (repo.idPorDni(c.getDni()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con el DNI " + c.getDni() + ".");
        }
        repo.insertar(c);
    }

    /** F25: editar cliente. */
    public void editar(Cliente c) {
        normalizar(c);
        validar(c);
        obtener(c.getIdCliente());
        Integer otro = repo.idPorDni(c.getDni());
        if (otro != null && !otro.equals(c.getIdCliente())) {
            throw new IllegalArgumentException("Otro cliente ya usa el DNI " + c.getDni() + ".");
        }
        repo.actualizar(c);
    }

    /** F26: desactivar cliente. */
    public void desactivar(int id) {
        Cliente c = obtener(id);
        if (!c.isActivo()) {
            throw new IllegalArgumentException("El cliente ya está desactivado.");
        }
        repo.cambiarEstado(id, "INACTIVO");
    }

    /** Reactivar cliente. */
    public void activar(int id) {
        Cliente c = obtener(id);
        if (c.isActivo()) {
            throw new IllegalArgumentException("El cliente ya está activo.");
        }
        repo.cambiarEstado(id, "ACTIVO");
    }

    private void normalizar(Cliente c) {
        c.setDni(trim(c.getDni()));
        c.setNombre(trim(c.getNombre()));
        c.setApellido(trim(c.getApellido()));
        c.setEmail(blankToNull(c.getEmail()));
        c.setTelefono(blankToNull(c.getTelefono()));
    }

    private void validar(Cliente c) {
        if (!c.getDni().matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos.");
        }
        if (c.getNombre().isEmpty() || c.getApellido().isEmpty()) {
            throw new IllegalArgumentException("Nombre y apellido son obligatorios.");
        }
        if (c.getNombre().length() > 100 || c.getApellido().length() > 100) {
            throw new IllegalArgumentException("Nombre y apellido no pueden superar 100 caracteres.");
        }
        if (c.getEmail() != null && !c.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El correo electrónico no es válido.");
        }
        if (c.getTelefono() != null && !c.getTelefono().matches("\\d{9}")) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos.");
        }
    }

    private String trim(String s) { return s == null ? "" : s.trim(); }
    private String blankToNull(String s) { return s == null || s.isBlank() ? null : s.trim(); }
}
