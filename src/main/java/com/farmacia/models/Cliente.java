package com.farmacia.models;

public class Cliente extends Pessoa implements Formatacao<Cliente>{
   private int idade;
   
   public Cliente(String nome, String cpf, int idade) {
        super(nome, cpf);
        this.idade = idade;
    }
    
    public int getIdade() {
        return idade;
    }
 
 
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    @Override
    public void atualizarDados(Cliente cliente) {
        setNome(cliente.getNome());
        setCpf(cliente.getCpf());
        setIdade(cliente.getIdade());
    }

    @Override
    public String dadosFormatados() {
        return this.getId()+";"+this.getNome()+";"+this.getCpf() +";"+this.getIdade();
    }

    
}