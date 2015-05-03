
package principal;

import javax.swing.JOptionPane;

public class Class_Fechar_Sistema {
    
    /**
     * Este método finaliza o sistema
     */
    public void fecharSistema() {
        
        if (JOptionPane.showConfirmDialog(null, "Deseja realmente sair?", "Atenção", JOptionPane.YES_NO_OPTION)==0) 
        {
            System.exit(0);
        }
        
    }
    
}
