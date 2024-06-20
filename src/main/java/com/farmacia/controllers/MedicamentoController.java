package com.farmacia.controllers;

import com.farmacia.daos.MedicamentoDao;
import com.farmacia.exceptions.MedicamentoNaoEncontradoException;
import com.farmacia.models.Medicamento;
import com.farmacia.views.MedicamentoView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class MedicamentoController {
    private static final MedicamentoDao medicamentoDao = new MedicamentoDao();
    private static final File MEDICAMENTO_FILE_NAME = new File("farmacia/src/main/resources/arquivos/medicamentos.txt");

    public static void cadastrar(Medicamento medicamento) {
        try {
            medicamentoDao.escrever(MEDICAMENTO_FILE_NAME, medicamento.dadosFormatados(), true);
            System.out.println("Medicamento cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void atualizar(Medicamento medicamento) {
        try {
            medicamentoDao.atualizar(medicamento, MEDICAMENTO_FILE_NAME);
            System.out.println("Medicamento atualizado com sucesso!");
        } catch (IOException | MedicamentoNaoEncontradoException e) {
            System.out.println("Erro ao atualizar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deletar(UUID id) {
        try {
            medicamentoDao.deletar(id, MEDICAMENTO_FILE_NAME);
            System.out.println("Medicamento deletado com sucesso!");
        } catch (IOException | MedicamentoNaoEncontradoException e) {
            System.out.println("Erro ao deletar medicamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void listar() {
        try {
            MedicamentoView.listar(medicamentoDao.listar(MEDICAMENTO_FILE_NAME));
        } catch (IOException e) {
            System.out.println("Erro ao listar medicamentos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Medicamento buscarMedicamentoPorUuid(UUID uuid) throws MedicamentoNaoEncontradoException, IOException {
        var listaMedicamento = medicamentoDao.listar(MEDICAMENTO_FILE_NAME);

        Optional<Medicamento> medicamento = listaMedicamento.stream()
                                                            .filter(m -> m.getId().equals(uuid))
                                                            .findFirst();

        if(medicamento.isEmpty()) {
            throw new MedicamentoNaoEncontradoException("Não encontrado o medicamento de UUID: " + uuid);
        }

        return medicamento.get();
    }
}
