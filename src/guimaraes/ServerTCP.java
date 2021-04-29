package guimaraes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTCP extends Thread {
    private static ArrayList<BufferedWriter>clients;
    private static ServerSocket server;
    private Socket connection;
    private InputStream input;
    private InputStreamReader inputReader;
    private BufferedReader bufferedReader;
    
    private Game game;

    public ServerTCP( Socket connection ) {
        this.connection = connection;
        
        game = new Game();
        
        try {
            input = connection.getInputStream();
            inputReader = new InputStreamReader( input );
            bufferedReader = new BufferedReader( inputReader );
            
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
    
    public void sendToAll( BufferedWriter bwSaida, String msg ) {
        try {  
            for ( BufferedWriter bw : clients ) {
                bw.write( msg + "\r\n" );
                bw.flush();
            }
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
    
    @Override
    public void run() {
        try {  
            String action;
            OutputStream ou = this.connection.getOutputStream();
            Writer ouw = new OutputStreamWriter( ou );
            BufferedWriter bfw = new BufferedWriter( ouw );
            clients.add( bfw );
            action = bufferedReader.readLine();

            while ( action != null && ! action.equalsIgnoreCase( "X" ) ) {
                action = bufferedReader.readLine();
                
                game.doAction(action);
                
                sendToAll( bfw, game.getField() );
            }

        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
    
    public static void main( String []args ) {
        try {
            server = new ServerSocket(12345);
            clients = new ArrayList<BufferedWriter>();

            while ( true ) {
                System.out.println( "Aguardando conexão..." );
                Socket newConnection = server.accept();
                System.out.println( "Cliente conectado..." );
                Thread t = new ServerTCP( newConnection );
                t.start();
            }

        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
}
