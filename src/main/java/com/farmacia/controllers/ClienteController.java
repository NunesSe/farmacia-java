package com.farmacia.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.farmacia.Main;
import com.farmacia.daos.ClienteDao;
import com.farmacia.models.Cliente;
import com.farmacia.views.ClienteView;



public class ClienteController {
    private static final Logger logger = LogManager.getLogger(Main.class);


    private static final ClienteDao clienteDao = new ClienteDao();
    private static final File CLIENTE_FILE_NAME = new File("src\\main\\resources\\arquivos\\clientes.txt");

    public static void cadastrar(Cliente cliente) {
        clienteDao.escrever(CLIENTE_FILE_NAME, cliente.dadosFormatados(), true);
        System.out.println("Cliente cadastrado com sucesso!");
        logger.info("Cliente cadastrado com sucesso: " + cliente.getId());
    }

    public static void atualizar(Cliente cliente) {
        try {
            clienteDao.atualizar(cliente, CLIENTE_FILE_NAME);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
        } 
    }

    public static void deletar(UUID id) {
        clienteDao.deletar(id, CLIENTE_FILE_NAME);
       
    }

    public static void listar() {
        ClienteView.listar(clienteDao.listar(CLIENTE_FILE_NAME));
    }

    public static Cliente buscarClientePorUuid(UUID uuid)   {
        List<Cliente> listaCliente;
            listaCliente = clienteDao.listar(CLIENTE_FILE_NAME);
            Cliente cliente = listaCliente.stream()
                                          .filter(c -> c.getId().equals(uuid))
                                          .findFirst().orElse(null);
    
            return cliente;
    }

    public static boolean clienteJaExiste(String CPF) {
        List<Cliente> clientes = clienteDao.listar(CLIENTE_FILE_NAME);
        if(clientes.size() == 0) {
            return false;
        }

        var verificar = clientes.stream().filter(c -> c.getCpf().equals(CPF)).findFirst().orElse(null);
        if(verificar != null) {
            return true;
        }

        return false;
    }
}
