package com.farmacia.views;

import com.farmacia.Main;
import com.farmacia.controllers.MedicamentoController;
import com.farmacia.models.Medicamento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MedicamentoView {
    private static final Logger logger = LogManager.getLogger(MedicamentoView.class);

    public static void iniciar(Scanner scan) {
        try {
            int escolha;
            do {
                exibirMenu();
                escolha = lerEscolhaUsuario(scan);
                tratarEscolha(scan, escolha);
            } while (escolha != 0);
        } catch (Exception e) {
            logger.error("Erro inesperado no menu de medicamentos", e);
        }
    }

    private static void exibirMenu() {
        System.out.println("##################################");
        System.out.println("         Menu de Medicamentos     ");
        System.out.println("##################################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar Medicamento");
        System.out.println("2 - Atualizar Medicamento");
        System.out.println("3 - Remover Medicamento");
        System.out.println("4 - Listar Medicamentos");
    }

    private static int lerEscolhaUsuario(Scanner scan) {
        int escolha = -1;
        try {
            escolha = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            logger.error("Entrada inválida, por favor digite um número.", e);
            System.out.println("Por favor, digite um número válido.");
        }
        return escolha;
    }

    private static void tratarEscolha(Scanner scan, int escolha) {
        switch (escolha) {
            case 0:
                System.out.println("Encerrando!");
                logger.info("Menu de medicamentos encerrado pelo usuário.");
                break;
            case 1:
                cadastrar(scan);
                break;
            case 2:
                alterar(scan);
                break;
            case 3:
                remover(scan);
                break;
            case 4:
                MedicamentoController.listar();
                break;
            default:
                System.out.println("Opção inválida!");
                logger.warn("Opção inválida selecionada: " + escolha);
                break;
        }
    }

    private static void cadastrar(Scanner scan) {
        try {
            System.out.println("Digite o nome do medicamento");
            var nome = scan.nextLine();
            if (nome.isEmpty()) {
                System.out.println("Nome inválido.");
                logger.warn("Nome inválido inserido no cadastro de medicamento.");
                return;
            }

            System.out.println("Digite o preço do medicamento");
            var preco = Double.parseDouble(scan.nextLine());

            if (preco <= 0) {
                System.out.println("Preço inválido.");
                logger.warn("Preço inválido inserido no cadastro de medicamento.");
                return;
            }

            System.out.println("Digite a função do medicamento");
            var funcao = scan.nextLine();

            if (funcao.isEmpty()) {
                System.out.println("Função inválida.");
                logger.warn("Função inválida inserida no cadastro de medicamento.");
                return;
            }

            Medicamento medicamento = new Medicamento(nome, preco, funcao);
            MedicamentoController.cadastrar(medicamento);
            logger.info("Medicamento cadastrado com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido. Por favor, insira um número válido.");
            logger.error("Erro ao cadastrar medicamento: preço inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar medicamento.");
            logger.error("Erro inesperado ao cadastrar medicamento.", e);
        }
    }

    private static void alterar(Scanner scan) {
        try {
            MedicamentoController.listar();
            System.out.println("Digite o ID do medicamento a ser alterado");
            var id = UUID.fromString(scan.nextLine());

            if (MedicamentoController.buscarMedicamentoPorUuid(id) == null) {
                System.out.println("Medicamento não encontrado com esse id!");
                logger.debug("Medicamento não encontrado em MedicamentoView!");
                return;
            }

            System.out.println("Digite o novo nome do medicamento");
            var nome = scan.nextLine();
            if (nome.isEmpty()) {
                System.out.println("Nome inválido.");
                logger.warn("Nome inválido inserido no cadastro de medicamento.");
                return;
            }

            System.out.println("Digite o novo preço do medicamento");
            var preco = Double.parseDouble(scan.nextLine());

            if (preco <= 0) {
                System.out.println("Preço inválido.");
                logger.warn("Preço inválido inserido no cadastro de medicamento.");
                return;
            }

            System.out.println("Digite a nova função do medicamento");
            var funcao = scan.nextLine();
            if (funcao.isEmpty()) {
                System.out.println("Função inválida.");
                logger.warn("Função inválida inserida no cadastro de medicamento.");
                return;
            }

            Medicamento medicamento = new Medicamento(nome, preco, funcao);
            medicamento.setId(id);
            MedicamentoController.atualizar(medicamento);
            logger.info("Medicamento atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
            logger.error("Erro ao alterar medicamento: ID inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao alterar medicamento.");
            logger.error("Erro inesperado ao alterar medicamento.", e);
        }
    }

    private static void remover(Scanner scan) {
        try {
            MedicamentoController.listar();
            System.out.println("Digite o ID do medicamento a ser removido");
            var id = UUID.fromString(scan.nextLine());

            if (MedicamentoController.buscarMedicamentoPorUuid(id) == null) {
                System.out.println("Medicamento não encontrado com esse id!");
                logger.debug("Medicamento não encontrado em MedicamentoView!");
                return;
            }

            MedicamentoController.deletar(id);
            logger.info("Medicamento removido com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
            logger.error("Erro ao remover medicamento: ID inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao remover medicamento.");
            logger.error("Erro inesperado ao remover medicamento.", e);
        }
    }

    public static void listar(List<Medicamento> medicamentos) {
        if (medicamentos.isEmpty()) {
            System.out.println("Nenhum medicamento encontrado!");
            logger.info("Nenhum medicamento encontrado.");
            return;
        }

        for (Medicamento medicamento : medicamentos) {
            System.out.println("Id: " + medicamento.getId());
            System.out.println("Nome: " + medicamento.getNome());
            System.out.println("Preço: " + medicamento.getPreco());
            System.out.println("Função: " + medicamento.getFuncao());
            System.out.println("------------------------");
        }
    }
}
