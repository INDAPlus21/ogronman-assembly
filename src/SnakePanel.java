

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author ozkar
 */
public class SnakePanel extends JPanel implements Runnable {

    private Player p;
    private Thread t;
    private String outpath;
    private ArrayList<Code> codeArray = new ArrayList<>();
    private ArrayList<String> fileCode = new ArrayList<>();
    private ArrayList<String> instructions = new ArrayList<>() {
        {
            add("add");
            add("sub");
            add("put");
            add("skip");
            add("ju");
            add("call");
            add("set");
            add("counter");
        }
    };
    private HashMap<Integer, String> intToString = new HashMap<>() {
        {
            put(0, "add");
            put(1, "sub");
            put(2, "put");
            put(3, "skip");
            put(4, "call");
            put(5, "counter");
            put(6, "ju");
            put(7, "set");
            put(8, "2");
            put(9, "3");
            put(10, "%0");
            put(11, "%1");
            put(12, "r1");
            put(13, "r2");
            put(14, "r3");
            put(15, "c1");
            put(15, "c2");

        }
    };

    public SnakePanel(Player p, String out) {
        this.p = p;
        codeArray.add(new Code(0));
        outpath = out;
        t = new Thread(this);
        t.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);

        for (int i = 0; i < p.getSize(); i++) {
            g.fillRect(p.getBody(i).x, p.getBody(i).y, 20, 20);
        }
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        g.drawString("Save the code to file with ctrl + P", 100, 100);
        g.drawString("As well as to be able to compile it", 100, 150);
        
        for (int i = 0; i < codeArray.size(); i++) {
            if (codeArray.get(i).getType() % 10 == 0) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("add", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("%0", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.red);
            } else if (codeArray.get(i).getType() % 10 == 1) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("sub", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("%1", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.blue);
            } else if (codeArray.get(i).getType() % 10 == 2) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("put", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("r1", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.black);
            } else if (codeArray.get(i).getType() % 10 == 3) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("skip", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("r2", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.green);
            } else if (codeArray.get(i).getType() % 10 == 4) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("call", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("r3", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.pink);
            } else if (codeArray.get(i).getType() % 10 == 5) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("counter", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("c1", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.magenta);
            } else if (codeArray.get(i).getType() % 10 == 6) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("ju", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("c2", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.orange);
            } else if (codeArray.get(i).getType() % 10 == 7) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("set", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("3", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.CYAN);
            } else if (codeArray.get(i).getType() % 10 == 8) {
                if (codeArray.get(i).getType() < 8) {
                    g.setColor(Color.black);
                    g.drawString("2", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("5", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.CYAN);
            } else if (codeArray.get(i).getType() % 10 == 9) {
                if (codeArray.get(i).getType() < 0) {
                    g.setColor(Color.black);
                    g.drawString("3", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                } else {
                    g.setColor(Color.black);
                    g.drawString("%0", codeArray.get(i).getP().x, codeArray.get(i).getP().y);
                }
                g.setColor(Color.CYAN);
            }
            g.fillRect(codeArray.get(i).getP().x, codeArray.get(i).getP().y, 20, 20);
        }

    }

    private void isCollision() {
        int length = codeArray.size();
        ArrayList<Integer> removedType = new ArrayList<>();
        try {
            for (int i = 0; i < length; i++) {
                if (p.getBody(0).x >= codeArray.get(i).getP().x && p.getBody(0).x < ((codeArray.get(i).getP().x) + 20)) {
                    if (p.getBody(0).y >= codeArray.get(i).getP().y && p.getBody(0).y < (codeArray.get(i).getP().y + 20)) {
                        removedType.add(codeArray.get(i).getType());
                        codeArray.remove(i);

                    }
                }
            }
        } catch (java.lang.IndexOutOfBoundsException e) {

        }

        for (int i = 0; i < removedType.size(); i++) {
            if (removedType.get(i) < 8) {
                codeArray.add(new Code(1));
                codeArray.add(new Code(1));
                codeArray.add(new Code(0));
            } else {
                codeArray.add(new Code(0));
                codeArray.add(new Code(0));
                codeArray.add(new Code(1));
            }
            p.addSize();
        }
        repaint();
        addToFile(removedType);
    }

    public void printToFile() throws IOException {

        BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(outpath)));
        String outString = "<Start\n";
        for (int i = 0; i < fileCode.size(); i++) {
            if(instructions.contains(fileCode.get(i)) && i > 1){
                outString += "\n";
            }
            outString += fileCode.get(i) + " ";
        }
        outString += "\n<End";
        fileOut.write(outString);
        fileOut.close();
    }

    private void addToFile(ArrayList<Integer> addFile) {
        for (int i = 0; i < addFile.size(); i++) {
            fileCode.add(intToString.get(addFile.get(i)));
        }

    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 20.0;
        double delta = 0;
        while (!Thread.interrupted()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                p.updateBody();
                isCollision();
                repaint();
                delta--;
            }
        }
    }

}
