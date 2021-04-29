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
    
    private static final int SHOOT_SPEED = 200;

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
                
                if ( action.endsWith( Game.SHOT_UP    ) ||
                     action.endsWith( Game.SHOT_LEFT  ) ||
                     action.endsWith( Game.SHOT_DOWN  ) ||
                     action.endsWith( Game.SHOT_RIGHT ) ) {
                    
                    shoot( bfw, action );
                    
                    System.out.println("Shoot");
                }
                
                else {
                    System.out.println("Move");
                    game.doAction( action );
                }
                
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
    
    private void shoot( BufferedWriter bfw, String action ) {
        
        Runnable shoot = new Runnable() {
            
            @Override
            public void run() {
                try {
                    String position = game.shoot( action );
                    
                    sendToAll( bfw, game.getField() );
                    
                    System.out.println("1 " + position);
                    
                    String direction = action;
                    
                    while ( ! position.isEmpty() ) {
                        
                        position = game.moveShoot( position, direction );
                        
                        Thread.sleep( SHOOT_SPEED );
                        
                        sendToAll( bfw, game.getField() );
                    }
                } catch ( InterruptedException e ) {
                    System.out.println( e );
                }

            }
        };
        
        Thread t = new Thread( shoot );
        t.start();
    }
}
