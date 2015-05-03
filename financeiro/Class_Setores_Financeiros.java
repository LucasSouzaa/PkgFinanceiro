
package financeiro;

import conexao_banco.Class_Conexao_Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Class_Setores_Financeiros {
    
    public Class_Setores_Financeiros() {
        
    }
    
    public int retornaIdSetorFinanceiro(String setor) {
        int id = 0;
        try {
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("select id_setor from setores_financeiros "
                    + "where descricao = '"+setor+"'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            id = rs.getInt(1);
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
}
