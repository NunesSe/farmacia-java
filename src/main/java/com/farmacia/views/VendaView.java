package com.farmacia.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.farmacia.Main;
import com.farmacia.controllers.MedicamentoController;
import com.farmacia.controllers.VendaController;
import com.farmacia.controllers.ClienteController;
import com.farmacia.controllers.VendedorController;
import com.farmacia.models.Medicamento;
import com.farmacia.models.Venda;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VendaView {

    private static final Logger logger = LogManager.getLogger(VendaView.class);

    public static void iniciar(Scanner scan) {
        try {
            int escolha;
            do {
                exibirMenu();
                escolha = lerEscolhaUsuario(scan);
                tratarEscolha(scan, escolha);
            } while (escolha != 0);
        } catch (Exception e) {
            logger.error("Erro inesperado no menu de vendas", e);
        }
    }

    private static void exibirMenu() {
        System.out.println("###########################");
        System.out.println("#       Venda Menu        #");
        System.out.println("###########################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Remover");
        System.out.println("3 - Listar");
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
                logger.info("Menu de vendas encerrado pelo usuário.");
                break;
            case 1:
                cadastrar(scan);
                break;
            case 2:
                remover(scan);
                break;
            case 3:
                VendaController.listar();
                break;
            default:
                System.out.println("Opção inválida!");
                logger.warn("Opção inválida selecionada: " + escolha);
                break;
        }
    }

    public static void cadastrar(Scanner scan) {
        List<Medicamento> medicamentos = new ArrayList<>();
        UUID clienteId = null;
        UUID vendedorId = null;
        int escolha = -1;
        do {
            try {
                ClienteController.listar();
                System.out.println("Escolha um cliente (UUID): ");
                clienteId = UUID.fromString(scan.nextLine());
                if (ClienteController.buscarClientePorUuid(clienteId) == null) {
                    System.out.println("Cliente não encontrado com esse id!");
                    logger.warn("Cliente não encontrado: " + clienteId);
                    return;
                }

                VendedorController.listar();
                System.out.println("Escolha um vendedor (UUID): ");
                vendedorId = UUID.fromString(scan.nextLine());
                if (VendedorController.buscarVendedorPorUuid(vendedorId) == null) {
                    System.out.println("Vendedor não encontrado com esse id!");
                    logger.warn("Vendedor não encontrado: " + vendedorId);
                    return;
                }

                MedicamentoController.listar();
                System.out.println("Escolha um medicamento (UUID): ");
                UUID medicamentoId = UUID.fromString(scan.nextLine());
                Medicamento medicamento = MedicamentoController.buscarMedicamentoPorUuid(medicamentoId);
                if (medicamento == null) {
                    System.out.println("Medicamento não encontrado com esse id!");
                    logger.warn("Medicamento não encontrado: " + medicamentoId);
                    return;
                }

                System.out.println("Digite a quantidade que deseja desse medicamento: ");
                int quantidade = Integer.parseInt(scan.nextLine());
                if (quantidade <= 0) {
                    System.out.println("Quantidade inválida.");
                    logger.warn("Quantidade inválida inserida.");
                    return;
                }

                for (int i = 0; i < quantidade; i++) {
                    medicamentos.add(medicamento);
                }

                System.out.println("Pressione 0 para sair ou qualquer outro número para continuar: ");
                escolha = Integer.parseInt(scan.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("UUID inválido.");
                logger.error("UUID inválido inserido.", e);
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar venda.");
                logger.error("Erro inesperado ao cadastrar venda.", e);
            }
        } while (escolha != 0);

        Venda venda = new Venda(clienteId, vendedorId, medicamentos);
        VendaController.cadastrar(venda);
        logger.info("Venda cadastrada com sucesso: " + venda.getId());
    }

    public static void remover(Scanner scan) {
        try {
            VendaController.listar();
            System.out.println("Digite o ID da venda a ser removida: ");
            var id = UUID.fromString(scan.nextLine());
            if (VendaController.buscarVendaPorUuid(id) == null) {
                System.out.println("Venda não encontrada com esse id!");
                logger.warn("Venda não encontrada: " + id);
                return;
            }
            VendaController.deletar(id);
            logger.info("Venda removida com sucesso: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
            logger.error("Erro ao remover venda: ID inválido.", e);
        } catch (Exception e) {
            System.out.println("Erro ao remover venda.");
            logger.error("Erro inesperado ao remover venda.", e);
        }
    }

    public static void listar(List<Venda> vendas) {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada.");
            logger.info("Nenhuma venda encontrada.");
            return;
        }

        for (Venda venda : vendas) {
            System.out.println("===============================");
            System.out.println("Id: " + venda.getId());
            System.out.println("Cliente: " + ClienteController.buscarClientePorUuid(venda.getClienteId()).getNome());
            System.out.println("Vendedor: " + VendedorController.buscarVendedorPorUuid(venda.getVendedorId()).getNome());
            System.out.println("Preço Final: " + venda.getPrecoFinal());
            System.out.println("-------------------------------");
            for (Medicamento medicamento : venda.getMedicamentos()) {
                System.out.println("Nome Medicamento: " + medicamento.getNome());
                System.out.println("Preço Medicamento: " + medicamento.getPreco());
                System.out.println("Função Medicamento: " + medicamento.getFuncao());
                System.out.println("-------------------------------");
            }
            System.out.println("===============================");
        }
    }
}
