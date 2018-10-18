package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;




class ButtonListener implements  ActionListener {
  public ButtonListener() {

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Button1")) {
        Main.kapcs = false; 
    }

    if (e.getActionCommand().equals("Button2")) {
        Main.kapcs = true;
    }
  }

}
public class Main extends JPanel {
    public static final int HEIGHT = 250;
    public static final int WIDTH  = 500;
    public static boolean kapcs = false;
        public Main() {

 
  }

        
        
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    ScTable mari = new ScTable();
    Pong    game = new Pong();

    frame.getContentPane().add( game);

    mari.setPreferredSize(new Dimension(80, HEIGHT));
    mari.setBackground(Color.YELLOW);
    game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    game.setBackground(Color.ORANGE);
    frame.getContentPane().setLayout(new FlowLayout());
    frame.getContentPane().add(game);
   frame.toFront();
   frame.requestFocus();

    frame.getContentPane().add(mari);
   
   
   
   
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.pack(); 
   frame.setVisible(true);

  }


}




class Pong extends JPanel implements  KeyListener, ActionListener{
    
    public static boolean kpd = false, kpdAI = false;
    private int maincou = 0;
    final int ballSize = 30, INSET = 15, PADyDIM = 40;
    final double coacc = 0.005;
    private Timer t = new Timer(1, this);
    private double pad1yP = 100,  pad1acc = 0.01;
    private double pad2yP = 100,  pad2acc = 0.01;
    private double xP = Main.WIDTH/2, yP = ballSize+ 10;
    private double xD = 0.2; 
    private double acc= coacc;
    private double xPad1 = INSET, xPad2 = 500 - INSET*2;
    
    
    public Pong (){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

              
        t.start();
    } 

    
    
    	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

		if (Main.kapcs){
                    g2d.setColor(Color.green);
                }
                else g2d.setColor(Color.blue);
                Ellipse2D ball = new Ellipse2D.Double((int)xP,(int)yP, ballSize, ballSize);
		g2d.fill(ball);
	Rectangle2D pad1 = new Rectangle(  (int) xPad1, (int)pad1yP, INSET,PADyDIM );
	g2d.fill(pad1);
	Rectangle2D pad2 = new Rectangle(  (int) xPad2, (int)pad2yP, INSET,PADyDIM );
	g2d.fill(pad2);
        }
        
        private void ballReset (boolean scored){

            int temp = 1;
             if ( xD>0 ) temp = -temp;
             xD = temp*(0.2+Math.random()*0.3);
             if ( scored ){ xP = 250;}
             else xP += temp*30;
        }
        
            @Override
	public void actionPerformed(ActionEvent e) { 

            

         maincou ++;
         pad1yP += pad1acc;
         if ( pad1yP< 40 )  pad1yP = 40;
         if ( pad1yP > 200 ) pad1yP = 200 ;
         if  ( pad1acc> -0.8 && kpd ) pad1acc -= 0.04;
         if ( !kpd && pad1acc<0.4 ) pad1acc += 0.01;

         pad2yP += pad2acc;
         if ( pad2yP< 40 )  pad2yP = 40;
         if ( pad2yP > 200 ) pad2yP = 200 ;
         if  ( pad2acc> -0.8 && kpdAI ) pad2acc -= 0.04;
         if ( !kpdAI && pad2acc<0.4 ) pad2acc += 0.01;
         
         Double ballmed = yP + ballSize/2;
         if (xP>10 && xP<INSET*2 && ballmed>pad1yP && ballmed<PADyDIM+pad1yP ){
            ballReset (false);
         }
         if (xP>468-ballSize && xP<485-ballSize && ballmed>pad2yP && ballmed<PADyDIM+pad2yP ){
             xD = -(0.2+Math.random()*0.3);  
            ballReset (false);
         }

         if ( maincou%30 ==0 && xD>0 && xP>270 ){
             if ( ballmed<pad2yP){ kpdAI = true;}
             else kpdAI = false;
         }
         
         if ( xP<0 || xP>Main.WIDTH - ballSize )  { 
             ballReset (true);
         }


         xP +=xD;
         if ( yP<ballSize ) acc = coacc;
         if ( yP>Main.HEIGHT - ballSize )  acc = -coacc;        
         yP += acc*yP;
         repaint();
        }
  	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_LEFT:
			Pong.kpd = true;
			break;

		case KeyEvent.VK_SPACE:
			Pong.kpd = true;
			break;
                }
        }        
	public void keyReleased(KeyEvent e) {
        Pong.kpd = false;
        }
        

}

class ScTable extends JPanel{
    
    
    public ScTable(){
    JButton btn1 = new JButton("Button1");
    JButton btn2 = new JButton("Button2");

   //btn1.addActionListener(new ButtonListener());
   // btn2.addActionListener(new ButtonListener());
      Component add;
      //btn1.setText("Selected");
      add = add(btn1);
      add(btn2);         
    }
    public void setScore (){
      btn1.setText("Selected");        
    }
    
    
}





