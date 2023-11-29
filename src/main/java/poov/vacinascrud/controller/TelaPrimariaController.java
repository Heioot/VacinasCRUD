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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import poov.modelo.Situacao;
import poov.modelo.Vacina;
import poov.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.modelo.dao.VacinaDAO;
import poov.modelo.dao.core.DAO;
import poov.modelo.dao.core.DAOFactory;
import poov.vacinascrud.App;
import poov.vacinascrud.modelo.Pessoa;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import poov.vacinascrud.modelo.Pessoa;

public class TelaPrimariaController implements Initializable {

    private DAOFactory factory;
    private Stage stageNova;
    private Vacina selecionado;
    private Stage stageEditar;
    private Stage stageRemover;

    private TelaSecundariaController telaSecundariaController;

    public TelaPrimariaController() {
        System.out.println("TelaPrimariaController criado");
        ConexaoFactoryPostgreSQL conexao = new ConexaoFactoryPostgreSQL("silly.db.elephantsql.com:5432/tplykbkp", "tplykbkp","yln0n1q5zcgZ3K58NWXeG2N2Gvl2MRKR");
        factory = new DAOFactory(conexao);
    }

    @FXML
    private TextField codigoPessoaField;

    @FXML
    private TableColumn<?, ?> codigoTablePessoa;

    @FXML
    private TableColumn<Vacina, Long> codigoTableVacina;

    @FXML
    private TextField codigoVacinaField;

    @FXML
    private TextField cpfPessoaField;

    @FXML
    private TableColumn<?, ?> cpfTablePessoa;

    @FXML
    private Button criaraplicacaoPessoaButton;

    @FXML
    private DatePicker datePickerApartir;

    @FXML
    private DatePicker datePickerAte;

    @FXML
    private TableColumn<Vacina, String> descricaoTableVacina;

    @FXML
    private TextField descricaoVacinaField;

    @FXML
    private Button editarButton;

    @FXML
    private TableColumn<?, ?> nascimentoTablePessoa;

    @FXML
    private TextField nomePessoaField;

    @FXML
    private TableColumn<?, ?> nomeTablePessoa;

    @FXML
    private TableColumn<Vacina, String> nomeTableVacina;

    @FXML
    private TextField nomeVacinaField;

    @FXML
    private Button novaButton;

    @FXML
    private Button pesquisarPessoaButton;

    @FXML
    private Button pesquisarVacinaButton;

    @FXML
    private Button removerButton;

    @FXML
    private TableView<Vacina> tabelaVacina;

    @FXML
    private TableView<Pessoa> tabelaPessoa;

    @FXML
    void criaraplicacaoButtonClick(ActionEvent event) {

    }

    @FXML
    void datePickerApartirClick(ActionEvent event) {
        // LocalDate dataApartir = datePickerApartir.getValue();
        // System.out.println(dataApartir);
    }

    @FXML
    void datePickerAteClick(ActionEvent event) {
    }

    @FXML
    void editarButtonClick(ActionEvent event) throws IOException, SQLException {
        if (tabelaVacina.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você deve selecionar alguma vacina!");
            alert.showAndWait();
        } else {
            Vacina selected = tabelaVacina.getSelectionModel().getSelectedItem();
            telaSecundariaController.setEditar(true);
            telaSecundariaController.setSelecionado(selected);

            if (stageNova.getOwner() == null) {
                stageNova.initOwner(((Button) event.getSource()).getScene().getWindow());
            }
            stageNova.showAndWait();
            pesquisarVacinaButtonClick(event);
        }

    }

    @FXML
    void novaButtonClick(ActionEvent event) throws IOException, SQLException {
        // mostrarNovaScene(event, "/fxml/telaSecundaria.fxml");
        telaSecundariaController.setEditar(false);
        telaSecundariaController.setSelecionado(new Vacina());
        if (stageNova.getOwner() == null) {
            telaSecundariaController.setTitle("Nova Vacina");
            telaSecundariaController.setSelecionado(new Vacina());
            stageNova.initOwner(((Button) event.getSource()).getScene().getWindow());
        }
        stageNova.showAndWait();
        pesquisarVacinaButtonClick(event);
    }

    @FXML
    void pesquisarButtonPessoaClick(ActionEvent event) {

    }

    @FXML
    void pesquisarVacinaButtonClick(ActionEvent event) throws SQLException {
        Vacina filtropesquisar = new Vacina();
        
        if (!codigoVacinaField.getText().isBlank()) {
            filtropesquisar.setCodigo(Long.parseLong(codigoVacinaField.getText()));
        } else {
            filtropesquisar.setCodigo(Long.parseLong("0"));
        }
        if (!nomeVacinaField.getText().isBlank()) {
            filtropesquisar.setNome(nomeVacinaField.getText());
        } else {
            filtropesquisar.setNome(null);
        }
        if (!descricaoVacinaField.getText().isBlank()) {
            filtropesquisar.setDescricao(descricaoVacinaField.getText());
        } else {
            filtropesquisar.setDescricao(null);
        }

        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);
            List<Vacina> vacinas = dao.findbyParameters(filtropesquisar.getNome(), filtropesquisar.getDescricao(), filtropesquisar.getCodigo(), filtropesquisar.getSituacao());
            tabelaVacina.getItems().clear();
            tabelaVacina.getItems().addAll(vacinas);
            for (Vacina vacina : vacinas) {
                System.out.println(vacina);
            }
        } finally {
            factory.fecharConexao();
        }
    }
    public void inicializarTableVacina() throws SQLException{
        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);
            List<Vacina> vacinas = dao.findbyParameters(null, null, 0, Situacao.ATIVO);
            tabelaVacina.getItems().clear();
            tabelaVacina.getItems().addAll(vacinas);
        } finally {
            factory.fecharConexao();
        }

    }

    @FXML
    void removerButtonClick(ActionEvent event) throws IOException, SQLException {
        if (tabelaVacina.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Você deve selecionar alguma vacina!");
            alert.showAndWait();
        } else {
            try {
                factory.abrirConexao();
                VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                Vacina selected = tabelaVacina.getSelectionModel().getSelectedItem();
                dao.remover(selected);
                tabelaVacina.getItems().clear();
                tabelaVacina.getItems().addAll(dao.findbyParameters(null, null, 0, Situacao.ATIVO));
            } finally {
                factory.fecharConexao();
            }

        }
    }

    private void mostrarNovaScene(ActionEvent event, String sceneFile) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(sceneFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean validaAplicacao() {
        return !codigoVacinaField.getText().isBlank() &&
                !codigoPessoaField.getText().isBlank() &&
                !datePickerApartir.getValue().toString().isBlank() &&
                !datePickerAte.getValue().toString().isBlank();
    }

    public void vacinaTableView() {
        codigoTableVacina.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
        nomeTableVacina.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
        descricaoTableVacina.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vacinaTableView();

        Parent parent;
        stageNova = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/telaSecundaria.fxml"));
        try {
            parent = fxmlLoader.load();
            telaSecundariaController = fxmlLoader.getController();
            telaSecundariaController.setDAOFactory(factory);
            Scene sceneAlterar = new Scene(parent);
            stageNova.setScene(sceneAlterar);
            stageNova.setTitle("Alterar Vacina");
            stageNova.setResizable(false);
            stageNova.initModality(Modality.WINDOW_MODAL);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Erro carregando a aplicação!");
            alert.showAndWait();
        }
        try{

            inicializarTableVacina();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean validaCamposSecundaria() {
        return !codigoVacinaField.getText().isBlank() &&
                !nomeVacinaField.getText().isBlank() &&
                !descricaoVacinaField.getText().isBlank();
    }

}
