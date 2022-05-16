package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RelacoesEntreMoedas {
	
	static String webService = "https://economia.awesomeapi.com.br/last/";
    static int codigoSucesso = 200;
    private Alert alertaErro = new Alert(AlertType.ERROR);

	
	public String valorDeConversao(String moedas) {
		
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
            	
            	if (y[i].contains("high")) {
            		
            		String[] z = y[i].split(":");
            		valor = z[1].replace("\"", "");
            	}
            }
            
            return valor;
        } catch (Exception e) {
            System.out.println("Erro aq");
        }
		return urlParaChamada;
	}
		
	public String configurarConversao(String tipoMoeda1, String tipoMoeda2) {
		
		String configuracao = null;
		
		String m1 = null;
		String m2 = null;
		
		
		if (tipoMoeda1.equals(tipoMoeda2)) {
			
			alertaErro.setContentText("Selecione diferentes moedas!");
			alertaErro.showAndWait();
			
			return null;
		}
		
		
		// Identificando a moeda de origem:
		if (tipoMoeda1.equals("Real")) {
			m1 = "BRL";
		} else if(tipoMoeda1.equals("Euro")) {
			m1 = "EUR";
		} else if(tipoMoeda1.equals("Dolar Americano")) {
			m1 = "USD";
		}
		
		// Identificando a moeda final:
		if (tipoMoeda2.equals("Real")) {
			m2 = "BRL";
		} else if(tipoMoeda2.equals("Euro")) {
			m2 = "EUR";
		} else if(tipoMoeda2.equals("Dolar Americano")) {
			m2 = "USD";
		}
		
		configuracao = m1 + "-" + m2;
		
		return configuracao;
	}
	
	
	public String calcularNovoValor(String valorInicial, String valorDeCambio) {
		
		
		Double v1 = Double.parseDouble(valorInicial);
		Double v2 = Double.parseDouble(valorDeCambio);
		
		Double valorFinal = (v1 * v2);
		
		String valorFinalStr = new DecimalFormat(".##").format(valorFinal);
		//String valorFinalStr = String.valueOf(valorFinal);
		
		
		
		return valorFinalStr;
	}
	
	public String valorValido(String mensagem) {
		
		try {
			String valorFormatado = mensagem.trim().replace(",", ".");
			Double valor = Double.parseDouble(valorFormatado);
			
			return valorFormatado;
			
		} catch(NumberFormatException e) {
			alertaErro.setContentText("Digite um valor válido!");
			alertaErro.showAndWait();
		}
		return null;
	}
	
	
}
