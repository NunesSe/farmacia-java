package com.farmacia.controllers;

import com.farmacia.daos.ClienteDao;
import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.models.Cliente;
import com.farmacia.views.ClienteView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClienteController {
    private static final ClienteDao clienteDao = new ClienteDao();
    private static final File CLIENTE_FILE_NAME = new File("src\\main\\resources\\arquivos\\clientes.txt");

    public static void cadastrar(Cliente cliente) {
        try {
            clienteDao.escrever(CLIENTE_FILE_NAME, cliente.dadosFormatados(), true);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void atualizar(Cliente cliente) {
        try {
            clienteDao.atualizar(cliente, CLIENTE_FILE_NAME);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("Cliente não encontrado para atualização: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deletar(UUID id) {
        try {
            clienteDao.deletar(id, CLIENTE_FILE_NAME);
            System.out.println("Cliente deletado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
            e.printStackTrace();
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("Cliente não encontrado para exclusão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void listar() {
        try {
            ClienteView.listar(clienteDao.listar(CLIENTE_FILE_NAME));
        } catch (IOException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Cliente buscarClientePorUuid(UUID uuid)   {
        List<Cliente> listaCliente;
        try {
            listaCliente = clienteDao.listar(CLIENTE_FILE_NAME);
            Optional<Cliente> cliente = listaCliente.stream()
                                                    .filter(c -> c.getId().equals(uuid))
                                                    .findFirst();
    
            return cliente.get();
        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo!");
        }

        return null;

    }
}
