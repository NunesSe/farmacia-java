package com.farmacia.daos;

import com.farmacia.exceptions.MedicamentoNaoEncontradoException;
import com.farmacia.models.Medicamento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicamentoDao extends Escrever implements DaoInterface<Medicamento> {

    @Override
    public Medicamento parse(String texto) {
        String[] dados = texto.split(";");
        UUID id = UUID.fromString(dados[0]);
        String nome = dados[1];
        Double preco = Double.parseDouble(dados[2]);
        String funcao = dados[3];
        Medicamento medicamento = new Medicamento(nome, preco, funcao);
        medicamento.setId(id);
        return medicamento;
    }

    @Override
    public void atualizar(Medicamento medicamentoAtualizado, File file) throws IOException, MedicamentoNaoEncontradoException {
        List<Medicamento> medicamentos = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Medicamento medicamento = parse(linha);
                if (medicamento.getId().equals(medicamentoAtualizado.getId())) {
                    medicamento.atualizarDados(medicamentoAtualizado);
                    encontrado = true;
                }
                medicamentos.add(medicamento);
            }
        }

        if (!encontrado) {
            throw new MedicamentoNaoEncontradoException("Medicamento não encontrado para atualização: " + medicamentoAtualizado.getId());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Medicamento medicamento : medicamentos) {
                bw.write(medicamento.dadosFormatados());
                bw.newLine();
            }
        }
    }

    @Override
    public void deletar(UUID id, File file) throws IOException, MedicamentoNaoEncontradoException {
        List<Medicamento> medicamentos = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Medicamento medicamento = parse(linha);
                if (!medicamento.getId().equals(id)) {
                    medicamentos.add(medicamento);
                } else {
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            throw new MedicamentoNaoEncontradoException("Medicamento não encontrado para exclusão: " + id);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Medicamento medicamento : medicamentos) {
                bw.write(medicamento.dadosFormatados());
                bw.newLine();
            }
        }
    }

    @Override
    public List<Medicamento> listar(File file) throws IOException {
        List<Medicamento> medicamentos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                Medicamento medicamento = parse(linha);
                medicamentos.add(medicamento);
            }
        } catch (IOException e) {
            System.out.println("Erro ao listar medicamentos!");
            return null;
        }
        return medicamentos;
    }
}
