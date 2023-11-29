package poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poov.modelo.Situacao;
import poov.modelo.Vacina;
import poov.modelo.dao.core.GenericJDBCDAO;

public class VacinaDAO extends GenericJDBCDAO<Vacina, Long> {

    public VacinaDAO(Connection conexao) {
        super(conexao);
    }
    
    private static final String SQL_QUERY = "SELECT * FROM vacina WHERE nome like ? or descricao like ? or codigo = ? ";
    private static final String FIND_ALL_QUERY = "SELECT codigo, nome, descricao FROM vacina WHERE situacao='ATIVO'";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "where codigo=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "AND upper(nome) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE vacina SET nome=?, descricao=? WHERE codigo = ?";
    private static final String CREATE_QUERY = "INSERT INTO vacina (nome, descricao, situacao) VALUES (?, ?, 'ATIVO')";
    private static final String REMOVE_QUERY = "UPDATE vacina SET situacao = 'INATIVO' WHERE codigo=?";

    @Override
    protected Vacina toEntity(ResultSet resultSet) throws SQLException {
        Vacina vacina = new Vacina();
        vacina.setCodigo(resultSet.getLong("codigo"));
        vacina.setNome(resultSet.getString("nome"));
        vacina.setDescricao(resultSet.getString("descricao"));
        if (resultSet.getString("situacao").equals("ATIVO")) {
            vacina.setSituacao(Situacao.ATIVO);
        } else {
            vacina.setSituacao(Situacao.INATIVO);
        }
        return vacina;
    }

    @Override
    protected void addParameters(PreparedStatement pstmt, Vacina entity) throws SQLException {
        pstmt.setString(1, entity.getNome());
        pstmt.setString(2, entity.getDescricao());
    }

    @Override
    protected String findByKeyQuery() {
        return FIND_BY_KEY_QUERY;
    }

    @Override
    protected String findAllQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected String updateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String createQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String removeQuery() {
        return REMOVE_QUERY;
    }
    
    public List<Vacina> findbyParameters(String nome, String descricao, long codigo, Situacao situacao) {
        int cursor = 1;
        situacao = Situacao.ATIVO;
        String query = "SELECT * FROM vacina where codigo is not null";
        try {
            if (nome != null) {
                query += " and LOWER(nome) like ?";
            }
            if (descricao != null) {
                query += " and descricao  like ?";
            }
            if(codigo != 0){
                query += " and codigo = ?";
            }

            if(situacao == Situacao.ATIVO){
                query += " and situacao = 'ATIVO'";
            }
            PreparedStatement statement = connection.prepareStatement(query);
            if (nome != null) {
                statement.setString(cursor,"%" + nome.toLowerCase() + "%");
                cursor++;
            }
            if (descricao != null) {
                statement.setString(cursor,"%" + descricao.toLowerCase() + "%");
                cursor++;
            }
            if(codigo != 0){
                statement.setLong(cursor,codigo);
                cursor++;
            }

            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Vacina>();
    }
    public void atualizarVacina(Vacina nova) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, nova.getNome());
            statement.setString(2, nova.getDescricao());
            statement.setLong(3, nova.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            showSQLException(e);
        }
        
    }
    public List<Vacina> findByNameLike(String nome) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_LIKE_QUERY);
            statement.setString(1, "%" + nome + "%");
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Vacina>();
    }

        public List<Vacina> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(findAllQuery());
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Vacina>();
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Vacina entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Vacina entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    public void remover(Vacina vacina) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY);
            statement.setLong(1, vacina.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            showSQLException(e);
        }
    }
}
