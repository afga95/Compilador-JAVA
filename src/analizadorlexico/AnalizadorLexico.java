
package analizadorlexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ALLAN ALENCAR
 */
public class AnalizadorLexico {

    public static int indice;
    public static String lexema;
    public static String letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String digito = "0123456789";
    public static String simbolosimples = ";()*/+-<>:$=,.";
    public static ArrayList<String> simbolocomposto = new ArrayList<>(); 
    public static ArrayList<String> palavrasReservadas = new ArrayList<>();
    public static int tipo = 0;
    public static int linha;
    public static String token = "";
    public static ArrayList<listaTokens> tokenlist = new ArrayList<>();
    public static listaTokens tok;
    
    public static void main(String[] args) {
        palavrasReservadas.add("if");
        palavrasReservadas.add("then");
        palavrasReservadas.add("while");
        palavrasReservadas.add("do");
        palavrasReservadas.add("write");
        palavrasReservadas.add("read");
        palavrasReservadas.add("else");
        palavrasReservadas.add("begin");
        palavrasReservadas.add("end");
        palavrasReservadas.add("real");
        palavrasReservadas.add("integer");
        palavrasReservadas.add("program");
        palavrasReservadas.add("procedure");
        palavrasReservadas.add("var");
        simbolocomposto.add("==");
        simbolocomposto.add("<>");
        simbolocomposto.add(">=");
        simbolocomposto.add("<=");
        simbolocomposto.add(":=");
    
        String nome = "Teste.txt";

        
//        tok.setToken(";");
//        tok.setClasse("simbolosimples");
//        tok.setLinha(0);
//        tokenlist.add(tok);
//        tok = new listaTokens();
//        tok.setToken("1");
//        tok.setClasse("digito");
//        tok.setLinha(10);
//        tokenlist.add(tok);
//        System.out.println(tokenlist.size());
//        for (int i = 0; i < tokenlist.size(); i++) {
//            System.out.println("token : " + tokenlist.get(i).getToken());
//            System.out.println("classe : " + tokenlist.get(i).getClasse());
//            System.out.println("linha : " + tokenlist.get(i).getLinha());
//        }
         
        //String token = "";
        try {
            try (FileReader arq = new FileReader(nome)) {
                BufferedReader lerArq = new BufferedReader(arq);
                String l = lerArq.readLine(); 
                while (l != null) {
                    for (int i = 0; i < l.length(); i++) {
                            token = token + Character.toString(l.charAt(i));
                    }
                    token = token + "\n";
                    l = lerArq.readLine(); // lê da segunda até a última linha
                }
            }
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
              e.getMessage());
        }
        System.out.println(token);
        indice = 0;
        linha = 0;
        //System.out.println("Começando a Analise Lexica!!\n");
        Um();
//        System.out.println("####################################################");
//        for (int i = 0; i < tokenlist.size(); i++) {
//            System.out.println("token : " + tokenlist.get(i).getToken());
//            System.out.println("classe : " + tokenlist.get(i).getClasse());
//            System.out.println("linha : " + tokenlist.get(i).getLinha());
//            System.out.println("");
//        }
//        System.out.println("####################################################");
//        System.out.println("Análise Lexica finalizada com Sucesso!!\n");
        AnalizadorSintatico analiser = new AnalizadorSintatico();
        analiser.analizador(tokenlist);
        
    }
    
    public static void Um(){
        lexema = "";
        tipo = 0;
        if (indice < token.length()) {
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            indice++;
            if(tipo == 0){
                for (int i = 0; i < letras.length(); i++) {
                    if (prox.equals(Character.toString(letras.charAt(i)))) {
                        
                        tipo = 1;
                        i = letras.length()+1;
                    }
                }
            }
            if(tipo == 0){
                for (int i = 0; i < digito.length(); i++) {
                    if (prox.equals(Character.toString(digito.charAt(i)))) {
                        tipo = 2;
                        i = digito.length()+1;
                    }
                }
            }
            if (prox.equals("/") ) {
                String prox1 = Character.toString(token.charAt(indice));
                if (prox1.equals("*")) {
                    lexema = lexema + prox;
                    Onze();
                    tipo = 4;
                }
            }
            if (prox.equals("{")) {
                lexema = lexema + prox;
                Quinze();
                tipo = 6;
            }
            if(tipo == 0){
                for (int i = 0; i < simbolosimples.length(); i++) {
                    if (prox.equals(Character.toString(simbolosimples.charAt(i)))) {
                        tipo = 3;
                        i = simbolosimples.length()+1;
                    }
                }
            }
            if (prox.equals(" ") || prox.equals("\n") || prox.equals("\t")){
                tipo = 5;
            }
            if (tipo == 0) {
                System.out.println("Erro lexico com o token : " + prox + " na linha : " + linha);
                System.exit(0);
            }
            
            if (tipo == 1) {
                lexema = lexema + prox;
                Dois();
            }
            if (tipo == 2) {
                lexema = lexema + prox;
                Quatro();
            }
            if (tipo == 3) {
                lexema = lexema + prox;
                Oito();
            }
            
            if (indice <= token.length()) {
                Um();
            }
            
        }   
    }
    
    public static void Dois(){
        if (indice >=token.length()){
            Tres();
        }
        if (indice < token.length()) {
            tipo = 0;
            String prox = Character.toString(token.charAt(indice));
            indice++;
            if(tipo == 0){
                for (int i = 0; i < letras.length(); i++) {
                    if (prox.equals(Character.toString(letras.charAt(i)))) {
                        tipo = 1;
                        i = letras.length()+1;
                    }
                }
            }
            if(tipo == 0){
                for (int i = 0; i < digito.length(); i++) {
                    if (prox.equals(Character.toString(digito.charAt(i)))) {
                        tipo = 2;
                        i = digito.length()+1;
                    }
                }
            }   
            
            if (tipo == 1 || tipo == 2) {
                tipo = 0;
                lexema = lexema + prox;
                Dois();
            }else {
                if (prox.equals(" ") || prox.equals(",") || prox.equals(";") || prox.equals(")") || prox.equals("\n") || prox.equals("(") || prox.equals(".")) {
                    indice--;
                    Tres();
                }else{
                    System.out.println("Erro lexico com o token : " + prox + " na linha : " + linha);
                    System.exit(0);
                }
            }
        }
    }
    
    public static void Tres(){
        int cont = 0; 
        for (int i = 0; i < palavrasReservadas.size(); i++) {
            if (palavrasReservadas.get(i).equals(lexema) && !"".equals(lexema)) {
                //System.out.println(lexema + " : palavra reservada" + " -- linha : " + linha);
                tok = new listaTokens();
                tok.setToken(lexema);
                tok.setClasse("palavra reservada");
                tok.setLinha(linha);
                tokenlist.add(tok);
                cont = 1;
            }
        }
        if (cont == 0 && !"".equals(lexema) ) {
            //System.out.println(lexema + " : identificador" + " -- linha : " + linha);
            tok = new listaTokens();
            tok.setToken(lexema);
            tok.setClasse("identificador");
            tok.setLinha(linha);
            tokenlist.add(tok);
        }
        
        Um();
    }
    
    public static void Quatro(){
        if (indice >=token.length()){
            Tres();
        }
        if (indice < token.length()) {
            tipo = 0;
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            indice++;
            if (".".equals(prox)) {   
                lexema = lexema + prox;
                Seis();
            }
            if(tipo == 0){
                for (int i = 0; i < digito.length(); i++) {
                    if (prox.equals(Character.toString(digito.charAt(i)))) {
                        tipo = 2;
                        i = digito.length()+1;
                    }
                }
            } 
            if (tipo == 2) {
                tipo = 0;
                lexema = lexema + prox;
                Quatro();
            }else{
                indice--;
                Cinco();
            }
        }
    }
    
    public static void Cinco(){
        if (!"".equals(lexema) && indice < token.length()) {
            //System.out.println(lexema + " : numero inteiro" + " -- linha : " + linha);
            tok = new listaTokens();
            tok.setToken(lexema);
            tok.setClasse("numero inteiro");
            tok.setLinha(linha);
            tokenlist.add(tok);
            Um();
        }
    }
    
    public static void Seis(){
        if (indice >=token.length()){
            Sete();
        }
        if (indice < token.length()) {
            tipo = 0;
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            //System.out.println(prox);
            indice++;
            if(tipo == 0){
                for (int i = 0; i < digito.length(); i++) {
                    if (prox.equals(Character.toString(digito.charAt(i)))) {
                        tipo = 2;
                        i = digito.length()+1;
                    }
                }
            } 
            if (tipo == 2) {
                tipo = 0;
                lexema = lexema + prox;
                Seis();
            }else{
                indice--;
                Sete();
            }
        }
    } 
    
    public static void Sete(){
        if (!"".equals(lexema) && indice < token.length()) {
            //System.out.println(lexema + " : numero real" + " -- linha : " + linha);
            tok = new listaTokens();
            tok.setToken(lexema);
            tok.setClasse("numero real");
            tok.setLinha(linha);
            tokenlist.add(tok);
            Um();
        }
    }
    
    public static void Oito(){
        if (indice >=token.length()){
            Nove();
        }
        if (indice < token.length()) {
            String simb = lexema;
            String prox = Character.toString(token.charAt(indice));    
            simb = simb + prox;
            for (int i = 0; i < simbolocomposto.size(); i++) {
                if (simb.equals(simbolocomposto.get(i))) {
                    lexema = lexema + prox;
                    indice++;
                    Dez();
                }
            }
            Nove();      
        }
    }
    
    public static void Nove(){
        if (!"".equals(lexema) && indice <= token.length()) {
            //System.out.println(lexema + " : simbolo simples" + " -- linha : " + linha);      
            tok = new listaTokens();
            tok.setToken(lexema);
            tok.setClasse("simbolo simples");
            tok.setLinha(linha);
            tokenlist.add(tok);
        }
        Um(); 
    }
    
    public static void Dez(){
        
        if (!"".equals(lexema) && indice < token.length()) {
            for (int i = 0; i < simbolocomposto.size(); i++) {
                if (lexema.equals(simbolocomposto.get(i)) ) {
                    //System.out.println(lexema + " : simbolo composto" + " -- linha : " + linha);
                    tok = new listaTokens();
                    tok.setToken(lexema);
                    tok.setClasse("simbolo composto");
                    tok.setLinha(linha);
                    tokenlist.add(tok);
                }
            }
            Um();
        }  
    }
    
    public static void Onze(){
       if (indice >=token.length()){
            Quatorze();
        }
        if (indice < token.length()) {
            tipo = 0;
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            indice++;
            lexema = lexema + prox;
            Doze();
        }
    }
    
    public static void Doze(){
        if (indice >=token.length()){
            Quatorze();
        }
        if (indice < token.length()) {
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            indice++;
            lexema = lexema + prox;
            if (prox.equals("*")) {
                String prox1 = Character.toString(token.charAt(indice));
                if (prox1.equals("/")) {
                    Treze();
                }
            }else{
                Doze();
            }
        }
    }
    
    public static void Treze(){
        if (indice >=token.length()){
            Quatorze();
        }
        if (indice < token.length()) {
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            indice++;
            lexema = lexema + prox;
            Quatorze();
        }    
    }
    
    public static void Quatorze(){
        if (!"".equals(lexema) && indice < token.length()) {
            //System.out.println(lexema + " : comentário" + " -- linha : " + linha);
//            tok = new listaTokens();
//            tok.setToken(lexema);
//            tok.setClasse("comentario");
//            tok.setLinha(linha);
//            tokenlist.add(tok);
        }
        Um();
    }
    
    public static void Quinze(){
        if (indice >=token.length()){
            Quatorze();
        }
        if (indice < token.length()) {
            String prox = Character.toString(token.charAt(indice));
            if (prox.equals("\n")) {
                linha++;
            }
            indice++;
            if (!prox.equals("}")) {
                lexema = lexema + prox;
                Quinze();
            }else{
                lexema = lexema + prox;
                Dezesseis();
            }
        }
    }
    
    public static void Dezesseis(){
        if (!"".equals(lexema) && indice < token.length()) {
            //System.out.println(lexema + " : comentario" + " -- linha : " + linha);
//            tok = new listaTokens();
//            tok.setToken(lexema);
//            tok.setClasse("comentario");
//            tok.setLinha(linha);
//            tokenlist.add(tok);
        }
        Um();
    }
}
