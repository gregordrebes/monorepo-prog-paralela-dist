package exemploservertcp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author gabriellopes
 */
public class ClienteSocket implements Closeable {
    
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    
    public ClienteSocket(final Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public boolean enviaMensagem( String mensagem ) {
        out.println( mensagem );
        
        //Retorna true se não houver nenhum erro ao enviar mensagem ou false caso tenha havido
        return !out.checkError();
    }
    
    public String getMensagem() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar socket: " + e.getMessage());
        }
    }
    
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }
    
    public boolean isOpen() {
        return !socket.isClosed();
    }
    
}
