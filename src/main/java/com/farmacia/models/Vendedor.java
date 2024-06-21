package com.farmacia.models;

public class Vendedor extends Pessoa implements Formatacao<Vendedor> {
    private Double salario;

    public Vendedor(String nome, String cpf, Double salario) {
        super(nome, cpf);
        this.salario = salario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    @Override
    public void atualizarDados(Vendedor vendedor) {
        setNome(vendedor.getNome());
        setCpf(vendedor.getCpf());
        setSalario(vendedor.getSalario());
    }

    @Override
    public String dadosFormatados() {
        try {
            return this.getId() + ";" + this.getNome() + ";" + this.getCpf() + ";" + this.getSalario();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao formatar os dados";
        }
    }
}
