package com.farmacia.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.farmacia.daos.VendedorDao;
import com.farmacia.exceptions.VendedorNaoEncontradoException;
import com.farmacia.models.Vendedor;
import com.farmacia.views.VendedorView;

public class VendedorController {
    private static final VendedorDao vendedorDao = new VendedorDao();
    private static final File VENDEDOR_FILE_NAME = new File("C:\\Users\\ivisf\\Desktop\\farmacia-java\\src\\main\\resources\\arquivos\\vendedores.txt");

    public static void cadastrar(Vendedor vendedor) {
        try {
            vendedorDao.escrever(VENDEDOR_FILE_NAME, vendedor.dadosFormatados(), true);
            System.out.println("Vendedor cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar vendedor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void atualizar(Vendedor vendedor) {
        try {
            vendedorDao.atualizar(vendedor, VENDEDOR_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar vendedor: " + e.getMessage());
            e.printStackTrace();
        } catch (VendedorNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletar(UUID id) {
        try {
            vendedorDao.deletar(id, VENDEDOR_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Erro ao deletar vendedor: " + e.getMessage());
            e.printStackTrace();
        } catch (VendedorNaoEncontradoException e) {
            
        }
    }

    public static void listar() throws IOException {
        VendedorView.listar(vendedorDao.listar(VENDEDOR_FILE_NAME));
    }

    public static Vendedor buscarVendedorPorUuid(UUID uuid)  {
        List<Vendedor> listaVendedor;
        try {
            listaVendedor = vendedorDao.listar(VENDEDOR_FILE_NAME);
            Optional<Vendedor> vendedor = listaVendedor.stream()
                                                       .filter(v -> v.getId().equals(uuid))
                                                       .findFirst();
    
            return vendedor.get();
        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo!");
        }
        return null;

    }
}
