/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

import static analizadorlexico.AnalizadorSintatico.tabSim;
import static analizadorlexico.AnalizadorSintatico.tabelaSimb;

/**
 *
 * @author Allan-pc
 */
public class tabelaDeSimbolos {
    private String cadeia ,token, categoria, tipo, escopo;
    private int numPar;
    private float valor;

    public String getCadeia() {
        return cadeia;
    }

    public void setCadeia(String cadeia) {
        this.cadeia = cadeia;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public int getNumPar() {
        return numPar;
    }

    public void setNumPar(int numPar) {
        this.numPar = numPar;
    }
    
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    public void modificar(String token, int numParam){ //adiciona na tabela o numero de parametros de um procedimento
        for (int i = 0; i < tabelaSimb.size(); i++) {
            if (token.equals(tabelaSimb.get(i).getCadeia()) && "proc".equals(tabelaSimb.get(i).getCategoria())) {
                tabSim = new tabelaDeSimbolos();
                tabSim = tabelaSimb.get(i);
                tabSim.setNumPar(numParam);
            }
        }
    }
    
    public void buscar(String token, String scp){   // variavel scp pode receber valor de escopo ou de categoria 
        for (int i = 0; i < tabelaSimb.size(); i++) {
            if (token.equals(tabelaSimb.get(i).getCadeia())) {
                if (scp.equals(tabelaSimb.get(i).getEscopo())) { // verifica se a variveal com o mesmo nome pertence ao mesmo escopo
                    System.out.println("Nome de variavel '"+ token +"' já existe!!");
                    System.exit(0);
                }else if (scp.equals(tabelaSimb.get(i).getCategoria())) { // verifica se o nome de procedimento já existe
                    System.out.println("Nome de procedimento '"+ token +"' já existe!!");
                    System.exit(0);
                }
            }
        }
    }
    
}
