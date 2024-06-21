package com.farmacia.views;

import com.farmacia.Main;
import com.farmacia.controllers.VendedorController;
import com.farmacia.models.Vendedor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class VendedorView {
    private static final Logger logger = LogManager.getLogger(VendedorView.class);

    public static void iniciar(Scanner scan) {
        try {
            int escolha;
            do {
                exibirMenu();
                escolha = lerEscolhaUsuario(scan);
                tratarEscolha(scan, escolha);
            } while (escolha != 0);
        } catch (Exception e) {
            logger.error("Erro inesperado no menu de vendedores", e);
        }
    }

    private static void exibirMenu() {
        System.out.println("###########################");
        System.out.println("#       Vendedor Menu     #");
        System.out.println("###########################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Alterar");
        System.out.println("3 - Remover");
        System.out.println("4 - Listar");
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
                System.out.println("Saindo do menu!");
                logger.info("Menu de vendedores encerrado pelo usuário.");
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
                VendedorController.listar();
                break;
            default:
                System.out.println("Opção inválida!");
                logger.warn("Opção inválida selecionada: " + escolha);
                break;
        }
    }

    private static void cadastrar(Scanner scan) {
        try {
            System.out.println("Digite o nome");
            var nome = scan.nextLine();
            if (nome.isEmpty()) {
                System.out.println("Nome inválido.");
                logger.warn("Nome inválido inserido no cadastro de vendedor.");
                return;
            }

            System.out.println("Digite o CPF");
            var cpf = scan.nextLine();
            if (cpf.length() != 11) {
                System.out.println("CPF inválido.");
                logger.warn("CPF inválido inserido no cadastro de vendedor.");
                return;
            }

            System.out.println("Digite o salário");
            var salario = Double.parseDouble(scan.nextLine());

            if (salario <= 0) {
                System.out.println("Salário inválido.");
                logger.warn("Salário inválido inserido no cadastro de vendedor.");
                return;
            }

            Vendedor vendedor = new Vendedor(nome, cpf, salario);
            VendedorController.cadastrar(vendedor);
            logger.info("Vendedor cadastrado com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Salário inválido. Por favor, insira um número válido.");
            logger.error("Erro ao cadastrar vendedor: salário inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar vendedor.");
            logger.error("Erro inesperado ao cadastrar vendedor.", e);
        }
    }

    private static void alterar(Scanner scan) {
        try {
            VendedorController.listar();
            System.out.println("Digite o ID do vendedor a ser alterado");
            var id = UUID.fromString(scan.nextLine());

            if (VendedorController.buscarVendedorPorUuid(id) == null) {
                System.out.println("Vendedor não encontrado com esse id!");
                logger.debug("Vendedor não encontrado em VendedorView!");
                return;
            }

            System.out.println("Digite o novo nome");
            var nome = scan.nextLine();
            if (nome.isEmpty()) {
                System.out.println("Nome inválido.");
                logger.warn("Nome inválido inserido na atualização de vendedor.");
                return;
            }

            System.out.println("Digite o novo CPF");
            var cpf = scan.nextLine();
            if (cpf.length() != 11) {
                System.out.println("CPF inválido.");
                logger.warn("CPF inválido inserido na atualização de vendedor.");
                return;
            }

            System.out.println("Digite o novo salário");
            var salario = Double.parseDouble(scan.nextLine());

            if (salario <= 0) {
                System.out.println("Salário inválido.");
                logger.warn("Salário inválido inserido na atualização de vendedor.");
                return;
            }

            Vendedor vendedor = new Vendedor(nome, cpf, salario);
            vendedor.setId(id);
            VendedorController.atualizar(vendedor);
            logger.info("Vendedor atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
            logger.error("Erro ao alterar vendedor: ID inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao alterar vendedor.");
            logger.error("Erro inesperado ao alterar vendedor.", e);
        }
    }

    private static void remover(Scanner scan) {
        try {
            VendedorController.listar();
            System.out.println("Digite o ID do vendedor a ser removido");
            var id = UUID.fromString(scan.nextLine());

            if (VendedorController.buscarVendedorPorUuid(id) == null) {
                System.out.println("Vendedor não encontrado com esse id!");
                logger.debug("Vendedor não encontrado em VendedorView!");
                return;
            }

            VendedorController.deletar(id);
            logger.info("Vendedor removido com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
            logger.error("Erro ao remover vendedor: ID inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao remover vendedor.");
            logger.error("Erro inesperado ao remover vendedor.", e);
        }
    }

    public static void listar(List<Vendedor> vendedores) {
        if (vendedores.isEmpty()) {
            System.out.println("Nenhum vendedor encontrado!");
            logger.info("Nenhum vendedor encontrado.");
            return;
        }

        for (Vendedor vendedor : vendedores) {
            System.out.println("===============================");
            System.out.println("Id: " + vendedor.getId());
            System.out.println("Nome: " + vendedor.getNome());
            System.out.println("CPF: " + vendedor.getCpf());
            System.out.println("Salário: " + vendedor.getSalario());
            System.out.println("===============================");
        }
    }
}
