/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author rohit
 */
public class GamePanel extends JPanel implements ActionListener  {
   static final int SCREEN_WIDTH = 600 ;
   static final int SCREEN_HEIGHT = 600 ;
   static final int UNIT_SIZE = 25 ; // size of unit pixel S
   static final int GAME_UNIT =(SCREEN_WIDTH * SCREEN_HEIGHT)/ UNIT_SIZE;
   static final int DELAY = 75 ;
   final int x[] = new int[GAME_UNIT];
   final int y[] = new int[GAME_UNIT];
   int bodyParts = 6;
   int applesEaten ;
   int appleX;
   int appleY;
   char direction = 'R';
   boolean running = false;
   Timer timer;
   Random random;
   
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        
    }
    public void startGame(){
        newApple();
        running = true ;
        timer = new Timer(DELAY ,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running ){
    //        drawind pixel grid
//           for(int i = 0 ;i <= SCREEN_HEIGHT/UNIT_SIZE ; i++){
//               g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//               g.drawLine(0, i*UNIT_SIZE, i*SCREEN_WIDTH, i*UNIT_SIZE);
//           }
    //       apple 
           g.setColor(Color.red);
           g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

    //       snake 
            for(int i = 0 ; i < bodyParts; i++){
    //            HEAD
                if (i == 0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i],UNIT_SIZE, UNIT_SIZE);
                } // body
                else{
                    g.setColor(new Color(34,139,34));
                    g.fillRect(x[i], y[i],UNIT_SIZE, UNIT_SIZE);
                }
            }
            displayScore(g);
        }else{
            gameOver(g);
        }
    }
    public void displayScore(Graphics g){
        //      displaying  score  
         g.setColor(Color.red);
        g.setFont(new Font ("Ink Free", Font.BOLD, 40));
//        adds font to center
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2 , g.getFont().getSize());

    }
    public void newApple(){
        appleX =random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE))*UNIT_SIZE;
        appleY =random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE))*UNIT_SIZE; 
    }   
    public void move(){
        for(int i  = bodyParts; i > 0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1]; 
        }
        switch (direction){
            case'U':
                y[0] = y[0]-UNIT_SIZE;
                break ;
            case'D':
                y[0] = y[0]+UNIT_SIZE;
                break ;
            case'L':
                x[0] = x[0]-UNIT_SIZE;
                break ;
            case'R':
                x[0] = x[0]+UNIT_SIZE;
                break ;
        }
    }
    public void checkApple(){
        
        if((x[0] == appleX )&& (y[0]== appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
//        check collision o head and body 
        for(int i  = bodyParts ; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){ 
                running = false;
            }
        }
//        check head touch left border
        if(x[0] < 0){
            running = false;
        }
//        check head touch right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
//        check head touch TOP border
        if(y[0] < 0){
            running = false;
        }
//        check head touch bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
//        stop runnig 
            if(!running ){
                timer.stop();
            }
    }
    public void gameOver(Graphics g){
        displayScore(g);
//       gameOver Text 
        g.setColor(Color.red);
        g.setFont(new Font ("Ink Free", Font.BOLD, 75));
//        adds font to center
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!",(SCREEN_WIDTH - metrics.stringWidth("Game Over!"))/2 , SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       
        if( running){
            move();
            checkApple();
            checkCollisions();
        } 
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
//                    dont let turn 180 degree
                    if(direction != 'R')
                        direction= 'L';
                    break;
                 case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                        direction= 'R';
                    break;
                  case KeyEvent.VK_UP:
                    if(direction != 'D')
                        direction= 'U';
                    break;
                   case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                        direction= 'D';
                    break;
            }
        }
        
    }
    
}
