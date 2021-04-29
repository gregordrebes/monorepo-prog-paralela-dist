package guimaraes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import javax.swing.JTextPane;

public class ClientTCP implements Runnable {
    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;
    
    private JTextPane map;
    
    private boolean start = false;
    private boolean finish = false;
    private String mapStr = "";
    
    public ClientTCP( JTextPane map ) throws Exception {
        this.map = map;
        start();
    }

    public void connect() throws IOException {
        socket = new Socket( "127.0.0.1", 12345 );
        ou = socket.getOutputStream();
        ouw = new OutputStreamWriter( ou );
        bfw = new BufferedWriter( ouw );
        bfw.write( "\r\n" );
        bfw.flush();
    }
    
    public void doAction( String action ) {
        try {
            bfw.write( action + "\r\n" );
            bfw.flush();
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }

    public void listen() throws IOException {
        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader( in );
        BufferedReader bfr = new BufferedReader( inr );
        String action = "";

        while ( ! action.equalsIgnoreCase( "X" ) ) {
            if ( bfr.ready() ) {
                action = bfr.readLine();
                
                if ( action.contains( "START" ) ) {
                    mapStr = "";
                }
                
                else if ( action.contains( "END" ) ) {
                    map.setText( mapStr );
                }
                
                else {
                    mapStr += action + "\n";
                }
            }
        }
    }

    public void exit() throws IOException{
        doAction( "X" );
        bfw.close();
        ouw.close();
        ou.close();
        socket.close();
    }
    
    @Override
    public void run() {
        try {  
            while ( true ) {
                this.listen();
            }

        } catch (IOException e) {
            System.out.println( e );
        }
    }
    
    private void start() throws Exception {
        connect();
        
        Thread t = new Thread( this );
        t.start();
    }
}