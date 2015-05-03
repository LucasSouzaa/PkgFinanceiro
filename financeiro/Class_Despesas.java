
package financeiro;

import principal.Class_Troca_Virgula_Por_Ponto;
import conexao_banco.Class_Conexao_Banco;
import formas_pagamento.Class_Formas_Pagto;
import fornecedores.Class_Fornecedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Class_Despesas {
    
    public Class_Despesas() {
        
    }
    
    public void cadastraDespesa(int id_compra, String descricao, String responsavel, String fornecedor, int qnt_parcelas, 
            String data_pagamento, String data_vencimento, String forma_pagamento, String valor, String acrescimo, 
            String desconto, int fixo, int variavel, String setor, int id_usuario, int id_movimentacao_caixa, 
            int id_movimentacao_conta_bancaria) {
        try {
            int dias = 0;
            int liquidado = 0;
            if (forma_pagamento.contains("Cartão")) {
                liquidado = 1;
            }
            
            Class_Fornecedores forn = new Class_Fornecedores();
            int id_fornecedor = forn.retornaIdFornecedor(fornecedor);
            
            Class_Formas_Pagto formas = new Class_Formas_Pagto();
            int id_forma_pagamento = formas.retornaIdFormaPagamento(forma_pagamento);
            
            Class_Setores_Financeiros setores = new Class_Setores_Financeiros();
            int id_setor = setores.retornaIdSetorFinanceiro(setor);

            Class_Troca_Virgula_Por_Ponto troca = new Class_Troca_Virgula_Por_Ponto();
            float valorDespesa = troca.trocaVirgulaPorPonto(valor);
            valorDespesa = valorDespesa / qnt_parcelas;
            float Acrescimo = troca.trocaVirgulaPorPonto(acrescimo);
            float Desconto = troca.trocaVirgulaPorPonto(desconto);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(data_vencimento);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);

            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("insert into despesas "
                    + "(id_compra_fk, descricao, responsavel, id_fornecedor_fk, qnt_parcelas, parcela_numero, data_pagamento, "
                    + "data_vencimento, id_forma_pagamento_fk, valor, acrescimo, desconto, fixo, variavel, liquidado, id_setor_fk, "
                    + "id_usuario_fk, id_movimentacao_caixa_fk, id_movimentacao_conta_bancaria_fk) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 1; i <= qnt_parcelas; i++) {
                ps.setInt(1, id_compra);
                ps.setString(2, descricao);
                ps.setString(3, responsavel);
                ps.setInt(4, id_fornecedor);
                ps.setInt(5, qnt_parcelas);
                ps.setInt(6, i);
                ps.setString(7, data_pagamento);
                ps.setString(8, data_vencimento);
                ps.setInt(9, id_forma_pagamento);
                ps.setFloat(10, valorDespesa);
                ps.setFloat(11, Acrescimo);
                ps.setFloat(12, Desconto);
                ps.setInt(13, fixo);
                ps.setInt(14, variavel);
                ps.setInt(15, liquidado);
                ps.setInt(16, id_setor);
                ps.setInt(17, id_usuario);
                ps.setInt(18, id_movimentacao_caixa);
                ps.setInt(19, id_movimentacao_conta_bancaria);
                ps.executeUpdate();

                if (forma_pagamento.contains("Cartão")) {
                    dias = formas.retornaDiasCartao(forma_pagamento);
                }
                else
                {
                    liquidado = 0;
                }

                calendar.add(Calendar.DAY_OF_MONTH, dias);
                data_vencimento = sdf.format(calendar.getTime());
                data_pagamento = null;
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e, "Atenção", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void alteraDespesaPelaMovimentacaoCaixa(int id_movimentacao_caixa, String descricao, String forma_pagamento, 
            String valor) {
        
        Class_Formas_Pagto formas = new Class_Formas_Pagto();
        int id_forma_pagamento = formas.retornaIdFormaPagamento(forma_pagamento);
        
        Class_Troca_Virgula_Por_Ponto troca = new Class_Troca_Virgula_Por_Ponto();
        float Valor = troca.trocaVirgulaPorPonto(valor);
        
        try {
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("UPDATE despesas SET "
                    + "descricao = '"+descricao+"', "
                    + "id_forma_pagamento_fk = '"+id_forma_pagamento+"', "
                    + "valor = '"+Valor+"' "
                    + "WHERE id_movimentacao_caixa_fk = '"+id_movimentacao_caixa+"'");
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void excluiDespesaPelaMovimentacaoCaixa(int id_movimentacao_caixa) {
        try {            
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("UPDATE despesas SET "
                    + "excluido = 1 "
                    + "WHERE id_movimentacao_caixa_fk = '"+id_movimentacao_caixa+"'");
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void alteraDespesaPelaMovimentacaoContaBancaria(int id_movimentacao_conta_bancaria, String descricao, 
            String forma_pagamento, String valor) {
        
        Class_Formas_Pagto formas = new Class_Formas_Pagto();
        int id_forma_pagamento = formas.retornaIdFormaPagamento(forma_pagamento);
        
        Class_Troca_Virgula_Por_Ponto troca = new Class_Troca_Virgula_Por_Ponto();
        float Valor = troca.trocaVirgulaPorPonto(valor);
        
        try {
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("UPDATE despesas SET "
                    + "descricao = '"+descricao+"', "
                    + "id_forma_pagamento_fk = '"+id_forma_pagamento+"', "
                    + "valor = '"+Valor+"' "
                    + "WHERE id_movimentacao_conta_bancaria_fk = '"+id_movimentacao_conta_bancaria+"'");
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void excluiDespesaPelaMovimentacaoContaBancaria(int id_movimentacao_conta_bancaria) {
        try {            
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("UPDATE despesas SET "
                    + "excluido = 1 "
                    + "WHERE id_movimentacao_conta_bancaria_fk = '"+id_movimentacao_conta_bancaria+"'");
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void carregaDespesas(String situacao, String data_de, String data_ate, DefaultTableModel tabela) {
        
        String trechoLiquidado = "";
        if (situacao.equals("Liquidado")) {
            trechoLiquidado = " AND despesas.liquidado = 1";
        } else if (situacao.equals("Não liquidado")) {
            trechoLiquidado = " AND despesas.liquidado = 0";
        }
        
        Object linha[] = new Object[8];
        float valorFloat = 0;
        String valor, liquidado;
        NumberFormat z = NumberFormat.getCurrencyInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        try {            
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("SELECT despesas.*, formas_pagamento.descricao "
                    + "FROM despesas "
                    + "INNER JOIN formas_pagamento ON despesas.id_forma_pagamento_fk = formas_pagamento.id_forma_pagamento "
                    + "WHERE despesas.excluido = 0 AND despesas.data_vencimento between '"+data_de+"' AND '"+data_ate+"' "
                    + ""+trechoLiquidado+" ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                valorFloat = rs.getFloat("despesas.valor") + rs.getFloat("despesas.acrescimo") - rs.getFloat("despesas.desconto");
                valor = z.format(valorFloat);
                
                if (rs.getInt("despesas.liquidado") == 0) {
                    liquidado = "Não";
                } else {
                    liquidado = "Sim";
                }
                
                linha[0] = rs.getString("despesas.Descricao");
                linha[1] = rs.getString("formas_pagamento.descricao");
                linha[2] = valor;
                linha[3] = rs.getInt("despesas.qnt_parcelas");
                linha[4] = sdf.format(rs.getDate("despesas.data_vencimento"));
                linha[5] = liquidado;
                linha[6] = "Despesa";
                linha[7] = rs.getInt("despesas.id_despesa");
                tabela.addRow(linha);
            }
            
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void liquidarDespesa(int id_despesa) {
        try {
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("UPDATE despesas SET liquidado = 1 WHERE id_despesa = '"+id_despesa+"'");
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void desliquidarDespesa(int id_despesa) {
        try {
            Class_Conexao_Banco banco = new Class_Conexao_Banco();
            Connection conn = banco.getConexaoMySQL();
            PreparedStatement ps = conn.prepareStatement("UPDATE despesas SET liquidado = 0 WHERE id_despesa = '"+id_despesa+"'");
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
