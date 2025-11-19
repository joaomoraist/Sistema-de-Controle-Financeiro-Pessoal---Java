package com.joao.controle_financeiro.model.relatorio;

public class TotalMesDTO {
    private int ano;
    private int mes;
    private double total;

    public TotalMesDTO(int ano, int mes, double total){
        this.ano = ano;
        this.mes = mes;
        this.total = total;
    }
    public int getAno(){return ano;}
    public int getMes(){return mes;}
    public double getTotal(){return total;}
}
