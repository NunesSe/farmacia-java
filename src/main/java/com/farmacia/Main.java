package com.farmacia;

import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.exceptions.VendedorNaoEncontradoException;
import com.farmacia.views.ClienteView;
import com.farmacia.views.MedicamentoView;
import com.farmacia.views.VendaView;
import com.farmacia.views.VendedorView;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        iniciar();
    }

    public static void iniciar() {
        try {
            int escolha;
            Scanner scan = new Scanner(System.in);
            do {
                mostrarMenu();
                escolha = Integer.parseInt(scan.nextLine());
                tratarEscolha(escolha, scan);
            } while (escolha != 0);
        } catch (NumberFormatException e) {
            System.out.println("Digite um número valido!");
            logger.warn("Entrada invalido (inteiro esperado)");
            iniciar();
        } 
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
        System.out.println("Digite o número que deseja: ");
    }

    public static void tratarEscolha(int escolha, Scanner scan){
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
                VendaView.iniciar(scan);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}
