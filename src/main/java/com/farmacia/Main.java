package com.farmacia;

import com.farmacia.controllers.VendaController;
import com.farmacia.daos.VendaDao;
import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.exceptions.VendedorNaoEncontradoException;
import com.farmacia.models.Medicamento;
import com.farmacia.models.Venda;
import com.farmacia.views.ClienteView;
import com.farmacia.views.MedicamentoView;
import com.farmacia.views.VendaView;
import com.farmacia.views.VendedorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        int escolha;
        Scanner scan = new Scanner(System.in);
        do {
            mostrarMenu();
            escolha = Integer.parseInt(scan.nextLine());
            tratarEscolha(escolha, scan);
        } while (escolha != 0);

        scan.close(); // Fechar o scanner ao finalizar o programa
    }

    public static void mostrarMenu() {
        System.out.println("##################################");
        System.out.println("               Menu               ");
        System.out.println("##################################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cliente");
        System.out.println("2 - Vendedor");
        System.out.println("3 - Medicamento");
        System.out.println("4 - Venda");
    }

    public static void tratarEscolha(int escolha, Scanner scan) throws IOException {
        switch (escolha) {
            case 0:
                System.out.println("Encerrando!");
                break;
            case 1:
                ClienteView.iniciar(scan);
                break;
            case 2:
                VendedorView.iniciar(scan);
                break;
            case 3:
                MedicamentoView.iniciar(scan);
                break;
            case 4:
                try {
                    VendaView.iniciar(scan);
                } catch (IOException | ClienteNaoEncontradoException | VendedorNaoEncontradoException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}
