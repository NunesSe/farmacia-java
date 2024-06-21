package com.farmacia.daos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.farmacia.models.Medicamento;
import com.farmacia.models.Venda;

public class VendaDao extends Escrever {

    public static Venda parse(String texto) {
        String[] partes = texto.split("#");
        String[] infos = partes[0].split(";");
        String[] produtos = partes[1].split(",");
        UUID id = UUID.fromString(infos[0]);
        UUID vendedorId = UUID.fromString(infos[1]);
        UUID clienteId = UUID.fromString(infos[2]);
        Double precoFinal = Double.parseDouble(infos[3]);

        List<Medicamento> medicamentos = new ArrayList<>();
        for (String produto : produtos) {
            String[] dadosProduto = produto.split(";");
            UUID idProduto = UUID.fromString(dadosProduto[0]);
            String nomeProduto = dadosProduto[1];
            Double precoProduto = Double.parseDouble(dadosProduto[2]);
            String funcaoProduto = dadosProduto[3];

            Medicamento medicamento = new Medicamento(nomeProduto, precoProduto, funcaoProduto);
            medicamento.setId(idProduto);
            medicamentos.add(medicamento);
        }

        Venda venda = new Venda(id, precoFinal, clienteId, vendedorId, medicamentos);
        return venda;
    }

    public static void deletar(UUID id, File file) throws IOException {
        List<Venda> vendas = new ArrayList<>();
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = reader.readLine()) != null) {
                Venda venda = parse(linha);
                if (!venda.getId().equals(id)) {
                    vendas.add(venda);
                }
            }

            writer = new BufferedWriter(new FileWriter(file, false));
            for (Venda venda : vendas) {
                writer.write(venda.dadosFormatados());
                writer.newLine();
            }
            System.out.println("Apagado com sucesso!");
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    public List<Venda> listar(File file){
        BufferedReader reader = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String linha;
            while ((linha = reader.readLine()) != null) {
                Venda venda = parse(linha);
                vendas.add(venda);
            }
            reader.close();
        } catch(IOException e) {
            System.out.println("Erro");
        }
        return vendas;
    }
}
