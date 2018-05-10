/*
 * Pong Version 0.1 DEV
 */
package pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 *
 * @author Pie.ink
 */
public class Game extends JPanel implements Runnable, KeyListener {
    
    public static final int WIDTH = 800, HEIGHT = 600;
    private Thread thread;
    private boolean isRunning = false;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    
    private Paddle paddle;
    private Paddle AIPaddle;
    private Ball ball;
    
    public static int mode, difficulty;
    private static int scoreP1, scoreP2, tempScoreP1, tempScoreP2;
    public static String scoreP1S, scoreP2S;
    public Font scoreFont = new Font("Monospaced Bold", 1, 50);
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong");
        JButton sp = new JButton("Singleplayer");
        sp.setBounds((WIDTH/2) - 75, (HEIGHT/2) - 60, 150, 50);
        frame.add(sp);
        JButton lmp = new JButton("Local Multiplayer");
        lmp.setBounds((WIDTH/2) - 75, (HEIGHT/2) + 10, 150, 50);
        frame.add(lmp);
        JButton easy = new JButton("Easy");
        easy.setBounds((WIDTH/2) - 75, (HEIGHT/2) - 95, 150, 50);
        easy.setVisible(false);
        frame.add(easy);
        JButton medium = new JButton("Medium");
        medium.setBounds((WIDTH/2) - 75, (HEIGHT/2) - 25, 150, 50);
        medium.setVisible(false);
        frame.add(medium);
        JButton hard = new JButton("Hard");
        hard.setBounds((WIDTH/2) - 75, (HEIGHT/2) + 45, 150, 50);
        hard.setVisible(false);
        frame.add(hard);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new Game(frame, sp, lmp, easy, medium, hard), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public Game(JFrame f, JButton s, JButton l, JButton ez, JButton md, JButton hd) {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        
        
        addKeyListener(this);
        
        paddle = new Paddle(10, HEIGHT / 2 - 50, 20, 100);
        AIPaddle = new Paddle(WIDTH - 30, HEIGHT / 2 - 50, 20, 100);
        ball = new Ball(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        scoreP1 = 0;
        scoreP2 = 0;
        scoreP1S = String.valueOf(scoreP1);
        scoreP2S = String.valueOf(scoreP2);
        tempScoreP1 = scoreP1;
        tempScoreP2 = scoreP2;
        menu(f, s, l, ez, md, hd);
    }
    public void menu(JFrame frame, JButton sp, JButton lmp, JButton ez, JButton md, JButton hd) {
        sp.setMnemonic(MouseEvent.BUTTON1);
        sp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lmp.setVisible(false);
                frame.remove(lmp);
                sp.setVisible(false);
                frame.remove(sp);
                mode = 1;
                diff(frame, ez, md, hd);
            }
        });
        lmp.setMnemonic(MouseEvent.BUTTON1);
        lmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(sp);
                frame.remove(lmp);
                mode = 2;
                start();
            }
        });
        repaint();
    }
    public void diff(JFrame frame, JButton easy, JButton medium, JButton hard) {
        easy.setVisible(true);
        medium.setVisible(true);
        hard.setVisible(true);
        easy.setMnemonic(MouseEvent.BUTTON1);
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(medium);
                frame.remove(hard);
                frame.remove(easy);
                difficulty = 1;
                paddle.setSpeed(3);
                AIPaddle.setSpeed(3);
                start();
            }
        });
        medium.setMnemonic(MouseEvent.BUTTON1);
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(easy);
                frame.remove(hard);
                frame.remove(medium);
                difficulty = 2;
                paddle.setSpeed(5);
                AIPaddle.setSpeed(5);
                ball.setSpeed(2);
                start();
            }
        });
        hard.setMnemonic(MouseEvent.BUTTON1);
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(medium);
                frame.remove(easy);
                frame.remove(hard);
                difficulty = 3;
                paddle.setSpeed(7);
                AIPaddle.setSpeed(7);
                ball.setSpeed(4);
                start();
            }
        });
    }
    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void run() {
        long start, elapsed, wait;
        
        while(isRunning) {
            start = System.nanoTime();
            
            try {
                tick();
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            repaint();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if(wait <= 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void tick() throws InterruptedException {
        //System.out.println(paddle.y);
        if(mode == 1) {
            AIPaddle.moveToPosition(ball.getBY(), ball.getBX(), ball);
        }
        AIPaddle.tick();
        ball.tick();
        paddle.tick();
        ball.collisionCheck(paddle.getLocY(), AIPaddle.getLocY());
        if(tempScoreP1 != scoreP1) {
            if(mode == 1) {
                AIPaddle.reset();
                if(difficulty == 1) {
                    paddle.reset();
                }
            }
            else if(mode == 2) {
                paddle.reset();
                AIPaddle.reset();
            }
            tempScoreP1 = scoreP1;
        }
        else if(tempScoreP2 != scoreP2) {
            if(mode == 1) {
                AIPaddle.reset();
                if(difficulty == 1) {
                    paddle.reset();
                }
            }
            else if(mode == 2) {
                paddle.reset();
                AIPaddle.reset();
            }
            tempScoreP2 = scoreP2;
        }
    }
    
    public static void scoreP1() throws InterruptedException {
        scoreP1++;
        scoreP1S = String.valueOf(scoreP1);
        Thread.sleep(100);
    }
    public static void scoreP2() throws InterruptedException {
        scoreP2++;
        scoreP2S = String.valueOf(scoreP2);
        Thread.sleep(100);
    }
    
    public void paintComponent(Graphics g) {
        if(g.getFont() != scoreFont) {
            g.setFont(scoreFont);
        }
        if(isRunning) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,WIDTH,HEIGHT);
            paddle.draw(g);
            AIPaddle.draw(g);
            ball.draw(g);
            g.drawString(scoreP1S, 350, 60);
            g.drawString(scoreP2S, 450, 60);
            g.drawString("|", 410, 35);
            g.drawString("|", 410, 146);
            g.drawString("|", 410, 257);
            g.drawString("|", 410, 368);
            g.drawString("|", 410, 479);
            //g.drawString("|", 410, 360);
            //g.drawString("|", 410, 425);
            //g.drawString("|", 410, 490);
            //g.drawString("|", 410, 555);
            g.drawString("|", 410, 590);
        }
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,WIDTH,HEIGHT);
            
        }
    }
    
    public void keyPressed(KeyEvent e) {
        paddle.keyPressed(e.getKeyCode(),paddle);
        AIPaddle.keyPressed(e.getKeyCode(),AIPaddle);
    }
    public void keyReleased(KeyEvent e) {
        paddle.keyReleased(e.getKeyCode(),paddle);
        AIPaddle.keyReleased(e.getKeyCode(),AIPaddle);
    }
    public void keyTyped(KeyEvent e) {
        
    }
    
}
