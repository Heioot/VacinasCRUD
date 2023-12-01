package poov.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import poov.modelo.Pessoa;
import poov.modelo.Situacao;
import poov.modelo.dao.core.GenericJDBCDAO;

public class PessoaDAO extends GenericJDBCDAO<Pessoa, Long> {

    private static final String FIND_ALL_QUERY = "SELECT * FROM pessoa WHERE situacao='ATIVO'";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "AND codigo=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "AND upper(nome) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE pessoa SET nome=?, cpf=?, data=? WHERE codigo = ?";
    private static final String CREATE_QUERY = "INSERT INTO pessoa (nome, cpf, data, situacao) VALUES (?, ?, ?, 'ATIVO')";
    private static final String REMOVE_QUERY = "UPDATE pessoa SET situacao = 'INATIVO' WHERE codigo=?";

    public PessoaDAO(Connection con) {
        super(con);
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Pessoa entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Pessoa entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    @Override
    protected Pessoa toEntity(ResultSet resultSet) throws SQLException {
        Pessoa pessoa = new Pessoa();
        pessoa.setCodigo(resultSet.getLong("codigo"));
        pessoa.setNome(resultSet.getString("nome"));
        pessoa.setCpf(resultSet.getString("cpf"));
        pessoa.setData(resultSet.getDate("data").toLocalDate());
        if (resultSet.getString("situacao").equals("ATIVO")) {
            pessoa.setSituacao(Situacao.ATIVO);
        } else {
            pessoa.setSituacao(Situacao.INATIVO);
        }
        return pessoa;
    }

    @Override
    protected void addParameters(PreparedStatement resultSet, Pessoa entity) throws SQLException {
        resultSet.setString(1, entity.getNome());
        resultSet.setString(2, entity.getCpf());
        resultSet.setDate(3, Date.valueOf(entity.getData()));
        resultSet.setString(4, entity.getSituacao().toString());
    }

    @Override
    protected String findByKeyQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByKeyQuery'");
    }

    @Override
    protected String findAllQuery() {
        return FIND_ALL_QUERY;
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

    public void inserirPessoa(Pessoa nova) {
        try {
            PreparedStatement pstm = connection.prepareStatement(CREATE_QUERY);
            pstm.setString(1, nova.getNome());
            pstm.setString(2, nova.getCpf());
            pstm.setDate(3, Date.valueOf(nova.getData()));
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerPessoa(Pessoa pessoa) {
        try {
            PreparedStatement pstm = connection.prepareStatement(REMOVE_QUERY);
            pstm.setLong(1, pessoa.getCodigo());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pessoa> buscarPorParametros(Pessoa pessoa, LocalDate ini, LocalDate fim){
        String SQL_QUERY = "SELECT * FROM pessoa WHERE situacao = 'ATIVO'";
        if(pessoa.getCodigo() != null){
            SQL_QUERY+=" AND codigo = ?";
        }
        if(pessoa.getNome() != null){
            SQL_QUERY+=" AND LOWER(nome) like ?";
        }
        if(pessoa.getCpf() != null){
            SQL_QUERY+=" AND cpf like ?";
        }
        if(ini != null && fim != null){
            SQL_QUERY+=" AND data BETWEEN ? AND ?";
        }
        
        int parametro = 1;
        try{
            PreparedStatement pstm = connection.prepareStatement(SQL_QUERY);
            if(pessoa.getCodigo() != null){
                pstm.setLong(parametro, pessoa.getCodigo());
                parametro++;
            }
            if(pessoa.getNome() != null){
                pstm.setString(parametro,"%"+ pessoa.getNome().toLowerCase()+"%");
                parametro++;
            }
            if(pessoa.getCpf() != null){
                pstm.setString(parametro,"%"+ pessoa.getCpf().toString()+"%");
                parametro++;
            }
            if(ini != null && fim != null){
                pstm.setDate(parametro,Date.valueOf(ini));
                parametro++;
                pstm.setDate(parametro,Date.valueOf(fim));
            }
            ResultSet rSet = pstm.executeQuery();
            return toEntityList(rSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<Pessoa>();

    }

    public List<Pessoa> buscarTodos() {
        try {
            PreparedStatement pstm = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet rSet = pstm.executeQuery();
            return toEntityList(rSet);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new ArrayList<Pessoa>();
    }

}
