package exemploservertcp;

/**
 *
 * @author gabriellopes
 */
public class Game {
    
    public static final char DIRECTION_UP     = 'W';
    public static final char DIRECTION_LEFT   = 'A';
    public static final char DIRECTION_DOWN   = 'S';
    public static final char DIRECTION_RIGHT  = 'D';
    
    private static final char FIELD_EMPTY     = '.';
    private static final char FIELD_TIRO      = '*';
    private static final char FIELD_PLAYER_A  = 'A';
    
    
    private static final int NUM_COL = 45;//must be odd 
    private static final int NUM_ROW = 25;//must be odd 
    
    private final int MIDDLE_COL;
    private final int MIDDLE_ROW;
    private final int PLAYER_A_COL;
    private final int PLAYER_A_ROW;
    
    private final char[][] field = new char[NUM_ROW][NUM_COL];
    
    public Game() {
        int auxRow = NUM_ROW/6;
        int auxCol = NUM_COL/6;
        
        this.MIDDLE_COL = NUM_COL/2;
        this.MIDDLE_ROW = NUM_ROW/2;
        
        this.PLAYER_A_COL = auxCol;
        this.PLAYER_A_ROW = auxRow * 2;
        
        initField();
    }
    
    private void initField() {
        
        for ( int row = 0; row < NUM_ROW; row++ ) {
            for ( int col = 0; col < NUM_COL; col++ ) {
                if( col == PLAYER_A_COL && row == PLAYER_A_ROW ){
                    field[row][col] = FIELD_PLAYER_A;
                } else {
                    field[row][col] = FIELD_EMPTY;
                }
               
            }
        }
    }
    
    public void movePlayer(char player, char direction) {
        for ( int col = 0; col < NUM_COL; col++ ) {
            for ( int row = 0; row < NUM_ROW; row++ ) {
                
                if ( field[row][col] == player ) {
                
                    switch (direction) {
                        case DIRECTION_UP:
                            
                            if ( field[row-1][col] == FIELD_EMPTY ) {
                                field[row-1][col] = player;
                                field[row][col] = FIELD_EMPTY;
                            }
                            
                            /*else if ( field[row-1][col] == FIELD_BALL ) {
                                
                                if ( field[row-2][col] == FIELD_EMPTY      ||
                                     field[row-2][col] == FIELD_UPPER_GOAL )
                                {
                                    if ( field[row-2][col] == FIELD_UPPER_GOAL ) {
                                        scoreGoal( TEAM_LOWER );
                                    }
                                    
                                    else {
                                        field[row-2][col] = FIELD_BALL;
                                        field[row-1][col] = player;
                                        field[row][col] = FIELD_EMPTY;
                                    }
                                }
                            }*/
                            
                            break;
                            
                        case DIRECTION_LEFT:
                            
                            if ( col == 0 && field[row][NUM_COL-1] == FIELD_EMPTY ) {
                                field[row][NUM_COL-1] = player;
                                field[row][col] = FIELD_EMPTY;
                            }
                            
                            /*else if ( col == 0 && 
                                      field[row][NUM_COL-1] == FIELD_BALL &&
                                      field[row][NUM_COL-2] == FIELD_EMPTY ) 
                            {
                                field[row][NUM_COL-2] = FIELD_BALL;
                                field[row][NUM_COL-1] = player;
                                field[row][col] = FIELD_EMPTY;
                            }*/
                            
                            else if ( field[row][col-1] == FIELD_EMPTY )
                            {
                                field[row][col-1] = player;
                                field[row][col] = FIELD_EMPTY;
                            }
                            
                            /*else if ( field[row][col-1] == FIELD_BALL ) {
                                
                                if ( col-1 == 0 ) {
                                    if ( field[row][NUM_COL-1] == FIELD_EMPTY ) 
                                    {
                                        field[row][NUM_COL-1] = FIELD_BALL;
                                        field[row][col-1] = player;
                                        field[row][col] = FIELD_EMPTY;
                                    }
                                }
                                
                                else if ( field[row][col-2] == FIELD_EMPTY )
                                {
                                    field[row][col-2] = FIELD_BALL;
                                    field[row][col-1] = player;
                                    field[row][col] = FIELD_EMPTY;
                                }
                            }*/
                            
                            break;
                            
                        case DIRECTION_DOWN:
                            
                            if ( field[row+1][col] == FIELD_EMPTY ) {
                                
                                field[row+1][col] = player;
                                field[row][col] = FIELD_EMPTY;
                            }
                            
                            /*else if ( field[row+1][col] == FIELD_BALL ) {
                                
                                if ( field[row+2][col] == FIELD_EMPTY      ||
                                     field[row+2][col] == FIELD_LOWER_GOAL )
                                {
                                    if ( field[row+2][col] == FIELD_LOWER_GOAL ) {
                                        scoreGoal( TEAM_UPPER );
                                    }
                                    
                                    else {
                                        field[row+2][col] = FIELD_BALL;
                                        field[row+1][col] = player;
                                        field[row][col] = FIELD_EMPTY;
                                    }
                                }
                            } */                          
                            
                            break;
                            
                        case DIRECTION_RIGHT:
                            
                            if ( col == NUM_COL-1 && field[row][0] == FIELD_EMPTY ) {
                                field[row][0] = player;
                                field[row][col] = FIELD_EMPTY;
                            }
                            
                            /*else if ( col == NUM_COL-1 && 
                                      field[row][0] == FIELD_BALL &&
                                      field[row][1] == FIELD_EMPTY ) 
                            {
                                field[row][1] = FIELD_BALL;
                                field[row][0] = player;
                                field[row][NUM_COL-1] = FIELD_EMPTY;
                            }*/
                            
                            else if ( field[row][col+1] == FIELD_EMPTY )
                            {
                                field[row][col+1] = player;
                                field[row][col] = FIELD_EMPTY;
                            }
                            
                            /*else if ( field[row][col+1] == FIELD_BALL ) {
                                
                                if ( col+1 == NUM_COL-1 ) {
                                    if ( field[row][0] == FIELD_EMPTY ) 
                                    {
                                        field[row][0] = FIELD_BALL;
                                        field[row][col+1] = player;
                                        field[row][col] = FIELD_EMPTY;
                                    }
                                }
                                
                                else if ( field[row][col+2] == FIELD_EMPTY )
                                {
                                    field[row][col+2] = FIELD_BALL;
                                    field[row][col+1] = player;
                                    field[row][col] = FIELD_EMPTY;
                                }
                            }*/
                            
                            break;
                            
                        default:
                            break;
                    }
                    
                    return;
                }
            }
        }
    }
    
    public String getField() {
        
        String fieldStr = "\n";
        
        for ( int row = 0; row < NUM_ROW; row++ ) {
            for ( int col = 0; col < NUM_COL; col++ ) {
                fieldStr += field[row][col];
            }
            fieldStr += "\n";
        }
        
        return fieldStr;
        
    }
}
