public class Map {
    
    private static final char DELIMITER = '#';
    private static final char EMPTY_SPACE = ' ';
    
    private int width = 30; // max col
    private int height = 15; // max lin
    
    private char[][] map = new char[this.height][this.width];

    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public Map(){
        this.buildMap();
    }


    private void buildMap(){
        boolean primeiraOuUltimaLinha, primeiraOuUltimaColuna;
        for (int i = 0; i < this.height; i++) {
            primeiraOuUltimaLinha = i == 0 || i == (this.height - 1); // true se passar em uma das condições
            for (int j = 0; j < this.width; j++) {
                primeiraOuUltimaColuna = j == 0 || j == (this.width - 1); // true se passar em uma das condições
                this.map[i][j] = (primeiraOuUltimaLinha || primeiraOuUltimaColuna) ? Map.DELIMITER : Map.EMPTY_SPACE;
            }
        }
    }

    public void showMap(){
        // System.out.println("\033[H\033[2J"); // retorna cursor para inicio
        for (int i = 0; i < this.height; i++) {
            System.out.println();
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.map[i][j]);
            }
        }
    }

}
