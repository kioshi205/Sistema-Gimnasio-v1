package com.gimnasioteam2.sistema_gimnasio.Service;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import com.gimnasioteam2.sistema_gimnasio.Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean registrar(Cliente cliente) {
        if (cliente.getDni() == null ||
                !cliente.getDni().matches("[0-9]{8}")) {
            return false;
        }

        if (cliente.getNombres() == null ||
                cliente.getNombres().trim().isEmpty()) {
            return false;
        }

        return clienteRepository.guardar(cliente);
    }

    public Cliente buscarPorDni(String dni) {
        return clienteRepository.buscarPorDni(dni);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.buscarPorNombre(nombre);
    }

    public boolean actualizar(Cliente cliente) {
        return clienteRepository.actualizar(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }
}