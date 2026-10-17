package com.gimnasioteam2.sistema_gimnasio.Repository;

import com.gimnasioteam2.sistema_gimnasio.Model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    private final List<Cliente> clientes = new ArrayList<>();

    public boolean guardar(Cliente cliente) {
        if (buscarPorDni(cliente.getDni()) != null) {
            return false;
        }

        clientes.add(cliente);
        return true;
    }

    public Cliente buscarPorDni(String dni) {
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }

        return null;
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> encontrados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (cliente.getNombres().toLowerCase()
                    .contains(nombre.toLowerCase())) {
                encontrados.add(cliente);
            }
        }

        return encontrados;
    }

    public boolean actualizar(Cliente actualizado) {
        Cliente cliente = buscarPorDni(actualizado.getDni());

        if (cliente == null) {
            return false;
        }

        cliente.setNombres(actualizado.getNombres());
        cliente.setTelefono(actualizado.getTelefono());
        cliente.setCorreo(actualizado.getCorreo());
        cliente.setDireccion(actualizado.getDireccion());

        return true;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
}