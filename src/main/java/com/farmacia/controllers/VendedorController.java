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
    private static final File VENDEDOR_FILE_NAME = new File("src\\main\\resources\\arquivos\\vendedores.txt");

    public static void cadastrar(Vendedor vendedor) {

            vendedorDao.escrever(VENDEDOR_FILE_NAME, vendedor.dadosFormatados(), true);
            System.out.println("Vendedor cadastrado com sucesso!");
        
    }

    public static void atualizar(Vendedor vendedor) {

        vendedorDao.atualizar(vendedor, VENDEDOR_FILE_NAME);

    }

    public static void deletar(UUID id) {

        vendedorDao.deletar(id, VENDEDOR_FILE_NAME);

    }

    public static void listar() {
        VendedorView.listar(vendedorDao.listar(VENDEDOR_FILE_NAME));
    }

    public static Vendedor buscarVendedorPorUuid(UUID uuid) {
        List<Vendedor> listaVendedor;

        listaVendedor = vendedorDao.listar(VENDEDOR_FILE_NAME);
        Vendedor vendedor = listaVendedor.stream()
                                                   .filter(v -> v.getId().equals(uuid))
                                                   .findFirst().orElse(null);

        return vendedor;

    }
}
