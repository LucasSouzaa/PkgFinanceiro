
package principal;

import javax.swing.JTabbedPane;

public class Class_Verifica_Menu_Aberto {
    
    public Class_Verifica_Menu_Aberto() {
        
    }
    
    /**
     * Este método vai verificar se um determinado menu já está aberto, para não o abrirmos novamente
     * @param Aba Nome da aba que será verificada se já está aberta
     * @return Se retornar >= 0 quer dizer que o menu já foi aberto e o valor de retorno é o index no painel
     */
    public int verificaMenuAberto(JTabbedPane painel_principal, String Aba) {
        
        String nomeAba;
        int flag = -1;
        for (int i = 0; i < painel_principal.getTabCount(); i++)
        {
            nomeAba = painel_principal.getTitleAt(i);
            if (nomeAba.equals(Aba))
            {
                flag = i;
                i = painel_principal.getTabCount();
            }
        }
        
        return flag;
    }
    
}
