package Game;
import Game.MapGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    //--> Members
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 40; //Total number of bricks

    private Timer timer;
    private int delay = 8; //Time delay

    private int playerX = 300;

    private int ballposX = playerX + 40;
    private int ballposY = 530;
    private int ballXdir = -4; //Also for speed
    private int ballYdir = -5; //Also for speed

    private MapGenerator map;
    private JFrame Pw;

    //--> Constructor
    public Gameplay(JFrame p_Pw) {
        Pw = p_Pw;
        map = new MapGenerator(6, 8);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    //--> Graphics
    public void paint(Graphics g) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D) g);

        //border
        g.setColor(Color.gray);
        g.fillRect(0,0,5,592); //left
        g.fillRect(0,0,692,5); //upper
        g.fillRect(680,0,5,592); //right

        //paddle
        g.setColor(new Color(171, 171, 171));
        g.fillRect(playerX, 550,100,8);

        //ball
        g.setColor(new Color(237, 237, 237));
        g.fillOval(ballposX, ballposY,20,20);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 620, 30);


        //Win condition (All bricks gone)
        if(totalBricks <= 0) {
            play = false;
            ballYdir = 0;
            ballXdir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("Monaco", Font.BOLD, 28));
            g.drawString("Well Done! Your Score: " + score, 205, 300);

            g.setFont(new Font("Monaco", Font.BOLD, 20));
            g.drawString("Press ENTER to restart", 220, 450);
        }

        //Lose condition (ball falls through)
        if(ballposY > 560) {
            play = false;
            ballYdir = 0;
            ballXdir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("Monaco", Font.BOLD, 28));
            g.drawString("Game Over.", 248, 330);

            g.setFont(new Font("Monaco", Font.BOLD, 25));
            g.drawString(" Score: " + score, 260, 380);

            g.setFont(new Font("Monaco", Font.BOLD, 20));
            g.drawString("Press ENTER to restart", 220, 450);
        }

        g.dispose();
    }

    //--> Action during play
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        //Key pressed and play == true
        if(play) {
            //Detecting intersection with puddle (if true then change direction)
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYdir = -ballYdir;
            }

            //Loop to set/draw bricks
            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 10;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            //To move ball in direction
            ballposX += ballXdir;
            ballposY += ballYdir;

            //If ball at wall x
            if(ballposX < 0) {
                ballXdir = -ballXdir;
            }
            //If ball at wall y
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }
            //If ball at bottom
            if(ballposX > 665) {
                ballXdir = -ballXdir;
            }
        }

        repaint(); //-> To redraw all
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Movement right
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //For right end
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        //Movement left
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            //For left end
            if(playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        //If enter pressed (only possible by play == false statement)
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = false;
                playerX = 310;
                ballposX = playerX + 40;
                ballposY = 530;
                ballXdir = -4;
                ballYdir = -5;
                score = 0;
                totalBricks = 40;
                map = new MapGenerator(5, 8);

                repaint();
            }
        }
    }

    //--> Move paddle right
    public void moveRight() {
        play = true;
        playerX += 25;
    }
    //--> Move paddle left
    public void moveLeft() {
        play = true;
        playerX -= 25;
    }
}
