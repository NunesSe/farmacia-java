package com.farmacia.models;

public interface Formatacao<T> {
    public String dadosFormatados();
    public void atualizarDados(T obj);
} 