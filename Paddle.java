/*
 * Pong Version 0.1 DEV
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author Pie.ink
 */
public class Paddle extends Rectangle {
    
    private boolean up = false, down = false;
    private boolean movingToPosition = false;
    private int locationY, locationX;
    private int sY;
    private double rot = 0;
    private int speed = 3;
    
    public Paddle(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        sY = y;
    }
    
    public void tick() {
        if(movingToPosition) {
            if(locationY < y) {
                down = false;
                up = true;
            }
            else if(locationY > y + height) {
                up = false;
                down = true;
            }
            else {
                movingToPosition = false;
                //up = false;
                //down = false;
            }
        }
        
        if(y <= 0) {
            up = false;
        }
        if(y + height >= Game.HEIGHT) {
            down = false;
        }
        if(up) {
            y -= speed;
        }
        if(down) {
            y += speed;
        }
    }
    public void setSpeed(int s) {
        speed = s;
    }
    public int getLocY() {
        return y;
    }
    /*public boolean getMv() {
        if()
    }*/
    public void reset() {
        y = sY;
    }
    /*private void moveToStart(int s) {
        if(y < sY - 5) {
            down = true;
        }
        else {
            down = false;
        }
        if(y > sY + 5) {
            up = true;
        }
        else {
            up = false;
        }
    }*/
    public void moveToPosition(int locationY, int locationX, Ball ball) {
        if(!movingToPosition) {
            this.locationY = locationY;
            this.locationX = locationX;
            rot = ball.getRot();
            movingToPosition = true;
        }
        
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
    public void keyPressed(int k, Paddle player) {
        if(Game.mode == 1 || Game.mode == 2) {
            if(k == KeyEvent.VK_W && player.x == 10) {
                up = true;
            }
            if(k == KeyEvent.VK_S && player.x == 10) {
                down = true;
            }
        }
        if(Game.mode == 2) {
            if(k == KeyEvent.VK_UP && player.x == Game.WIDTH - 30) {
                up = true;
            }
            if(k == KeyEvent.VK_DOWN && player.x == Game.WIDTH - 30) {
                down = true;
            }
        }
        
    }
    public void keyReleased(int k, Paddle player) {
        if(Game.mode == 1 || Game.mode == 2) {
            if(k == KeyEvent.VK_W && player.x == 10) {
                up = false;
            }
            if(k == KeyEvent.VK_S && player.x == 10) {
                down = false;
            }
        }
        if(Game.mode == 2) {
            if(k == KeyEvent.VK_UP && player.x == Game.WIDTH - 30) {
                up = false;
            }
            if(k == KeyEvent.VK_DOWN && player.x == Game.WIDTH - 30) {
                down = false;
            }
        }
        
    }
}
