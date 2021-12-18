import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class Gameplay extends JPanel implements KeyListener, ActionListener {

    Random rand = new Random();
    private boolean play = false;
    private int score = 0;
    private int totalbricks = rand.nextInt(4,15)*rand.nextInt(4,15);
    private Timer Timer;
    private int delay = 8;
    private int playerX = 620;
    private int ballposX = 660;
    private int ballposY = 731;
    private int ballXdir = -1;
    private int ballYdir = 3;
    private MapGenerator map;

    

    public Gameplay() {
        map = new MapGenerator(rand.nextInt(4,15),rand.nextInt(4,15));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }

    public void paint(Graphics g) {
        int R = (int)(Math.random()*256);
        int G = (int)(Math.random()*256);
        int B= (int)(Math.random()*256);
    
         
        Color randomColor1 = new Color(R,G,B);
        g.setColor(Color.black);
        g.fillRect(1, 1, 1480, 800);

        map.draw((Graphics2D) g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 800);
        g.fillRect(0, 0, 1480, 3);
        g.fillRect(1480, 0, 3, 800);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score : " + score, 1250, 30);

        g.setColor(Color.yellow);
        g.fillRect(playerX, 750, 100, 8);

        //ball
        g.setColor(randomColor1);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (ballposY > 753) 
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.black);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("    Game Over Score: " + score, 600, 300);

            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("   Press Enter to Restart", 600, 340);
        }
        if(totalbricks == 0)
        {
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.black);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString(" You Won : "+score,250,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 250, 340);


        }

        g.dispose();


    }
    public void timeDelay(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 750, 100, 100))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score += 5;
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }


                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 1400) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

       }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 1350) {
                playerX = 1350;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 660;
                ballposY = 731;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 620;
                // totalbricks = 10;
                
                map = new MapGenerator(rand.nextInt(15),rand.nextInt(15));

                repaint();
            }
        }


        }

        public void moveRight ()
        {
            play = true;
            playerX += 20;
        }
        public void moveLeft ()
        {
            play = true;
            playerX -= 20;
        }

    }