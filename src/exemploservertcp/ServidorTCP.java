package exemploservertcp;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gabriellopes
 */
public class ServidorTCP {
    
    public static final int PORTA = 4343;
    
    private ServerSocket servidor;
    
    private final List<ClienteSocket> listaClienteSocket;
    
    public ServidorTCP() {
        listaClienteSocket = new LinkedList<>();
    }
    
    public static void main(String[] args) {
        
        final ServidorTCP servidorTCP = new ServidorTCP();
        try {
            servidorTCP.initServer();
        } catch (IOException e) {
            System.err.println( "Erro ao iniciar servidor: " + e.getMessage() );
        }
        
    }
    
    private void initServer() throws IOException {
        servidor = new ServerSocket(PORTA);   
        
        loopConexaoCliente();
    }
    
    private void loopConexaoCliente() throws IOException {
        try {
            while ( true ) {                
                System.out.println("Aguardando conexão de novo cliente");
                
                final ClienteSocket clienteSocket;
                try {
                    clienteSocket = new ClienteSocket(servidor.accept());
                    System.out.println( "Cliente " + clienteSocket.getRemoteSocketAddress() + " conectado!" );
                } catch (SocketException e) {
                    System.err.println( "Erro ao aceitar conexão do cliente" );
                    System.err.println( e.getMessage() );
                    continue;
                }
                
                //Cria uma nova Thread para executar o método que ficará esperando mensagens do cliente.
                try {
                    new Thread( () -> loopMensagemCliente( clienteSocket ) ).start();
                    listaClienteSocket.add( clienteSocket );
                } catch (OutOfMemoryError e) {
                    System.err.println( "Não foi possível criar Thread para novo cliente. Servidor possivelmente está sobrecarregado" );
                    System.err.println( e.getMessage() );
                    clienteSocket.close();
                }
            }
        } finally {
            stop();
        }
    }
    
    //Fica em loop aguardando mensagens do cliente até que o mesmo se desconecte
    private void loopMensagemCliente( final ClienteSocket clienteSocket ) {
        try {
            String mensagem;
            while( (mensagem = clienteSocket.getMensagem()) != null ) {
                System.out.println( "Mensagem recebida do cliente " + clienteSocket.getRemoteSocketAddress() + ": " + mensagem );
                if( "x".equalsIgnoreCase(mensagem) ) {
                    return;
                }
                
                enviarMsgTodos(clienteSocket, mensagem);
            }
        } finally {
            clienteSocket.close();
        }
    }
    
    /**Encaminha uma mensagem recebida de um cliente para todos os outros clientes
     * Usa um iterator para permitir percorrer a lista de clientes conectados.
     * Neste caso não é usado um for pois, como estamos removendo um cliente
     * da lista caso não consegamos enviar mensagem pra ele (pois ele já
     * desconectou), se fizermos isso usando um foreach, ocorrerá
     * erro em tempo de execução. Um foreach não permite percorrer e modificar
     * uma lista ao mesmo tempo. Assim, a forma mais segura de fazer
     * isso é com um iterator.
     */
    private void enviarMsgTodos( final ClienteSocket  remetente, final String msg ) {
        final Iterator<ClienteSocket> iterator = listaClienteSocket.iterator();
        int count = 0;
        
        while ( iterator.hasNext() ) {
            final ClienteSocket cliente = iterator.next();
            
            if( !cliente.equals( remetente ) ) {
                if( cliente.enviaMensagem( msg ) )
                    count ++;
                else iterator.remove();
            }
        }
        System.out.println( "Mensagem encaminhada para " + count + "clientes" );
    }
    
    private void stop() {
        try {
            System.out.println( "Finalizando servidor" );
            servidor.close();
        } catch (IOException e) {
            System.err.println( "Erro ao fechar socket do servidor: " + e.getMessage() );
        }
    }
}
