import java.util.*;

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
            String binario32Bits = String.format("%32s", binario).replace(' ', '1');

            //Juntar la cadena de bits
            resultadoCompleto.append(binario32Bits);



        }
        return resultadoCompleto;
    }
    public static String[] dividirArreglo(StringBuilder sb, int tamanoBloque){

        // 1. Calcular la cantidad de fragmentos necesarios
        int longitudTotal = sb.length();
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

    //Paso 4 (reutilizable): aplica el mismo "desmadre" que cambioPrimerElemento,
    //pero recibiendo directamente el bloque (String) en lugar del arreglo completo.
    //Esto permite repetirlo sobre CUALQUIER bloque, no solo sobre list[1].
    public static String desmadreBloque(String bloque){
        List<String> letras = new ArrayList<>(Arrays.asList(bloque.split("")));

        for (int i = 0; i < letras.size() - 1; i++) {
            letras.set(i, String.valueOf((Integer.parseInt(letras.get(i))) ^ (Integer.parseInt(letras.get(i + 1)))));
        }

        return String.join("", letras);
    }

    //Paso 5: hace XOR bit a bit entre dos bloques (cadenas de '0' y '1') de la misma longitud
    public static String xorBloques(String bloqueA, String bloqueB){
        int longitud = Math.min(bloqueA.length(), bloqueB.length());
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            char bitA = bloqueA.charAt(i);
            char bitB = bloqueB.charAt(i);
            //XOR de bits: '0'^'0'=0, '1'^'1'=0, '0'^'1'=1, '1'^'0'=1
            char bitResultado = (bitA == bitB) ? '0' : '1';
            resultado.append(bitResultado);
        }

        return resultado.toString();
    }

    //Paso 6: repite el ciclo "desmadre (paso 4) + xor con el siguiente bloque (paso 5)"
    //hasta recorrer todos los bloques del arreglo, y devuelve la palabra final de 16 bits (el hash)
    public static String encadenarBloques(String[] bloques){
        // Validación de seguridad por si el arreglo viene vacío
        if (bloques == null || bloques.length == 0) {
            return "";
        }

        // Usamos StringBuilder para ir uniendo (concatenando) todos los bloques
        StringBuilder bloquesJuntos = new StringBuilder();

        // Arrancamos con el primer bloque ya "desmadrado" (esto es el paso 4 sobre el elemento inicial)
        String acumulado = desmadreBloque(bloques[0]);
        System.out.println("Desmadre inicial (bloque 0): [" + acumulado + "]");

        // Guardamos el primer bloque procesado en nuestra cadena final (primeros 32 caracteres)
        bloquesJuntos.append(acumulado);

        for (int i = 1; i < bloques.length; i++) {
            // Paso 5: xor del acumulado con el siguiente bloque del arreglo
            acumulado = xorBloques(acumulado, bloques[i]);
            System.out.println("Paso 5 - XOR con bloque " + i + ": [" + acumulado + "]");

            // Si quedan más bloques por mezclar, se vuelve a "desmadrar" el resultado (paso 4)
            if (i < bloques.length - 1) {
                acumulado = desmadreBloque(acumulado);
                System.out.println("Paso 6 - Desmadre repetido: [" + acumulado + "]");
            }

            // Añadimos el bloque actual (ya procesado) a la cadena final
            bloquesJuntos.append(acumulado);
        }

        // Devuelve todos los bloques concatenados (ej: si son 3 bloques, devuelve 96 caracteres)
        return bloquesJuntos.toString();
    }

    //Convertir hash a texto

    public static String convertiraTexto(String[] cadena){
        StringBuilder hexString = new StringBuilder();
        for (String bloque : cadena) {
            int valorEntero = Integer.parseUnsignedInt(bloque, 2);
            // Convertir cada bloque binario a entero usando base 2
            String hex = String.format("%02x", valorEntero);
            hexString.append(hex);
        }
        return hexString.toString();

    }
    public static void main(String[] args) {
        java.lang.StringBuilder ps1= convertir32Bytes();
        String[] ps2=dividirArreglo(ps1,16);
        System.out.println("Paso 3");
        String[] ps3=cambioPrimerElemento(ps2);

        System.out.println("Pasos 5 y 6");
        String hashFinalBin = encadenarBloques(ps3);
        String[] hashbloque16=dividirArreglo(new StringBuilder(hashFinalBin),16);

        String hashFinal=convertiraTexto(hashbloque16);
        System.out.println("Hash final (16 bits): [" + hashFinal + "]");


    }
}