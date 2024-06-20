package com.farmacia.models;

public class Medicamento extends Produto implements Formatacao<Medicamento> {
    private String funcao;
    public Medicamento(String nome, Double preco, String funcao) {
        super(nome, preco);
        this.funcao = funcao;
    }
    
    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String dadosFormatados() {
        return this.getId() + ";" + this.getNome() + ";" + this.getPreco() + ";" + this.funcao;
    }

    @Override
    public void atualizarDados(Medicamento medicamento) {
        this.setNome(medicamento.getNome());
        this.setPreco(medicamento.getPreco());
        this.setFuncao(medicamento.getFuncao());
    }
    
}
