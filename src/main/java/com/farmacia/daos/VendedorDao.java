package com.farmacia.daos;

import com.farmacia.models.Vendedor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VendedorDao extends Escrever implements DaoInterface<Vendedor> {
    @Override
    public Vendedor parse(String texto) {
        String[] dados = texto.split(";");
        UUID id = UUID.fromString(dados[0]);
        String nome = dados[1];
        String cpf = dados[2];
        double salario = Double.parseDouble(dados[3]);
        Vendedor vendedor = new Vendedor(nome, cpf, salario);
        vendedor.setId(id);
        return vendedor;
    }

    @Override
    public void atualizar(Vendedor vendedorAtualizado, File file) {

        List<Vendedor> vendedores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Vendedor vendedor = parse(linha);
                if (vendedor.getId().equals(vendedorAtualizado.getId())) {
                    vendedor.atualizarDados(vendedorAtualizado);
                }
                vendedores.add(vendedor);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                for (Vendedor vendedor : vendedores) {
                    bw.write(vendedor.dadosFormatados());
                    bw.newLine();
                }
        } 
        catch(IOException e) {
            System.out.println("Erro");
        }
    }

    @Override
    public void deletar(UUID id, File file) {
      
        List<Vendedor> vendedores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Vendedor vendedor = parse(linha);
                if (!vendedor.getId().equals(id)) {
                    vendedores.add(vendedor);
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                for (Vendedor vendedor : vendedores) {
                    bw.write(vendedor.dadosFormatados());
                    bw.newLine();
                }
        } catch(IOException e) {
            
        }

        
    }

    @Override
    public List<Vendedor> listar(File file) {

        List<Vendedor> vendedores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Vendedor vendedor = parse(linha);
                vendedores.add(vendedor);
            }
        } catch (IOException e) {
            System.out.println("Erro");
        }
        return vendedores;
    }

    private boolean existeVendedor(UUID id, File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Vendedor vendedor = parse(linha);
                if (vendedor.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }
}
