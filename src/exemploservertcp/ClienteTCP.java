package exemploservertcp;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author gabriellopes
 */
public class ClienteTCP implements Runnable {
    
    public static final String ADDRESS = "127.0.0.1";
    
    //Captura dados do teclado
    private final Scanner scanner;
    
    //Objeto que armazena alguns dados do cliente
    public static ClienteSocket clienteSocket;
    
    Game game;
    
    public ClienteTCP() {
        scanner = new Scanner( System.in );
    }
    
    public static void main(String[] args) {
        
        try {
            ClienteTCP clienteTCP = new ClienteTCP();
            clienteTCP.start();
        } catch (IOException e) {
            System.out.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }
    
    //Inicia o cliente, conecta ao servidor e entra no loop de envio e recebimento de mensagens
    private void start() throws IOException {
        final Socket socket = new Socket( ADDRESS, ServidorTCP.PORTA );
        clienteSocket = new ClienteSocket( socket );
        System.out.println( "Cliente conectado ao servidor no endereço " + ADDRESS + " e porta " + ServidorTCP.PORTA );
        game = new Game();
        System.out.println( game.getField() );
        
        new Thread( this ).start();
        loopMensagem();
    }
    
    private void loopMensagem() {
        String mensagem;
        do {
            System.out.println("W, A, S and D to move and . to fire (ou 'x' para encerrar): ");
            mensagem = "A"+ "|"+ scanner.nextLine();
      
            clienteSocket.enviaMensagem( mensagem );
            
            String playerStr = mensagem.split("|")[0].toUpperCase();
            String directionStr = mensagem.split("|")[2].toUpperCase();
            
            char player;
            char direction;
            
            String result = "";
            String error = "";
            
            if( playerStr.length() == 1 && directionStr.length() == 1 ) {
                try {
                    player = playerStr.charAt(0);
                    direction = directionStr.charAt(0);
                    
                    game.movePlayer(player, direction);
                    
                    result = game.getField();
                    
                    System.out.println( game.getField() );
                   

                    
                } catch (Exception e) {
                    error = "Error: " + e;
                    result = "";
                }
            }                         
        } while( !"A|x".equalsIgnoreCase( mensagem ) );
        clienteSocket.close();
    }

    @Override
    public void run() {
        String mensagem;
        while( (mensagem = clienteSocket.getMensagem()) != null ) {
            //System.out.println( "Servidor diz: " + mensagem );
            
            String playerStr = mensagem.split("|")[0].toUpperCase();
            String directionStr = mensagem.split("|")[2].toUpperCase();
            
            char player;
            char direction;
            
            String result = "";
            String error = "";
            
            if( playerStr.length() == 1 && directionStr.length() == 1 ) {
                try {
                    player = playerStr.charAt(0);
                    direction = directionStr.charAt(0);
                    
                    System.out.println("P: " + player+ " D: " + direction);
                    
                    game.movePlayer(player, direction);
                    
                    result = game.getField();
                    
                    System.out.println( game.getField() );
                   

                    
                } catch (Exception e) {
                    error = "Error: " + e;
                    result = "";
                }
            }  
        }
    }
      
}
