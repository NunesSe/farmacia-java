package com.farmacia.daos;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.exceptions.MedicamentoNaoEncontradoException;
import com.farmacia.exceptions.VendaNaoEncontradaException;
import com.farmacia.exceptions.VendedorNaoEncontradoException;

public interface DaoInterface<T> {
    public T parse(String texto);
    public void atualizar(T obj, File file) throws IOException, ClienteNaoEncontradoException, VendedorNaoEncontradoException, MedicamentoNaoEncontradoException, VendaNaoEncontradaException;
    public void deletar(UUID id, File file) throws IOException, ClienteNaoEncontradoException, VendedorNaoEncontradoException, MedicamentoNaoEncontradoException, VendaNaoEncontradaException;
    public List<T> listar(File file) throws IOException, VendedorNaoEncontradoException;
}
    
    

