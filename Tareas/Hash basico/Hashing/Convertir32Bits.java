import java.util.Scanner;

public class Convertir32Bits {
    public static void main(String[] args) {

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
            //Mostrar resultados
            System.out.println("Palabra: " + palabra);
            System.out.println("Resultado completo: " + resultadoCompleto.toString());
    }
}
