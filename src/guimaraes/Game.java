/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimaraes;

/**
 *
 * @author Gabriel
 */
public class Game {
    public static final char DIRECTION_UP     = 'W';
    public static final char DIRECTION_LEFT   = 'A';
    public static final char DIRECTION_DOWN   = 'S';
    public static final char DIRECTION_RIGHT  = 'D';
    
    private static final char EMPTY_SPACE = ' ';
    private static final char DELIMITER = '#';
    private static final char PLAYER_A = 'A';
    private static final char PLAYER_B = 'B';
    private static final char PLAYER_C = 'C';
    private static final char PLAYER_D = 'D';
    
    private static final int COL = 40;
    private static final int ROW = 15;
    
    private final char[][] map = new char[ROW][COL];
    
    public Game() {
        buildMap();
        spawnPlayers();
    }
    
    public String doAction( String action ) {
        switch ( action ) {
            case "W":
                movePlayer( DIRECTION_UP );
                break;
            case "A":
                movePlayer( DIRECTION_LEFT );
                break;
            case "S":
                movePlayer( DIRECTION_DOWN );
                break;
            case "D":
                movePlayer( DIRECTION_RIGHT );
                break;
            case "UP":
//                "Atirar para cima"
                break;
            case "LEFT":
//                "Atirar para esquerda"
                break;
            case "DOWN":
//                "Atirar para baixo"
                break;
            case "RIGHT":
//                "Atirar para direita"
                break;
        }
        
        return getField();
    }
    
    public void movePlayer( char direction ) {
        
        char player = 'A';
        
        for ( int col = 0; col < COL; col++ ) {
            for ( int row = 0; row < ROW; row++ ) {
                
                if ( map[row][col] == player ) {
                
                    switch (direction) {
                        case DIRECTION_UP:
                            
                            if ( map[row-1][col] == EMPTY_SPACE ) {
                                map[row-1][col] = player;
                                map[row][col] = EMPTY_SPACE;
                            }
                            
                            break;
                            
                        case DIRECTION_LEFT:
                            if ( map[row][col-1] == EMPTY_SPACE )
                            {
                                map[row][col-1] = player;
                                map[row][col] = EMPTY_SPACE;
                            }
                            
                            break;
                            
                        case DIRECTION_DOWN:
                            
                            if ( map[row+1][col] == EMPTY_SPACE ) {
                                
                                map[row+1][col] = player;
                                map[row][col] = EMPTY_SPACE;
                            }
                            
                            break;
                            
                        case DIRECTION_RIGHT:
                            
                            if ( map[row][col+1] == EMPTY_SPACE )
                            {
                                map[row][col+1] = player;
                                map[row][col] = EMPTY_SPACE;
                            }
                                                        
                            break;
                    }
                    
                    return;
                }
            }
        }
    }
    
    public String getField() {
        String fieldStr = "";
        
        for ( int row = 0; row < ROW; row++ ) {
            for ( int col = 0; col < COL; col++ ) {
                fieldStr += map[row][col];
            }
            fieldStr += "\n";
        }
        
        return "START \n" + fieldStr + "END";
    }
    
    private void buildMap() {
        boolean firstOrLastRow, firstOrLastCol;
        
        for ( int row = 0; row < this.ROW; row++ ) {
            
            firstOrLastRow = row == 0 || row == ( this.ROW - 1 );
            
            for (int col = 0; col < this.COL; col++) {
                firstOrLastCol = col == 0 || col == ( this.COL - 1 );
                
                this.map[row][col] = ( firstOrLastRow || firstOrLastCol ) ? DELIMITER : EMPTY_SPACE;
            }
        }
    }
    
    private void spawnPlayer( char player ) {
        
        int auxRow = ROW/4;
        int auxCol = COL/4;
        
        switch ( player ) {
            case PLAYER_A:
                this.map[auxRow][auxCol] = PLAYER_A;
                break;
            case PLAYER_B:
                this.map[ROW - auxRow -1][auxCol] = PLAYER_B;
                break;
            case PLAYER_C:
                this.map[auxRow][COL - auxCol - 1] = PLAYER_C;
                break;
            case PLAYER_D:
                this.map[ROW - auxRow -1][COL - auxCol - 1] = PLAYER_D;
                break;
        }
    }
    
    private void spawnPlayers() {
        spawnPlayer( PLAYER_A );
        spawnPlayer( PLAYER_B );
        spawnPlayer( PLAYER_C );
        spawnPlayer( PLAYER_D );
    }
}
