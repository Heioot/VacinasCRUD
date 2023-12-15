package poov.vacinascrud.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import poov.modelo.Situacao;
import poov.modelo.Vacina;
import poov.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.modelo.dao.VacinaDAO;
import poov.modelo.dao.core.DAOFactory;

public class TelaSecundariaController implements Initializable{

    private DAOFactory factory;
    private boolean editar;
    public void initialize(URL location, ResourceBundle resources){
        codigoField.setEditable(false);
    }

    public void setDAOFactory (DAOFactory factory) {
        this.factory = factory;
    }


    public void setSelecionado(Vacina selecionado) throws SQLException{
        codigoField.setText(selecionado.getCodigo().toString());
        nomeField.setText(selecionado.getNome());
        descricaoField.setText(selecionado.getDescricao());
        if(!editar){
            Long cod = Long.parseLong("0");
            try {
                factory.abrirConexao();
                VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                List<Vacina> vacinas = dao.findAll();        
                if (!vacinas.isEmpty()) {
                    cod = vacinas.get(vacinas.size() - 1).getCodigo() + Long.parseLong("1");
                }
            } finally {
                factory.fecharConexao();
            }
            codigoField.setText(cod.toString());
        }
    }


    public void setEditar(boolean editar) {
        this.editar = editar;
        codigoField.setEditable(!editar);
        codigoField.setDisable(!editar);
    }

    public TelaSecundariaController() {
        System.out.println("TelaSecundariaController criado");
        ConexaoFactoryPostgreSQL conexao = new ConexaoFactoryPostgreSQL("localhost:5432/vacinascrud", "postgres","heitor6505");
        factory = new DAOFactory(conexao);
    }

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField codigoField;

    @FXML
    private Button confirmarButton;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField nomeField;
    @FXML
    void cancelarButtonClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja sair e cancelar?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            fecharClicado(event);
        }
    }

    @FXML
    void confirmarButtonClick(ActionEvent event) throws IOException, SQLException {
        if (validaCamposSecundaria()) {
            try {
                factory.abrirConexao();
                VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                Vacina vacina = new Vacina();
                vacina.setCodigo(Long.parseLong(codigoField.getText()));
                vacina.setNome(nomeField.getText());
                vacina.setDescricao(descricaoField.getText());
                if(!editar){
                    dao.inserirVacina(vacina);
                }else{
                    dao.atualizarVacina(vacina);
                }

                fecharClicado(event);
            }finally {
                factory.fecharConexao();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ocorreu um erro");
            alert.setHeaderText("Todos os campos dem ser preenchidos!");
            alert.showAndWait();
        }
    }

    public boolean validaCamposSecundaria() {
        return  !nomeField.getText().isBlank() &&
                !descricaoField.getText().isBlank();
    }


    private void mostrarNovaScene(ActionEvent event, String sceneFile) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(sceneFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    void fecharClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }


    public void setTitle(String string) {
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        stage.setTitle(string);
    }


    public TextField getNomeField() {
        return nomeField;
    }


    public TextField getDescricaoField() {
        return descricaoField;
    }


    public TextField getCodigoField() {
        return codigoField;
    }


}
