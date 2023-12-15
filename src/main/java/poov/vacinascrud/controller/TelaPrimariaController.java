package poov.vacinascrud.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import poov.modelo.Situacao;
import poov.modelo.Vacina;
import poov.modelo.dao.AplicacaoDAO;
import poov.modelo.dao.ConexaoFactoryPostgreSQL;
import poov.modelo.dao.PessoaDAO;
import poov.modelo.dao.VacinaDAO;
import poov.modelo.dao.core.DAO;
import poov.modelo.dao.core.DAOFactory;
import poov.vacinascrud.App;
// import poov.vacinascrud.modelo.Pessoa;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import poov.modelo.Aplicacao;
import poov.modelo.Pessoa;

public class TelaPrimariaController implements Initializable {

    private DAOFactory factory;
    private Stage stageNova;
    private Vacina selecionado;
    private Stage stageEditar;
    private Stage stageRemover;

    private TelaSecundariaController telaSecundariaController;

    public TelaPrimariaController() {
        System.out.println("TelaPrimariaController criado");
        ConexaoFactoryPostgreSQL conexao = new ConexaoFactoryPostgreSQL("localhost:5432/vacinascrud", "postgres","heitor6505");
        factory = new DAOFactory(conexao);
    }

    @FXML
    private TextField codigoPessoaField;

    @FXML
    private TableColumn<Pessoa, Long> codigoTablePessoa;

    @FXML
    private TableColumn<Vacina, Long> codigoTableVacina;

    @FXML
    private TextField codigoVacinaField;

    @FXML
    private TextField cpfPessoaField;

    @FXML
    private TableColumn<Pessoa, String> cpfTablePessoa;

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
    //private TableColumn<Pessoa, LocalDate > nascimentoTablePessoa;
    private TableColumn<Pessoa, String > nascimentoTablePessoa;

    @FXML
    private TextField nomePessoaField;

    @FXML
    private TableColumn<Pessoa, String> nomeTablePessoa;

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
        if(tabelaPessoa.getSelectionModel().getSelectedItem() == null ||tabelaVacina.getSelectionModel().getSelectedItem() == null){
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro na Aplicação");
            alerta.setContentText("Selecione uma Pessoa e uma Vacina para realizar a aplicação");
            alerta.showAndWait();
        }else{
            Pessoa slctPessoa = tabelaPessoa.getSelectionModel().getSelectedItem();
            Vacina slctVacina = tabelaVacina.getSelectionModel().getSelectedItem();
            try{
                factory.abrirConexao();
                AplicacaoDAO dao = factory.getDAO(AplicacaoDAO.class);
                dao.novaAplicacao(slctPessoa, slctVacina);
                List<Aplicacao> todas = dao.buscarAplicacoes();

                Alert aviso = new Alert(AlertType.INFORMATION);
                aviso.setTitle("Vacina aplicada com sucesso");
                aviso.setHeaderText(slctPessoa.getNome()+" acabou de receber a "+slctVacina.getNome());
                aviso.setContentText(slctVacina.getDescricao());
                aviso.showAndWait();
                for(Aplicacao a: todas){
                    System.out.println(a);
                }
            }catch(SQLException e){
                e.printStackTrace();
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Sistema fora do ar!");
                alerta.showAndWait();
            }finally{
                factory.fecharConexao();
            }
        }

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
            telaSecundariaController.getCodigoField().setDisable(true);


            

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
        Pessoa nova = new Pessoa();
        LocalDate ini = null;
        LocalDate fim = null;
        
        if(!codigoPessoaField.getText().isBlank()){
            nova.setCodigo(Long.parseLong(codigoPessoaField.getText()));
        }else{
            nova.setCodigo(null);
        }
        if(!nomePessoaField.getText().isBlank()){
            nova.setNome(nomePessoaField.getText());
        }else{
            nova.setNome(null);
        }
        if(!cpfPessoaField.getText().isBlank()){
            nova.setCpf(cpfPessoaField.getText());
        }else{
            nova.setCpf(null);
        }
        if(datePickerApartir.getValue() != null){
            ini = datePickerApartir.getValue();
        }else{
            ini = null;
        }
        if(datePickerAte.getValue() != null){
            fim = datePickerAte.getValue();
        }else{
            fim = null;
        }
        try{
            factory.abrirConexao();
            PessoaDAO dao2 = factory.getDAO(PessoaDAO.class);
            List<Pessoa> disponiveis = dao2.buscarPorParametros(nova, ini, fim);
            tabelaPessoa.getItems().clear();
            tabelaPessoa.getItems().addAll(disponiveis);
            for(Pessoa a : disponiveis){
                // a.setData(  a.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println(a.toString());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            factory.fecharConexao();
        }
    }

    @FXML
    void pesquisarVacinaButtonClick(ActionEvent event) throws SQLException {
        Vacina filtroPesquisar = new Vacina();
    
        if (!isApenasNumeros(codigoVacinaField.getText())) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("O código deve conter apenas números!");
            alert.showAndWait();
            return;
        }
    
        if (!codigoVacinaField.getText().isBlank()) {
            filtroPesquisar.setCodigo(Long.parseLong(codigoVacinaField.getText()));
        } else {
            filtroPesquisar.setCodigo(0L);
        }
    
        if (!nomeVacinaField.getText().isBlank()) {
            filtroPesquisar.setNome(nomeVacinaField.getText());
        } else {
            filtroPesquisar.setNome(null);
        }
    
        if (!descricaoVacinaField.getText().isBlank()) {
            filtroPesquisar.setDescricao(descricaoVacinaField.getText());
        } else {
            filtroPesquisar.setDescricao(null);
        }
    
        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);
            List<Vacina> vacinas = dao.findbyParameters(
                    filtroPesquisar.getNome(),
                    filtroPesquisar.getDescricao(),
                    filtroPesquisar.getCodigo(),
                    filtroPesquisar.getSituacao()
            );
    
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

            PessoaDAO dao2 = factory.getDAO(PessoaDAO.class);
            List<Pessoa> pessoas = dao2.buscarTodos();
            tabelaPessoa.getItems().clear();
            tabelaPessoa.getItems().addAll(pessoas);

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
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Deseja realmente remover a vacina?");
        Optional<ButtonType> result = confirmacao.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
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

        codigoTablePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigo"));
        nomeTablePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
        cpfTablePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("Cpf"));
        //nascimentoTablePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, LocalDate>("Data"));
        nascimentoTablePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("dataBrasil"));
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

    private boolean isApenasNumeros(String texto) {
        for (char caractere : texto.toCharArray()) {
            if (!Character.isDigit(caractere)) {
                return false;
            }
        }
        return true;
    }

}
