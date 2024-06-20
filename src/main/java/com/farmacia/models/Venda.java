package com.farmacia.models;

import java.util.List;
import java.util.UUID;

public class Venda implements Formatacao<Venda> {
    private UUID id;
    private Double precoFinal;
    private UUID clienteId;
    private UUID vendedorId;
    private List<Medicamento> medicamentos;

    public Venda(UUID clienteId, UUID vendedorId, List<Medicamento> medicamentos) {
        this.id = UUID.randomUUID();
        this.clienteId = clienteId;
        this.vendedorId = vendedorId;
        this.medicamentos = medicamentos;
        this.precoFinal = calcularPrecoFinal();
    }


    public Venda(UUID id, Double precoFinal, UUID clienteId, UUID vendedorId, List<Medicamento> medicamentos) {
        this.id = id;
        this.precoFinal = precoFinal;
        this.clienteId = clienteId;
        this.vendedorId = vendedorId;
        this.medicamentos = medicamentos;
    }


    private Double calcularPrecoFinal() {
        double total = 0.0;
        for (Produto medicamento : medicamentos) {
            total += medicamento.getPreco();
        }
        return total;
    }

    @Override
    public String dadosFormatados() {
        String resultado = id + ";" + vendedorId + ";" + clienteId + ";" + precoFinal + "#";

        boolean primeiro = true;
        for (Medicamento medicamento : medicamentos) {
            if (!primeiro) {
                resultado += ",";
            } else {
                primeiro = false;
            }
            resultado += medicamento.dadosFormatados();
        }

        return resultado;
    }

    @Override
    public void atualizarDados(Venda obj) {
        this.precoFinal = obj.precoFinal;
        this.clienteId = obj.clienteId;
        this.vendedorId = obj.vendedorId;
        this.medicamentos = obj.medicamentos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(Double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public List<Medicamento> getProdutos() {
        return medicamentos;
    }

    public void setProdutos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }



    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }



    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public UUID getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(UUID vendedorId) {
        this.vendedorId = vendedorId;
    }
}
