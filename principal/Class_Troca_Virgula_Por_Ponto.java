package principal;

public class Class_Troca_Virgula_Por_Ponto {

    public float trocaVirgulaPorPonto(String ValorAbertura) {
        String value = ValorAbertura;
        int caracterer = value.length();
        int cont = 0;
        String valorfinal = "";
        while (cont < caracterer) {
            String carac = String.valueOf(value.charAt(cont));
            if (carac.equals(",")) {
                String caractere = ".";
                carac = caractere;
                valorfinal = valorfinal + carac;
            } else {
                valorfinal = valorfinal + carac;
            }

            cont++;
        }

        float Valor = 0;
        //no trecho abaixo verifico se existe um ou dois pontos a string passada. Se existir mais de um preciso retirar o primeiro dos pontos.
        int contOcorrenciasPonto = 0;
        int contador = 0;
        while (contador < valorfinal.length()) {
            String carac = String.valueOf(valorfinal.charAt(contador));
            if (carac.equals(".")) {
                contOcorrenciasPonto++;
            }
            contador++;
        }

        if (contOcorrenciasPonto > 1) {
            //no trecho abaixo preciso verificar se existe . e tambem ,. Exemplo: 1,000.00. Ate o trecho acima ficaria 1.000.00 o que da pal. Preciso retirar o primeiro ponto e deixar apenas 1000.00
            int caracterValidacao = valorfinal.length();
            int contValidacao = 0;
            String valorFinalValidado = "";
            while (contValidacao < caracterValidacao) {
                String carac = String.valueOf(valorfinal.charAt(contValidacao));
                if (carac.equals(".") && contValidacao == 1 || carac.equals(".") && contValidacao == 2 || carac.equals(".") && contValidacao == 3) {//pego o contValidacao na posicao 1 porque e aí que se encontra o ponto indesejado.
                    String caractere = "";
                    carac = caractere;
                    valorFinalValidado = valorFinalValidado + carac;
                } else {
                    valorFinalValidado = valorFinalValidado + carac;
                }
                contValidacao++;
            }
            System.out.println(valorFinalValidado);
            Valor = Float.valueOf(valorFinalValidado);
        } else {
            Valor = Float.valueOf(valorfinal);
        }
        return Valor;
    }
}
