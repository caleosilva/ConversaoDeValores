package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import model.RelacoesEntreMoedas;

public class TestantoTudo {
	
	static String webService = "https://economia.awesomeapi.com.br/last/";
    static int codigoSucesso = 200;
	
	public static void main(String[] args) {
		//RelacoesEntreMoedas rem = new RelacoesEntreMoedas();
		
		// Recebendo o valor de câmbio
		String valorDeCambio = valorDeConversaoDireta("GBP-INR");
		System.out.println(valorDeCambio);
		
		String valorDeCambio1 = moeda1ParaDolar("GBP-INR");
		System.out.println(valorDeCambio1);
		
		
		String valorDeCambio2 = dolarParaMoeda2("GBP-INR");
		System.out.println(valorDeCambio2);
		
		//valorSaida =  valor input x valorDeCambio1 x valorDeCambio2
		
		
		
	}
	
	public static String valorDeConversaoDireta(String moedas) {
		
		String urlParaChamada = webService + moedas;

        try {
        	
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
            
            
            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

            String valor = null;
            
            // Passando a reposta para String e filtrando o valor;
            String x = resposta.lines().collect(Collectors.joining());
            String[] y = x.split(",");
            
            for(int i = 0; i < y.length; i++) {
            	
            	if (y[i].contains("bid")) {
            		
            		String[] z = y[i].split(":");
            		valor = z[1].replace("\"", "");
            	}
            }
            
            return valor;
        } catch (Exception e) {
        	System.out.println("Erro 0");
        }
        
		return null;
	}
	
	
	
	
	
	public static String moeda1ParaDolar(String moedas) {
		
		String[] moedasSeparadas = moedas.split("-");
		String moeda1ParaDolar = moedasSeparadas[0] + "-USD";
         
   
		String urlParaChamada = webService + moeda1ParaDolar;

        try {
        	
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
            
            
            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

            String valor = null;
            
            // Passando a reposta para String e filtrando o valor;
            String x = resposta.lines().collect(Collectors.joining());
            String[] y = x.split(",");
            
            for(int i = 0; i < y.length; i++) {
            	
            	if (y[i].contains("bid")) {
            		
            		String[] z = y[i].split(":");
            		valor = z[1].replace("\"", "");
            	}
            }
            
            return valor;
        } catch (Exception e) {
        	System.out.println("Erro aq 1");
        }
		
		return null;
	}
	
	
	public static String dolarParaMoeda2(String moedas) {
		
		String[] moedasSeparadas = moedas.split("-");
		String dolarParaMoeda2 = "USD-" + moedasSeparadas[1];
		
		String urlParaChamada = webService + dolarParaMoeda2;

        try {
        	
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
            
            
            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

            String valor = null;
            
            // Passando a reposta para String e filtrando o valor;
            String x = resposta.lines().collect(Collectors.joining());
            String[] y = x.split(",");
            
            for(int i = 0; i < y.length; i++) {
            	
            	if (y[i].contains("bid")) {
            		
            		String[] z = y[i].split(":");
            		valor = z[1].replace("\"", "");
            	}
            }
            
            return valor;
        } catch (Exception e) {
        	System.out.println("Erro aq 2");
        }
         
   
		
		
		return null;
	}

}
