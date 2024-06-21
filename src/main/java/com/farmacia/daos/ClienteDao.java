package com.farmacia.daos;

import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.models.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClienteDao extends Escrever implements DaoInterface<Cliente> {
    @Override
    public Cliente parse(String texto) {
        String[] dados = texto.split(";");
        UUID id = UUID.fromString(dados[0]);
        String nome = dados[1];
        String cpf = dados[2];
        int idade = Integer.parseInt(dados[3]);
        Cliente cliente = new Cliente(nome, cpf, idade);
        cliente.setId(id);
        return cliente;
    }

    @Override
    public void atualizar(Cliente clienteAtualizado, File file) throws IOException {
        

        List<Cliente> clientes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Cliente cliente = parse(linha);
                if (cliente.getId().equals(clienteAtualizado.getId())) {
                    cliente.atualizarDados(clienteAtualizado);
                }
                clientes.add(cliente);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Cliente cliente : clientes) {
                bw.write(cliente.dadosFormatados());
                bw.newLine();
            }
        }
    }

    @Override
    public void deletar(UUID id, File file) {
       

        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Cliente> clientes = new ArrayList<>();
            String linha;
            while ((linha = br.readLine()) != null) {
                Cliente cliente = parse(linha);
                if (!cliente.getId().equals(id)) {
                    clientes.add(cliente);
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                   for (Cliente cliente : clientes) {
                       bw.write(cliente.dadosFormatados());
                       bw.newLine();
                   }
            bw.close();
            System.out.println("Cliente deletado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao deletar clientes");
        }

        
    }

    @Override
    public List<Cliente> listar(File file)  {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String linha;
            List<Cliente> clientes = new ArrayList<>();
            while ((linha = reader.readLine()) != null) {
                Cliente cliente = parse(linha);
                clientes.add(cliente);
            }
            return clientes;
        } catch (IOException e) {
            System.out.println("Erro ao listar em ClienteDao");
            return null;
        }
        
    }


}
