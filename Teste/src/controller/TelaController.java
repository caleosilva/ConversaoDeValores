package controller;

import java.net.URL;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import model.RelacoesEntreMoedas;

public class TelaController implements Initializable{

    @FXML
    private Button botaoCalcular;

    @FXML
    private ImageView iconeApp;

    @FXML
    private ImageView imgClose;

    @FXML
    private ChoiceBox<String> moeda1;

    @FXML
    private ChoiceBox<String> moeda2;
    
    private String[] moedas = {"Real Brasileiro", "Dólar Americano", "Euro", "Peso Colombiano", 
    		"Libra Esterlina", "Rúpia Indiana"};

    @FXML
    private TextField valorEntrada;

    @FXML
    private Label valorsaida;
    
    private Alert alertaErro = new Alert(AlertType.ERROR);


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imgClose.setOnMouseClicked(event -> {
            System.exit(0);
        });
		
		moeda1.setValue("Real Brasileiro");
		moeda1.getItems().addAll(moedas);
		moeda1.setOnAction(this::getMoeda1);
		
		moeda2.setValue("Dólar Americano");
		moeda2.getItems().addAll(moedas);
		moeda2.setOnAction(this::getMoeda2);
	}
	
	@FXML
    void mostrarResultado(ActionEvent event) {
		
		// Instanciando o controller
		RelacoesEntreMoedas rem = new RelacoesEntreMoedas();
		
		// Verificando se o valor é válido
		String valorInput = rem.valorValido(valorEntrada.getText());
		
		if(valorInput != null) {
			
			String m1 = moeda1.getValue();
			String m2 = moeda2.getValue();
			
			// Formatando as moedas para buscar na API
			String config = rem.configurarConversao(m1, m2);
			String novoValor = null;
			
			if (config == null) {
				return;
				
			} else if (config.equals("mesmaMoeda")) {
				valorsaida.setText(valorInput);
				
			} else {
				
				// Recebendo o valor de câmbio
				String valorDeCambio = rem.valorDeConversaoDireta(config);
				
				// Se não for possível converter diretamente:
				if (valorDeCambio == null) {
					System.out.println("Do novo jeito\n");
					
					// Convertendo indiretamente:
					String valorDeCambio1 = rem.moeda1ParaDolar(config);
					System.out.println("valorDeCambio1: " + valorDeCambio1);
					
					String valorDeCambio2 = rem.dolarParaMoeda2(config);
					System.out.println("valorDeCambio2: " + valorDeCambio2);
					
					String valorApoio = rem.calcularNovoValor(valorInput, valorDeCambio1);
					System.out.println("valorApoio1: " + valorApoio);
					
					//valorApoio = rem.valorValido(valorApoio);
					valorApoio = valorApoio.replace(",", ".");
					System.out.println("valorApoio1Formatado: " + valorApoio);
					
					novoValor = rem.calcularNovoValorEFormatar(valorApoio, valorDeCambio2);
					System.out.println("novoValor: " + novoValor);
					
				} else {
					
					System.out.println("Direto");
					
					// Calculando o novo valor
					novoValor = rem.calcularNovoValorEFormatar(valorInput, valorDeCambio);
				}
				
				if (novoValor == null) {
					
					alertaErro.setContentText("A \"API\" utilizada para captar os dados não retornou o valor "
		        			+ "da conversão entre as moedas escolhidas. Por gentileza "
		        			+ "tente novamente mais tarde!");
					alertaErro.showAndWait();
					
				}
				
				
				
				valorsaida.setText(novoValor);
				
			}
			
			
			
		}
    }
	
	public void getMoeda1(ActionEvent event) {
		moeda1.getValue();
	}
	
	public void getMoeda2(ActionEvent event) {
		moeda2.getValue();
	}

}
