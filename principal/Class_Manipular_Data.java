
package principal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Class_Manipular_Data {
    
    public Class_Manipular_Data() {
        
    }
    
    public String retornaDataFormatoMySQL(String dataFormatoBrasil, String hora) {
        String dia = dataFormatoBrasil.substring(0, 2);
        String mes = dataFormatoBrasil.substring(3, 5);
        String ano = dataFormatoBrasil.substring(6, 10);
        
        String data = ano+"/"+mes+"/"+dia+" "+hora+":00";
        return data;
    }
    
    public int retornaQntAnos(String dataNascimentoFormatoBrasil) {
        int diasVividos = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dia = dataNascimentoFormatoBrasil.substring(0, 2);
            String mes = dataNascimentoFormatoBrasil.substring(3, 5);
            String ano = dataNascimentoFormatoBrasil.substring(6, 10);

            Date nascimento = sdf.parse(ano+"/"+mes+"/"+dia);
            diasVividos = (int) ((new Date().getTime() - nascimento.getTime()) / 86400000L);
            diasVividos = diasVividos / 365;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diasVividos;
    }
    
}
