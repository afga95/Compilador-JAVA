/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

import java.util.ArrayList;

/**
 *
 * @author Allan-pc
 */
public class AnalizadorSintatico {
    
    public static ArrayList<tabelaDeSimbolos> tabelaSimb = new ArrayList<>();
    public static ArrayList<String> pilhaVar = new ArrayList<>();
    public static ArrayList<String> pilhaEscopo = new ArrayList<>();
    public static tabelaDeSimbolos tabSim; // limpar lista .clear()
    public static tabelaDeSimbolos tabMetodos = new tabelaDeSimbolos();
    
    public static String escopo = "global";
    public static String cat;
    public static int indice = 0;
    private String Token;
    private String Classe;
    private int linha;
    private int numParam = 0; // é um contador de parametros 
    private String nomeParam; //guarda o nome de um procedimento para conseguir mandar ele como paramentro para o metodo adicionar na tabela de simbolos os numemro parametro que ele tem

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getClasse() {
        return Classe;
    }

    public void setClasse(String Classe) {
        this.Classe = Classe;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    public void proxToken(ArrayList<listaTokens> tok){
        listaTokens objeto = new listaTokens();
        objeto.setClasse(tok.get(indice).getClasse());
        objeto.setLinha(tok.get(indice).getLinha());
        objeto.setToken(tok.get(indice).getToken());
        setToken(objeto.getToken());
        setClasse(objeto.getClasse());
        setLinha(objeto.getLinha());
        indice++;
    } 
    
//    public void modTabParam(String token){ //adiciona na tabela o numero de parametros de um procedimento
//        for (int i = 0; i < tabelaSimb.size(); i++) {
//            if (token.equals(tabelaSimb.get(i).getCadeia()) && "proc".equals(tabelaSimb.get(i).getCategoria())) {
//                tabSim = new tabelaDeSimbolos();
//                tabSim = tabelaSimb.get(i);
//                tabSim.setNumPar(numParam);
//            }
//        }
//    }
//    
//    public void buscaTab(String token, String scp){   // variavel scp pode receber valor de escopo ou de categoria 
//        for (int i = 0; i < tabelaSimb.size(); i++) {
//            if (token.equals(tabelaSimb.get(i).getCadeia())) {
//                if (scp.equals(tabelaSimb.get(i).getEscopo())) { // verifica se a variveal com o mesmo nome pertence ao mesmo escopo
//                    System.out.println("Nome de variavel '"+ token +"' já existe!!");
//                    System.exit(0);
//                }else if (scp.equals(tabelaSimb.get(i).getCategoria())) { // verifica se o nome de procedimento já existe
//                    System.out.println("Nome de procedimento '"+ token +"' já existe!!");
//                    System.exit(0);
//                }
//            }
//        }
//    }
     
    public void analizador(ArrayList<listaTokens> tok){
        
//        pilhaEscopo.add(escopo);
//        pilhaEscopo.add("soma");
//        //pilhaEscopo.remove("soma");
//        for (int i = 0; i < pilhaEscopo.size(); i++) {
//            System.out.println(pilhaEscopo.get(i));
//        }
//        System.exit(0);

        System.out.println("Começando a Analise Sintática\n");
        System.out.println("####################################################");
        programa(tok);
        System.out.println("--------------------------------------");
        
        System.out.println("\nTabela de Simbolos");
        System.out.println("\ncadeia token categoria tipo valor escopo numpar\n");
        for (int i = 0; i < tabelaSimb.size(); i++) {
            System.out.println(tabelaSimb.get(i).getCadeia()+"  "+
                tabelaSimb.get(i).getToken()+"  "+
                tabelaSimb.get(i).getCategoria()+"  "+
                tabelaSimb.get(i).getTipo()+"  "+
                tabelaSimb.get(i).getValor()+"  "+
                tabelaSimb.get(i).getEscopo()+"  "+
                tabelaSimb.get(i).getNumPar());
        }
        System.out.println("--------------------------------------");
    }
    
    public void programa(ArrayList<listaTokens> tok){
        pilhaEscopo.add(escopo);
        proxToken(tok);
        if (Token.equals("program")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha  " + linha + " com o Token " + Token);
            System.exit(0);
        }
        proxToken(tok);
        if (Classe.equals("identificador")) {
            System.out.println(Token);
            tabMetodos.buscar(Token, escopo);
            //buscaTab(Token, escopo);
            tabSim = new tabelaDeSimbolos();
            tabSim.setCadeia(Token); 
            tabSim.setToken("ident");
            tabSim.setCategoria("nome_program");
            tabelaSimb.add(tabSim);            
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        corpo(tok);
        proxToken(tok);
        if(Token.equals(".")){
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        System.out.println("####################################################");
        System.out.println("\nAnalize Sintática feita com Sucesso!!");
    }
    
    public void corpo(ArrayList<listaTokens> tok){
        proxToken(tok);
        dc(tok);
        if (Token.equals("begin")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        comandos(tok);
        proxToken(tok);
        if (Token.equals("end")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        escopo = "global";
    }
    
    public void dc(ArrayList<listaTokens> tok){
        if (Token.equals("var")) {
            dc_v(tok);
            mais_dc(tok);
        }else if (Token.equals("procedure")) {
            dc_p(tok);
            mais_dc(tok);
        }
    }
    
    public void comandos(ArrayList<listaTokens> tok){
        comando(tok);
        if (Token.equals(";")) {
            mais_comandos(tok);
        }
    }
   
    public void dc_v(ArrayList<listaTokens> tok){
        cat = "var";
        if (Token.equals("var")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        variaveis(tok); 
        if (Token.equals(":")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        tipo_var(tok);
    }
    
    public void tipo_var(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals("real") || Token.equals("integer")) {
            System.out.println(Token);
            for (int i = 0; i < pilhaVar.size(); i++) {
                tabSim = new tabelaDeSimbolos();
                tabMetodos.buscar(pilhaVar.get(i), escopo);
                //buscaTab(pilhaVar.get(i), escopo);
                tabSim.setCadeia(pilhaVar.get(i));
                tabSim.setToken("ident");
                tabSim.setCategoria(cat);
                tabSim.setTipo(Token);
                tabSim.setEscopo(escopo);
                tabelaSimb.add(tabSim);
            }
            pilhaVar.clear();
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
    }
    
    public void variaveis(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Classe.equals("identificador")) {
            numParam++;
            System.out.println(Token);
            pilhaVar.add(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        proxToken(tok);
        if (Token.equals(",")) {
            mais_var(tok);
        }
    }
    
    public void mais_var(ArrayList<listaTokens> tok){
        if (Token.equals(",")) {
            System.out.println(Token);
            variaveis(tok);
        }
    }
    
    public void mais_dc(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals(";")) {
            System.out.println(Token);
            proxToken(tok);
            dc(tok);
        }
    }
    
    public void dc_p(ArrayList<listaTokens> tok){
        if (Token.equals("procedure")) {
            System.out.println(Token);
            cat = "proc"; 
            proxToken(tok);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        if (Classe.equals("identificador")) {
            System.out.println(Token);
            nomeParam = Token;
            
            //mudando o escopo da variaveis
            escopo = Token;
            
            tabMetodos.buscar(Token, cat);
            //buscaTab(Token, cat);// verifica se ja existe o nome do proc na tabela de simbolos;
            
            // adicionando na tabela de simbolos cadeia, token e categoria do procedimento
            tabSim = new tabelaDeSimbolos();
            tabSim.setCadeia(Token);
            tabSim.setToken("ident");
            tabSim.setCategoria(cat);
            tabSim.setEscopo(escopo);
            tabelaSimb.add(tabSim);
            
            proxToken(tok);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        if (Token.equals("(")) {
            parametros(tok);
        }
        corpo_p(tok);
    }
    
    public void corpo_p(ArrayList<listaTokens> tok){
        dc_loc(tok);
        if (Token.equals("begin")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        comandos(tok);
        if (Token.equals("end")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
    }
    
    public void mais_comandos(ArrayList<listaTokens> tok){
        if (Token.equals(";")) {
            System.out.println(Token);
            comandos(tok);
        }
    }
    
    public void comando(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals("read")) {
            System.out.println(Token);
            proxToken(tok);
            if (Token.equals("(")) {
                System.out.println(Token);
            }else{
                System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                System.exit(0);
            }
            variaveis(tok);
            if (Token.equals(")")) {
                System.out.println(Token);
                proxToken(tok);
            }
        }else if (Token.equals("write")) {
            System.out.println(Token);
            proxToken(tok);
            if (Token.equals("(")) {
                System.out.println(Token);
            }else{
                System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                System.exit(0);
            }
            variaveis(tok);
            if (Token.equals(")")) {
                System.out.println(Token);
                proxToken(tok);
            }
        }else if (Token.equals("while")) {
            System.out.println(Token);
            condicao(tok);
            if (Token.equals("do")) {
                System.out.println(Token);
                comandos(tok);
                if (Token.equals("$")) {
                    System.out.println(Token);
                    proxToken(tok);
                }else{
                    System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                    System.exit(0);
                }
            }else{
                System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                System.exit(0);
            }
        }else if (Token.equals("if")) {
            System.out.println(Token);
            condicao(tok);
            if (Token.equals("then")) {
                System.out.println(Token);
                comandos(tok);
                if (Token.equals("else")) {
                    pfalsa(tok);
                } 
                if (Token.equals("$")) {
                    System.out.println(Token);
                }else{
                    System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                    System.exit(0);
                }
            }
        }else if (Classe.equals("identificador")) {
            System.out.println(Token);
            restoIdent(tok);
//            if (Token.equals(";")) {
//                System.out.println("allan");
//            }
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
    }
    
    public void restoIdent(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals(":=")) {
            System.out.println(Token);
            expressao(tok);
        }
        if (Token.equals("(")) {
            lista_arg(tok);
            proxToken(tok);
        }
    }
    
    public void lista_arg(ArrayList<listaTokens> tok){
        System.out.println(Token);
        argumentos(tok);
        if (Token.equals(")")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }   
    }
    
    public void argumentos(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Classe.equals("identificador")) {
            System.out.println(Token);
            mais_ident(tok);
        }
    }
    
    public void mais_ident(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals(";")) {
            System.out.println(Token);
            argumentos(tok);
        }
    }
    
    public void pfalsa(ArrayList<listaTokens> tok){
        System.out.println(Token);
        comandos(tok);
    }
    
    public void condicao(ArrayList<listaTokens> tok){
        expressao(tok);
        relacao(tok);
        expressao(tok);
        
    }
    
    public void relacao(ArrayList<listaTokens> tok){
        switch (Token) {
            case "=":
                System.out.println(Token);
                break;
            case ">=":
                System.out.println(Token);
                break;
            case "<=":
                System.out.println(Token);
                break;
            case ">":
                System.out.println(Token);
                break;
            case "<":
                System.out.println(Token);
                break;
            default:
                System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                System.exit(0);
        }
    }
    
    public void expressao(ArrayList<listaTokens> tok){
        termo(tok);
        outros_termos(tok);
    }
    
    public void outros_termos(ArrayList<listaTokens> tok){
        if (Token.equals("+") || Token.equals("-")){
            op_ad(tok);
            termo(tok);
            outros_termos(tok);
        }
    }
    
    public void op_ad(ArrayList<listaTokens> tok){
        System.out.println(Token);
    }
    
    public void termo(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals("+") || Token.equals("-")) {
            op_un(tok);
        }else if (Classe.equals("identificador")) {
            fator(tok);
        }else if (Classe.equals("numero real")) {
            fator(tok);
        }else if (Classe.equals("numero inteiro")) {
            fator(tok);
        }else if (Token.equals("(")) {
            fator(tok);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        
        mais_fatores(tok);
    }
    
    public void mais_fatores(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals("*") || Token.equals("/")) {
            op_mul(tok);
            fator(tok);
            mais_fatores(tok);
        }
    }
    
    public void op_mul(ArrayList<listaTokens> tok){
        System.out.println(Token);
    }
    
    public void fator(ArrayList<listaTokens> tok){
        if (Token.equals("(")) {
            System.out.println(Token);
            expressao(tok);
            proxToken(tok);
            if (Token.equals(")")) {
                System.out.println(Token);
            }else{
                System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
                System.exit(0);
            }
        }else{
            System.out.println(Token);
            if (Classe.equals("numero inteiro")) {
                tabSim = new tabelaDeSimbolos();
                tabSim.setCadeia(Token);
                tabSim.setToken("num");
                tabSim.setTipo("integer");
                tabSim.setValor(Integer.parseInt(Token));
                tabelaSimb.add(tabSim);
            }else if (Classe.equals("numero real")){
                tabSim = new tabelaDeSimbolos();
                tabSim.setCadeia(Token);
                tabSim.setToken("num");
                tabSim.setTipo("real");
                tabSim.setValor(Float.parseFloat(Token));
                tabelaSimb.add(tabSim);
            }
        }
    }
    
    public void op_un(ArrayList<listaTokens> tok){
        if (Token.equals("+")) { 
            System.out.println(Token);
        }else if (Token.equals("-")){
            System.out.println(Token);
        }
    }
    
    public void dc_loc(ArrayList<listaTokens> tok){
        proxToken(tok);
        if (Token.equals("var")) {
            dc_v(tok);
            proxToken(tok);
            if (Token.equals(";")) {
                mais_dcloc(tok);
            }
        }
    }
    
    public void mais_dcloc(ArrayList<listaTokens> tok){
        if (Token.equals(";")) {
            System.out.println(Token);
            dc_loc(tok);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
    }
    
    public void parametros(ArrayList<listaTokens> tok){
        if (Token.equals("(")) {
            System.out.println(Token);
            numParam = 0;
            lista_par(tok);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        if (Token.equals(")")) {
            tabMetodos.modificar(nomeParam, numParam);
            //modTabParam(nomeParam);
            System.out.println(Token);
        }
    }
    
    public void lista_par(ArrayList<listaTokens> tok){
        cat = "param";
        variaveis(tok);
        if (Token.equals(":")) {
            System.out.println(Token);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
        tipo_var(tok);
        proxToken(tok);
        if (Token.equals(";")) {
            mais_par(tok);
        }
    }
    
    public void mais_par(ArrayList<listaTokens> tok){
        if (Token.equals(";")) {
            System.out.println(Token);
            lista_par(tok);
        }else{
            System.out.println("Erro sintático na linha : " + linha + " com o Token " + Token);
            System.exit(0);
        }
    }
}
