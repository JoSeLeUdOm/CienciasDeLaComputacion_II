import java.security.*;
import java.util.*;
import java.util.Base64;
import java.io.*;
import java.nio.file.*;
import java.security.spec.*;
import java.nio.charset.StandardCharsets;

public class FirmaDigital {

    // Archivos donde se guardan TUS claves de forma permanente
    static final String ARCHIVO_CLAVE_PRIVADA = "clave_privada.key";
    static final String ARCHIVO_CLAVE_PUBLICA = "clave_publica.key";

    public static void main(String[] args) throws Exception {

        Scanner teclado = new Scanner(System.in);


        System.out.print("Ingrese el mensaje: ");
        String mensaje = teclado.nextLine();

        PrivateKey clavePrivada = obtenerClavePrivada();
        PublicKey clavePublica = obtenerClavePublica();


        byte[] hash = calcularHash(mensaje);
        System.out.println("Mensaje original: " + mensaje);
        System.out.println("Hash " + Base64.getEncoder().encodeToString(hash));

        System.out.println("y genera la firma usando la clave privada.");
        byte[] firma = firmarMensaje(mensaje, clavePrivada);
        String firmaTexto = Base64.getEncoder().encodeToString(firma);
        System.out.println("Firma generada (Base64): " + firmaTexto);

        System.out.println("\nDATOS QUE RECIBIRIA EL RECEPTOR");
        System.out.println("Mensaje: " + mensaje);
        System.out.println("Firma: " + firmaTexto);
        System.out.println("Clave pública (Base64): " +
                Base64.getEncoder().encodeToString(clavePublica.getEncoded()));

        boolean esValida = verificarFirma(mensaje, firma, clavePublica);
        System.out.println("Resultado de la verificación: " +
                (esValida ? "VERDADERA (true)" : "FALSA (false)"));

    }

    // Si ya existe el archivo de la clave privada, la carga; si no, genera el par y lo guarda.
    public static PrivateKey obtenerClavePrivada() throws Exception {
        if (!Files.exists(Paths.get(ARCHIVO_CLAVE_PRIVADA))) {
            generarYGuardarParDeClaves();
        }
        byte[] bytesClave = Files.readAllBytes(Paths.get(ARCHIVO_CLAVE_PRIVADA));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesClave);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    public static PublicKey obtenerClavePublica() throws Exception {
        if (!Files.exists(Paths.get(ARCHIVO_CLAVE_PUBLICA))) {
            generarYGuardarParDeClaves();
        }
        byte[] bytesClave = Files.readAllBytes(Paths.get(ARCHIVO_CLAVE_PUBLICA));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytesClave);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    // Genera un par de claves RSA de 2048 bits UNA sola vez y las guarda en archivos
    public static void generarYGuardarParDeClaves() throws Exception {
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(2048);
        KeyPair par = generador.generateKeyPair();

        Files.write(Paths.get(ARCHIVO_CLAVE_PRIVADA), par.getPrivate().getEncoded());
        Files.write(Paths.get(ARCHIVO_CLAVE_PUBLICA), par.getPublic().getEncoded());

        System.out.println("(Se generó tu par de claves por primera vez y se guardó en archivos)");
    }

    // Firma el mensaje con la clave privada.
    public static byte[] firmarMensaje(String mensaje, PrivateKey clavePrivada) throws Exception {
        Signature firmador = Signature.getInstance("SHA256withRSA");
        firmador.initSign(clavePrivada);
        firmador.update(mensaje.getBytes(StandardCharsets.UTF_8));
        return firmador.sign();
    }

    // Calcula el hash para mostrarlo durante la demostración.
    public static byte[] calcularHash(String mensaje) throws Exception {
        return Hashing.Hash(mensaje);
    }

    // Verifica la firma con la clave pública.
    public static boolean verificarFirma(String mensaje, byte[] firma, PublicKey clavePublica) throws Exception {
        Signature verificador = Signature.getInstance("SHA256withRSA");
        verificador.initVerify(clavePublica);
        verificador.update(mensaje.getBytes(StandardCharsets.UTF_8));
        return verificador.verify(firma);
    }
}
