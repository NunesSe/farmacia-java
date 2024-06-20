package com.farmacia.controllers;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.farmacia.daos.VendaDao;
import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.exceptions.VendedorNaoEncontradoException;
import com.farmacia.models.Venda;
import com.farmacia.views.VendaView;


public class VendaController {
    private static final VendaDao vendaDao = new VendaDao();
    private static final File VENDA_FILE_NAME = new File("farmacia/src/main/resources/arquivos/vendas.txt");

    public static void cadastrar(Venda venda) {
        try {
            VendaDao.escrever(VENDA_FILE_NAME, venda.dadosFormatados(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listar() throws IOException {
       
        VendaView.listar(vendaDao.listar(VENDA_FILE_NAME));
        
    }

    public static void deletar(UUID id) {
        try {
            VendaDao.deletar(id, VENDA_FILE_NAME);
        } catch (IOException e) {
            System.out.println("Falha ao deletar!");
        }
    }

}