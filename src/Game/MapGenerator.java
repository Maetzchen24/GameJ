package Game;
import java.awt.*;
import java.util.Random;

public class MapGenerator {
    //--> Members
    public int map[][];
    public Color colors[][];
    public int brickWidth;
    public int brickHeight;


    //--> Constructor
    public MapGenerator(int row, int col) {

        map = new int[row][col];
        colors = new Color[row][col];
        Random rand = new Random();

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();
                if(r == 0 & g == 0 & b == 0)
                {
                    g += 100;
                }
                colors[i][j] = new Color(r, g, b);
            }
        }

        brickWidth = 540/col;
        brickHeight = 230/row;
    }


    //--> Draw graphic bricks
    public void draw(Graphics2D g2) {
        //Random rand = new Random();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    //Main brick
                    //Disco
                    //float r = rand.nextFloat();
                    //float g = rand.nextFloat();
                    //float b = rand.nextFloat();
                    g2.setColor(colors[i][j]);
                    g2.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    //Outline of brick
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.BLACK);
                    g2.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    //-->Set Array value (0 if touched)
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
