package poov.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import poov.modelo.dao.core.ConnectionFactory;

public class ConexaoFactoryPostgreSQL implements ConnectionFactory {
    
    private String dbURL;
    private String user;
    private String password;
    private static final String classeDriver = "org.postgresql.Driver";
    private static final String caminho = "jdbc:postgresql";
    
    /**
     * 
     * @param dbURL     silly.db.elephantsql.com:5432/tplykbkp
     * @param user      tplykbkp
     * @param password  yln0n1q5zcgZ3K58NWXeG2N2Gvl2MRKR
     */
    public ConexaoFactoryPostgreSQL(String dbURL, String user, String password) {
        this.dbURL = dbURL;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        String url = caminho + "://" + dbURL;
        Connection conexao = null;
        try {
            System.out.println("Conectando com o banco de dados.");
            Class.forName(classeDriver);
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o banco de dados estabelecida.");
            return conexao;
        } catch (Exception e) {
            System.out.println("Erro obtendo uma conexão com o banco de dados.");
            e.printStackTrace();
            return null;
        }
    }

    
}
