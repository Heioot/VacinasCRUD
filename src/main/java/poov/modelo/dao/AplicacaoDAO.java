package poov.modelo.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import poov.modelo.Aplicacao;
import poov.modelo.Pessoa;
import poov.modelo.Situacao;
import poov.modelo.Vacina;
import poov.modelo.dao.core.GenericJDBCDAO;


public class AplicacaoDAO extends GenericJDBCDAO<Aplicacao, Long>{

    public AplicacaoDAO(Connection connection) {
        super(connection);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Aplicacao entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Aplicacao entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    @Override
    protected Aplicacao toEntity(ResultSet resultSet) throws SQLException {
        Aplicacao nova = new Aplicacao();
        nova.setCodigo(resultSet.getLong("codigo"));
        nova.setData(resultSet.getDate("data").toLocalDate());
        nova.setCodigo_pessoa(resultSet.getLong("codigo_pessoa"));
        nova.setCodigo_vacina(resultSet.getLong("codigo_vacina"));
        if (resultSet.getString("situacao").equals("ATIVO")) {
            nova.setSituacao(Situacao.ATIVO);
        } else {
            nova.setSituacao(Situacao.INATIVO);
        }
        return nova;
    }

    @Override
    protected void addParameters(PreparedStatement resultSet, Aplicacao entity) throws SQLException {
        resultSet.setDate(1, Date.valueOf(entity.getData()));
        resultSet.setLong(2, entity.getCodigo_pessoa());
        resultSet.setLong(3, entity.getCodigo_vacina());
        resultSet.setString(4, entity.getSituacao().toString());
    }

    public void novaAplicacao(Pessoa p, Vacina v){
        String INSERT_QUERY = "INSERT INTO aplicacao(data, codigo_pessoa, codigo_vacina, situacao) VALUES(?, ?, ?, 'ATIVO')";
        try{
            PreparedStatement ppstt = connection.prepareStatement(INSERT_QUERY);
            ppstt.setDate(1, Date.valueOf(LocalDate.now()));
            ppstt.setLong(2, p.getCodigo());
            ppstt.setLong(3, v.getCodigo());
            ppstt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<Aplicacao> buscarAplicacoes(){
        String SQL_QUERY = "SELECT * FROM aplicacao";
        try{
            PreparedStatement ppstt = connection.prepareStatement(SQL_QUERY);
            ResultSet rSet = ppstt.executeQuery();
            return toEntityList(rSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<Aplicacao>();
    }
    @Override
    protected String findByKeyQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByKeyQuery'");
    }

    @Override
    protected String findAllQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllQuery'");
    }

    @Override
    protected String updateQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateQuery'");
    }

    @Override
    protected String createQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createQuery'");
    }

    @Override
    protected String removeQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeQuery'");
    }
    
}
