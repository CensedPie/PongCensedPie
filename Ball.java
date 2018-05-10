/*
 * Pong Version 0.1 DEV
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Pie.ink
 */
public class Ball extends Rectangle {
    
    private int speedX, speedY, sX, sY;
    private double rot;
    
    public Ball(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        sX = x;
        sY = y;
        speedX = 5;
        speedY = 3;
        rot = Math.random()*2;
        if(rot < 1.0) {
            rot = 3.5;
        }
        else {
            rot = 4.5;
        }
    }
    public void reset() {
        x = sX;
        y = sY;
        rot = Math.random()*2;
        if(rot < 1.0) {
            rot = 3.5;
        }
        else {
            rot = 4.5;
        }
    }
    public int getBY() {
        return y;
    }
    public int getBX() {
        return x;
    }
    public double getRot() {
        return rot;
    }
    public void setSpeed(int s) {
        speedX += s;
        speedY += s/2;
    }
    public void tick() throws InterruptedException {
        if(rot <= 1.0) {
            x -= speedX;
            y -= speedY;
        }
        if(rot > 1.0 && rot <= 2.0) {
            x += speedX;
            y -= speedY;
        }
        if(rot > 2.0 && rot <= 3.0) {
            y -= speedY; 
        }
        if(rot > 3.0 && rot <= 4.0) {
            x += speedX;
        }
        if(rot > 4.0 && rot <= 5.0) {
            x -= speedX;
        }
        if(rot > 5.0 && rot <= 6.0) {
            x -= speedX;
            y += speedY;
        }
        if(rot > 6.0 && rot <= 7.0) {
            y += speedY;
        }
        if(rot > 7.0 && rot <= 8.0) {
            x += speedX;
            y += speedY;
        }
        if(y + height >= Game.HEIGHT && rot > 7.0 && rot <= 8.0) {
            rot = 1.5;
        }
        else if(y + height >= Game.HEIGHT && rot > 5.0 && rot <= 6.0) {
            rot = 0.5;
        }
        if(y <= 0 && rot > 1.0 && rot <= 2.0) {
            rot = 7.5;
        }
        else if(y <= 0 && rot <= 1.0) {
            rot = 5.5;
        }
        if(x < 0) {
            Game.scoreP2();
            reset();
        }
        else if(x > Game.WIDTH) {
            Game.scoreP1();
            reset();
        }
    }
    public void collisionCheck(int paddleY, int AIPaddleY/*, boolean paddleMv, boolean AIPaddleMv*/) {
        if(x <= 30 && x >= 10 && y <= paddleY + 90 && y >= paddleY - 10) {
            if(rot > 4.0 && rot <= 5.0) {
                double rotTemp = Math.random() * 3;
                if(rotTemp <= 1.0) {
                    rot = 1.5;
                }
                if(rotTemp > 1.0 && rotTemp <= 2.0) {
                    rot = 3.5;
                }
                if(rotTemp > 2.0 && rotTemp <= 3.0) {
                    rot = 7.5;
                }
            }
            if(rot > 5.0 && rot <= 6.0) {
                rot = 7.5;
            }
            if(rot <= 1.0) {
                rot = 1.5;
            }
        }
        if(x >= Game.WIDTH - 50 && x <= Game.WIDTH - 30 && y <= AIPaddleY + 90 && y >= AIPaddleY - 10) {
            if(rot >= 3.0 && rot <= 4.0) {
               double rotTemp = Math.random() * 3;
                if(rotTemp <= 1.0) {
                    rot = 0.5;
                }
                if(rotTemp > 1.0 && rotTemp <= 2.0) {
                    rot = 4.5;
                }
                if(rotTemp > 2.0 && rotTemp <= 3.0) {
                    rot = 5.5;
                } 
            }
            if(rot > 7.0 && rot <= 8.0) {
                rot = 5.5;
            }
            if(rot > 1.0 && rot <= 2.0) {
                rot = 0.5;
            }
        }
    }
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }
    
}
