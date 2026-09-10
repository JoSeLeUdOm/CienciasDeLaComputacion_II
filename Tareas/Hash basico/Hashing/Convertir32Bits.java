import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Convertir32Bits {
    public static StringBuilder convertir32Bytes(){

        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese una palabra: ");
        String palabra = teclado.nextLine();

        //Crea un acumulador de texto dinámico y eficiente para juntar los bits
        StringBuilder resultadoCompleto = new StringBuilder();


        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);

            //Obtener el valor ASCII
            int ascii = (int) letra; 
            
            //Convertir el valor ASCII a binario (bits)
            String binario = Integer.toBinaryString(ascii);
            
            //Rellenar con ceros a la izquierda para completar 32 bits
            String binario32Bits = String.format("%32s", binario).replace(' ', '0');

            //Juntar la cadena de bits
            resultadoCompleto.append(binario32Bits);

            

        }
            return resultadoCompleto;
    }
    public static String[] dividirArreglo(StringBuilder sb){

    // 1. Calcular la cantidad de fragmentos necesarios
            int longitudTotal = sb.length();
            int tamanoBloque = 16;
            int cantidadBloques = (int) Math.ceil((double) longitudTotal / tamanoBloque);

            String[] resultado = new String[cantidadBloques];

    // 2. Extraer los bloques de 16 caracteres
            for (int i = 0; i < cantidadBloques; i++) {
                int inicio = i * tamanoBloque;
                // Evitar que el índice final exceda la longitud total
                int fin = Math.min(inicio + tamanoBloque, longitudTotal);

                resultado[i] = sb.substring(inicio, fin);
            }
        for (String bloque : resultado) {
            System.out.println("[" + bloque + "]");
        }
            return resultado;

    }
    public static String[] cambioPrimerElemento(String[] list){
        String texto=list[1];
        List<String> letras = Arrays.asList(texto.split(""));


        for (int i=0;i< letras.size()-1;i++){
            letras.set(i,String.valueOf((Integer.parseInt(letras.get(i)))^((Integer.parseInt(letras.get(i+1))))));
        }



        String textoMezclado = String.join("", letras);
        list[1]=textoMezclado;
        for (String bloque : list) {
            System.out.println("[" + bloque + "]");
        }
        return list;
    }
    static void main(String[] args) {
        java.lang.StringBuilder ps1= convertir32Bytes();
        String[] ps2=dividirArreglo(ps1);
        System.out.println("Paso 3");
        String[] ps3=cambioPrimerElemento(ps2);
    }
}
