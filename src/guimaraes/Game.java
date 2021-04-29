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
    public static final String SHOT_UP    = "UP";
    public static final String SHOT_LEFT  = "LEFT";
    public static final String SHOT_DOWN  = "DOWN";
    public static final String SHOT_RIGHT = "RIGHT";
    
    public static final char DIRECTION_UP    = 'W';
    public static final char DIRECTION_LEFT  = 'A';
    public static final char DIRECTION_DOWN  = 'S';
    public static final char DIRECTION_RIGHT = 'D';
    
    private static final char EMPTY_SPACE = ' ';
    private static final char DELIMITER = '#';
    private static final char SHOOT = '*';
    
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
        }
        
        return getField();
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
    
    private void movePlayer( char direction ) {
        
        char player = PLAYER_A;
        
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
                            if ( map[row][col-1] == EMPTY_SPACE ) {
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
                            
                            if ( map[row][col+1] == EMPTY_SPACE ) {
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
    
    public String shoot( String direction ) {
        
        char player = PLAYER_A;
        char shot = SHOOT;
        
        String position = "";
        
        for ( int col = 0; col < COL; col++ ) {
            for ( int row = 0; row < ROW; row++ ) {
                
                if ( map[row][col] == player ) {
                
                    switch ( direction ) {
                        case SHOT_UP:
                            
                            if ( map[row-1][col] == EMPTY_SPACE ) {
                                
                                map[row-1][col] = shot;
                                
                                position = (row-1) + "|" + col;
                            }
                            
                            break;
                            
                        case SHOT_LEFT:
                            if ( map[row][col-1] == EMPTY_SPACE ) {
                                
                                map[row][col-1] = shot;
                                
                                position = row + "|" + (col-1);
                            }
                            
                            break;
                            
                        case SHOT_DOWN:
                            
                            if ( map[row+1][col] == EMPTY_SPACE ) {
                                
                                map[row+1][col] = shot;
                                
                                position = (row+1) + "|" + col;
                            }
                            
                            break;
                            
                        case SHOT_RIGHT:
                            
                            if ( map[row][col+1] == EMPTY_SPACE ) {
                                
                                map[row][col+1] = shot;
                                
                                position = row + "|" + (col+1);
                            }
                                                        
                            break;
                    }
                }
            }
        }
        
        return position;
    }
    
    public String moveShoot ( String position, String direction ) {
        
        String newPosition = "";
        
        String rowStr = position.split("\\|")[0];
        String colStr = position.split("\\|")[1];
        
        int row = Integer.parseInt( rowStr );
        int col = Integer.parseInt( colStr );
        
        char shot = SHOOT;
        
        if ( map[row][col] == shot ) {
                
            switch ( direction ) {
                case SHOT_UP:

                    if ( map[row-1][col] == EMPTY_SPACE ) {

                        map[row-1][col] = shot;
                        map[row][col] = EMPTY_SPACE;

                        newPosition = (row-1) + "|" + col;
                    }
                    
                    else if ( map[row-1][col] == PLAYER_A ||
                              map[row-1][col] == PLAYER_B ||
                              map[row-1][col] == PLAYER_C ||
                              map[row-1][col] == PLAYER_D ) {
                        map[row][col] = EMPTY_SPACE;
                        map[row-1][col] = EMPTY_SPACE;
                    }
                    
                    else map[row][col] = EMPTY_SPACE;

                    break;

                case SHOT_LEFT:
                    
                    if ( map[row][col-1] == EMPTY_SPACE ) {

                        map[row][col-1] = shot;
                        map[row][col] = EMPTY_SPACE;

                        newPosition = row + "|" + (col-1);
                    }

                    else if ( map[row][col-1] == PLAYER_A ||
                              map[row][col-1] == PLAYER_B ||
                              map[row][col-1] == PLAYER_C ||
                              map[row][col-1] == PLAYER_D ) {
                        map[row][col] = EMPTY_SPACE;
                        map[row][col-1] = EMPTY_SPACE;
                    }
                    
                    else map[row][col] = EMPTY_SPACE;
                    
                    break;

                case SHOT_DOWN:

                    if ( map[row+1][col] == EMPTY_SPACE ) {

                        map[row+1][col] = shot;
                        map[row][col] = EMPTY_SPACE;

                        newPosition = (row+1) + "|" + col;
                    }
                    
                    else if ( map[row+1][col] == PLAYER_A ||
                              map[row+1][col] == PLAYER_B ||
                              map[row+1][col] == PLAYER_C ||
                              map[row+1][col] == PLAYER_D ) {
                        map[row][col] = EMPTY_SPACE;
                        map[row+1][col] = EMPTY_SPACE;
                    }
                    
                    else map[row][col] = EMPTY_SPACE;

                    break;

                case SHOT_RIGHT:

                    if ( map[row][col+1] == EMPTY_SPACE ) {

                        map[row][col+1] = shot;
                        map[row][col] = EMPTY_SPACE;

                        newPosition = row + "|" + (col+1);
                    }

                    else if ( map[row][col+1] == PLAYER_A ||
                              map[row][col+1] == PLAYER_B ||
                              map[row][col+1] == PLAYER_C ||
                              map[row][col+1] == PLAYER_D ) {
                        map[row][col] = EMPTY_SPACE;
                        map[row][col+1] = EMPTY_SPACE;
                    }
                    
                    else map[row][col] = EMPTY_SPACE;
                    
                    break;
            }
        }
        
        return newPosition;
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
