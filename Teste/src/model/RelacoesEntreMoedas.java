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
	
	// Link base da API.
	static String webService = "https://economia.awesomeapi.com.br/last/";
    static int codigoSucesso = 200;
    
    // PopUp para informar algo.
    private Alert alertaErro = new Alert(AlertType.ERROR);

	/**
	 * M�todo respons�vel por burcar em uma API o valor de convers�o entre duas moedas distintas.
	 * 
	 * @param Moedas As duas moedas escolhidas pelo usu�rio.
	 * @return O valor de convers�o ou null caso n�o encontre.
	 */
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
        	alertaErro.setContentText("A \"API\" utilizada para captar os dados n�o retornou o valor "
        			+ "da convers�o entre as moedas escolhidas. Por gentileza "
        			+ "tente novamente mais tarde!");
			alertaErro.showAndWait();
        }
        
		return urlParaChamada;
	}
	
	/**
	 * M�todo respons�vel por formatar as duas moedas escolhidas pelo usu�rio de tal modo que a API
	 * saiba buscar atrav�s deles.
	 * 
	 * @param tipoMoeda1 Moeda original escolhida.
	 * @param tipoMoeda2 Moeda final escolhida.
	 * @return String com a informa��o de busca formatada.
	 */
	public String configurarConversao(String tipoMoeda1, String tipoMoeda2) {
		
		String configuracao = null;
		
		String m1 = null;
		String m2 = null;
		
		
		if (tipoMoeda1.equals(tipoMoeda2)) {			
			return "mesmaMoeda";
		}
		
		
		// Identificando a moeda de origem:
		if (tipoMoeda1.equals("Real Brasileiro")) {
			m1 = "BRL";
		} else if(tipoMoeda1.equals("Euro")) {
			m1 = "EUR";
		} else if(tipoMoeda1.equals("D�lar Americano")) {
			m1 = "USD";
		} else if(tipoMoeda1.equals("Peso Colombiano")) {
			m1 = "COP";
		} else if(tipoMoeda1.equals("Libra Esterlina")) {
			m1 = "GBP";
		} else if(tipoMoeda1.equals("R�pia Indiana")) {
			m1 = "INR";
		}		
		
		// Identificando a moeda final:
		if (tipoMoeda2.equals("Real Brasileiro")) {
			m2 = "BRL";
		} else if(tipoMoeda2.equals("Euro")) {
			m2 = "EUR";
		} else if(tipoMoeda2.equals("D�lar Americano")) {
			m2 = "USD";
		} else if(tipoMoeda2.equals("Peso Colombiano")) {
			m2 = "COP";
		} else if(tipoMoeda2.equals("Libra Esterlina")) {
			m2 = "GBP";
		} else if(tipoMoeda2.equals("R�pia Indiana")) {
			m2 = "INR";
		}
		
		configuracao = m1 + "-" + m2;
		
		return configuracao;
	}
	
	/**
	 * M�todo respons�vel por calcular o valor referente a convers�o.
	 * 
	 * @param valorInicial Valor inserido pelo usu�rio.
	 * @param valorDeCambio Valor informado pela API.
	 * @return String referente ao novo valor calculado.
	 */
	public String calcularNovoValor(String valorInicial, String valorDeCambio) {
		
		try {
			
			Double v1 = Double.parseDouble(valorInicial);
			Double v2 = Double.parseDouble(valorDeCambio);
			
			Double valorFinal = (v1 * v2);
			
			String valorFinalStr = String.format("%.2f",valorFinal);
			
			return valorFinalStr;
			
		} catch(NumberFormatException NFE) {
			return null;
		}
	}
	
	/**
	 * M�todo respos�vel por verificar se o valor inserido pelo usu�rio � v�lido ou n�o.
	 * 
	 * @param valorInput Informa��o recebida pelo input.
	 * @return Valor formatado da maneira correta.
	 */
	public String valorValido(String valorInput) {
		
		try {
			String valorFormatado = valorInput.trim().replace(",", ".");
			Double valor = Double.parseDouble(valorFormatado);
			
			return valorFormatado;
			
		} catch(NumberFormatException e) {
			alertaErro.setContentText("Digite um valor v�lido!");
			alertaErro.showAndWait();
		}
		return null;
	}
	
	
}
