public class Player{
    private int x;
    private int y;
    private char name;

    public Player(int x, int y, char name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    
}
