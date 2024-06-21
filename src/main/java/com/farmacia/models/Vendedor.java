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
            String nome = this.getNome() != null ? this.getNome() : "";
            String cpf = this.getCpf() != null ? this.getCpf() : "";

            // Validação do salário
            double salario = 0.0;
            if (this.getSalario() != null) {
                salario = this.getSalario();
                if (salario < 0) {
                    throw new IllegalArgumentException("Salário não pode ser negativo");
                }
            }

            return this.getId() + ";" + nome + ";" + cpf + ";" + salario;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao formatar os dados";
        }
    }
}
