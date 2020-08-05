
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ComboBox<Country> cmbBoxCountry;

    @FXML
    private Button trovaVicini;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	cmbBoxCountry.getItems().clear();
    	int anno=0;
    	try {
    		anno=Integer.parseInt(txtAnno.getText());
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero valido.\n");
    		return;
    	}
    	if(anno<1816||anno>2016) {
    		txtResult.appendText("Inserire un anno nel range 1816-2016.\n");
    		return;
    	}
    	model.creaGrafo(anno);
    	for(Country c: model.getCountries()) {
    		txtResult.appendText(c.toString()+": "+ model.getGrafo().degreeOf(c)+" paesi confinanti.\n");
    	}
    	txtResult.appendText("In totale ci sono "+model.numComponents()+" componenti.\n");
    	cmbBoxCountry.getItems().addAll(model.getCountries());
    }
    
    @FXML
    void doTrovaVicini(ActionEvent event) {
    	txtResult.clear();
    	List<Country> componente;
    	Country source=cmbBoxCountry.getValue();
    	if(source==null) {
    		txtResult.appendText("Selezionare un country.\n");
    		return;
    	}
    	componente=model.visitaGrafo(source);
    	if(componente==null) {
    		txtResult.appendText("Ops. Qualcosa è andato storto: il componente è vuoto...\n");
    		return;
    	}
    	txtResult.appendText("Gli stati che fanno parte dello stesso componente di "+source+" sono:\n");
    	for(Country c: componente) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
