
package conexao_banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Class_Conexao_Banco {
        
    public static String status = "Não conectou...";
    public Class_Conexao_Banco(){
        
    }
    
    //Método de Conexão//
    public java.sql.Connection getConexaoMySQL() {

        Connection connection = null;          //atributo do tipo Connection

        try {

            // Carregando o JDBC Driver padrão
            Class.forName("com.mysql.jdbc.Driver");

            //Configurando a nossa conexão com um banco de dados
            connection = DriverManager.getConnection("url", "user", "pass");

            //Testa sua conexão
            if (connection != null) {
                status = ("STATUS--->Conectado com sucesso!");
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
            }


            return connection;

        } catch (ClassNotFoundException e) {  //Driver não encontrado

            System.out.println("O driver expecificado nao foi encontrado.");

            return null;

        } catch (SQLException e) {

            //Não conseguindo se conectar ao banco

            System.out.println("Nao foi possivel conectar ao Banco de Dados.");

            return null;

        }
    } //Fim do metodo getConexao//

    //status conexao
    public String statusConection() {

        System.out.println(status);
        return status;

    }//fim do metodo status da conexao


    //Método que fecha sua conexão//
    public boolean FecharConexao(/*Connection con*/) {

        try {

            //con.close();
            
            return true;

        } catch (/*SQL*/Exception e) {

            return false;

        }

 

    } // fim do metodo de fechar conexao

}
