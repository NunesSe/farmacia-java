package com.farmacia.daos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.farmacia.Main;

public abstract class Escrever {
    private static final Logger logger = LogManager.getLogger(Escrever.class);

     public static void escrever(File fileName, String texto, Boolean append)  {
        try (FileWriter writer = new FileWriter(fileName, append);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(texto);
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar no arquivo "  + fileName);
            logger.error("Erro ao cadastrar no arquivo " + fileName + "\nTexto: " + texto);
        }
    }
}

