
package principal;

public class Class_Consumir_Letras {
    
    public Class_Consumir_Letras() {
        
    }
    
    public void consome(String caracteres, java.awt.event.KeyEvent evt) {
        
        if (!caracteres.contains(evt.getKeyChar()+"")) {
            evt.consume();
        }
    }
    
    public String retiraLetras(String valor) {
        String valorAtualizado = "";
        String caracteres = "1234567890,.";
        for (int i = 0; i < valor.length(); i++)
        {
            if (caracteres.contains(String.valueOf(valor.charAt(i))))
            {
                valorAtualizado = valorAtualizado + String.valueOf(valor.charAt(i));
            }
        }
        return valorAtualizado;
    }
    
    public String retiraLetrasEPontos(String valor) {
        String valorAtualizado = "";
        String caracteres = "1234567890";
        for (int i = 0; i < valor.length(); i++)
        {
            if (caracteres.contains(String.valueOf(valor.charAt(i))))
            {
                valorAtualizado = valorAtualizado + String.valueOf(valor.charAt(i));
            }
        }
        return valorAtualizado;
    }
    
    public String retiraEspaco(String nome) {
        String a, b = "";
        for (int i = 0; i < nome.length(); i++) {
            a = String.valueOf(nome.charAt(i));
            if (!a.equals(" ")) {
                b = a;
            }
        }
        return b;
    }
    
    public void consomeQntDeCaracteres(javax.swing.JTextField campo, int quantidade, java.awt.event.KeyEvent evt) {
        if (campo.getText().length() > quantidade) {
            evt.consume();
        }
    }
    
}
