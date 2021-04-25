import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTCP {

    public static final int PORTA       = 5000;
    
    public static final String IP       = "";
    public static final String IP_LOCAL = "127.0.0.1";

    public static void main(String[] args) {
        try {
            Socket cliente = new Socket(IP, PORTA);

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            // RECEBE OBJETO (player) E EXIBE NO MAPA
            Object mens =  entrada.readObject();
            System.out.println("O servidor disse: " + mens);

            String aviso = "Meus pêsames";
            saida.writeObject(aviso);

            entrada.close();
            saida.close();
            cliente.close();

            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
