package com.farmacia.daos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Escrever {

     public static void escrever(File fileName, String texto, Boolean append) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, append);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(texto);
            bufferedWriter.newLine();
        }
    }
}

