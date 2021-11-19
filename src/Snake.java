

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author ozkar
 */
public class Snake extends JFrame {
    
    private Player p;
    private SnakePanel panel;
    private int[] command = {0, 0};

    public Snake(String outpath){
        
        p = new Player();

        panel = new SnakePanel(p, outpath);
        add(panel);
        panel.repaint();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 39){
                    p.setDirection(0, 1);
                    p.setDirection(1, 0);
                }else if(e.getKeyCode() == 40){
                    p.setDirection(1, 1);
                    p.setDirection(0, 0);
                }else if(e.getKeyCode() == 37){
                    p.setDirection(0, -1);
                    p.setDirection(1, 0);
                }else if (e.getKeyCode() == 38){
                    p.setDirection(1, -1);
                    p.setDirection(0, 0);
                }else if (e.getKeyCode() == 17){
                    command[0] = 1;
                } else if(e.getKeyCode() == 80){
                    command[1] = 1;
                }
                
                if(command[0] != 0 && command[1] != 0){
                    try {
                        panel.printToFile();
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    command[0] = 0;
                    command[0] = 1;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });

        this.setPreferredSize(new Dimension(1000, 1000));

        pack();
        setVisible(true);

    }


}
