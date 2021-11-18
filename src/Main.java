import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import static javax.swing.border.BevelBorder.RAISED;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author oGronman
 */
public class Main extends JFrame {

    private static HashMap<String, Integer> flags = new HashMap<>();
    private static HashMap<Integer, String[]> source = new HashMap<>();
    private static HashMap<String, Integer> instructions = new HashMap<>() {
        {
            put("add", 3);
            put("sub", 3);
            put("put", 3);
            put("skip", 3);
            put("ju", 2);
            put("call", 3);
        }
    };

    private static ArrayList<String> registers = new ArrayList<>() {
        {
            add("r1");
            add("r2");
            add("r3");
            add("%0");
            add("%1");
        }
    };

    private static HashMap<String, Integer> regInstructions = new HashMap<>() {
        {
            put("add", 3);
            put("sub", 3);
            put("put", 3);
            put("skip", 4);
        }
    };

    private static String outPath;
    private static String filePath;
    private static ArrayList<Byte> compiledCode;
    private int fontSize = 25;
    private JTextPane textPane = new JTextPane();
    private JScrollPane scrollPane;
    private TextLineNumber tln;
    private Font bigFont;
    JPanel panel;

    public Main() {

        super("Compiler for the \"Not Enough Java\" language");

        int[] command = {0, 0};
        this.setBackground(new Color(12, 11, 21));
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(900, 500));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(16, 4, 27));
        bigFont = new Font("Serif", Font.PLAIN, fontSize);

        textPane.setFont(bigFont);
        textPane.setBackground(new Color(16, 4, 27));
        textPane.setForeground(new Color(63 + 50, 15 + 50, 41 + 50));

        scrollPane = new JScrollPane(textPane);
        scrollPane.setFont(bigFont);
        scrollPane.setForeground(new Color(100, 76, 38));
        scrollPane.setBackground(new Color(16, 4, 27));
        scrollPane.setBorder(new BevelBorder(RAISED));
        scrollPane.getVerticalScrollBar().setBackground(new Color(23, 9, 37));
        scrollPane.getHorizontalScrollBar().setBackground(new Color(23, 9, 37));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(63, 15, 41);
            }
        });

        tln = new TextLineNumber(textPane);
        tln.setBackground(new Color(16, 4, 27));
        tln.setForeground(new Color(100, 76, 38));
        tln.setFont(bigFont);
        scrollPane.setRowHeaderView(tln);

        JButton compileB = new JButton("Compile");
        compileB.setFont(bigFont);
        compileB.setForeground(new Color(63 + 50, 15 + 50, 41 + 50));
        compileB.setBackground(new Color(23, 9, 37));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 3;
        gbc.gridheight = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(scrollPane, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        gbc.weighty = 1;
        panel.add(compileB, gbc);

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {        //Ner = 1, upp = -1
                if (command[0] == 17) {
                    if (e.getWheelRotation() == 1) {
                        if (fontSize >= 5) {
                            fontSize = fontSize - 1 * e.getScrollAmount();
                            bigFont = new Font("Serif", Font.PLAIN, fontSize);
                            tln.setFont(bigFont);
                            textPane.setFont(bigFont);
                            panel.validate();
                        }
                    } else {
                        if (fontSize <= 75) {
                            fontSize = fontSize + 1 * e.getScrollAmount();
                            bigFont = new Font("Serif", Font.PLAIN, fontSize);
                            tln.setFont(bigFont);
                            textPane.setFont(bigFont);
                            panel.validate();
                        }
                    }
                    command[0] = 0;
                }
            }
        });

        textPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //updateText(textPane);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (command[0] == 0) {
                    command[0] = e.getKeyCode();
                } else if (command[0] != 0 && command[1] == 0) {
                    command[1] = e.getKeyCode();
                }
                if (command[0] != 0 && command[1] != 0) {
                    if (command[0] == 17) {
                        if (command[1] == 83) {
                            try {
                                writeToFile(textPane);
                                System.out.println("Saved...");
                            } catch (IOException ex) {
                                System.out.println("Something wrong with source");
                            }
                        }
                    }
                    command[0] = 0;
                    command[1] = 0;
                }
                if (e.getKeyCode() == 32 || e.getKeyCode() == 10) {  //
                    updateText(textPane);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        compileB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeToFile(textPane);
                    preReadFile();
                    compileFile();
                    makeExecutable();
                } catch (FileNotFoundException ex) {
                    System.out.println("Could not find source file");
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        add(panel);
        pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void updateText(JTextPane text) {
        String content = text.getText();
        String lines[] = content.split("\\r?\\n");
        text.setText("");
        for (int i = 0; i < lines.length; i++) {
            String[] com = lines[i].split("\\s+");
            if (!com[0].isBlank() || !com[0].isEmpty()) {
                for (int j = 0; j < com.length; j++) {
                    if (j == com.length - 1) {
                        if (instructions.containsKey(com[j])) {
                            appendToPane(text, com[j], Color.RED);
                            appendToPane(text, "", new Color(63 + 50, 15 + 50, 41 + 50));
                        } else if (registers.contains(com[j])) {
                            appendToPane(text, com[j], new Color(78 + 50, 159 + 50, 61 + 50));
                            appendToPane(text, "", new Color(63 + 50, 15 + 50, 41 + 50));
                        } else if (com[j].contains("<")) {
                            appendToPane(text, com[j], new Color(255, 76, 41));
                            appendToPane(text, "", new Color(63 + 50, 15 + 50, 41 + 50));
                        } else {
                            appendToPane(text, com[j] + "", new Color(63 + 50, 15 + 50, 41 + 50));
                        }
                        //appendToPane(text, "\n", Color.RED);
                    } else if (instructions.containsKey(com[j])) {
                        appendToPane(text, com[j] + " ", new Color(149 + 50, 1 + 50, 1 + 50));
                        appendToPane(text, "", new Color(63 + 50, 15 + 50, 41 + 50));
                    } else if (registers.contains(com[j])) {
                        appendToPane(text, com[j] + " ", new Color(30 + 50, 81 + 50, 40 + 50));
                        appendToPane(text, "", new Color(63 + 50, 15 + 50, 41 + 50));
                    } else if (com[j].contains("<")) {
                        appendToPane(text, com[j] + " ", new Color(255, 76, 41));
                        appendToPane(text, "", new Color(63 + 50, 15 + 50, 41 + 50));
                    } else {
                        appendToPane(text, com[j] + " ", new Color(63 + 50, 15 + 50, 41 + 50));
                    }
                }
                if (i < lines.length - 1) {
                    appendToPane(text, "\n", new Color(63 + 50, 15 + 50, 41 + 50));
                }
            }
        }
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Serif");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public void writeToFile(JTextPane text) throws IOException {
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(filePath)));
            text.write(fileOut);
            fileOut.close();
        } catch (java.lang.NullPointerException e) {
            System.out.println("No source");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            if (args[0].substring(args[0].lastIndexOf(".") + 1).equals("nej")) {
                filePath = args[0];
                outPath = args[0].substring(0, args[0].lastIndexOf("/"));
                outPath += "out.nexe";
                System.out.println("file path is: " + filePath);
                System.out.println("output file will be generated at: " + outPath);
                preReadFile();
                compileFile();
                makeExecutable();
            } else {
                filePath = args[0];
                filePath += "in.nej";
                outPath = args[0];
                outPath += "out.nexe";
                System.out.println("file path is: " + filePath);
                System.out.println("output file will be generated at: " + outPath);
                Main m = new Main();
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide the directory to valid source file or a valid folder...");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void preReadFile() throws FileNotFoundException {
        //filePath = "G:/My Drive/Programmering/Compiler/src/test2.noel";
        //filePath = "src/test2.noel";
        Scanner preReader = new Scanner(new File(filePath));

        String temp = "";
        if (preReader.hasNextLine()) {
            try {
                do {
                    temp = preReader.nextLine();
                } while (!temp.equals("<Start")); // && preReader.hasNextLine()
            } catch (java.util.NoSuchElementException e) {
                System.out.println("No <Start flag");
                System.out.println("Stopping the compile");
                System.exit(0);
            }
        } else {
            System.out.println("Only on line in source ;)");
            return;
        }
        int counter = 1;

        flags.put(temp.substring(1), counter);
        if (preReader.hasNextLine()) {
            do {

                temp = preReader.nextLine();

                if (!temp.isBlank()) {
                    if (temp.startsWith("<")) {
                        flags.put(temp.substring(1).toLowerCase(), counter);
                    } else {
                        try {
                            String[] elements = temp.split("\\s+");
                            if (instructions.containsKey(elements[0])) {
                                String[] compCode = new String[instructions.get(elements[0])];
                                for (int i = 0; i < elements.length; i++) {
                                    if (i >= compCode.length) {
                                        break;
                                    }
                                    compCode[i] = elements[i].toLowerCase();
                                }
                                if (compCode[0].equals("ju")) {
                                    if (compCode[1].contains("<")) {
                                        if (flags.containsKey(compCode[1].substring(1))) {
                                            compCode[1] = flags.get(compCode[1].substring(1)) + "";
                                        } else {
                                            compCode[1] = "0";
                                        }
                                    }
                                }

                                if (compCode.length != elements.length) {
                                    for (int i = compCode.length - 1; i > elements.length - 1; i--) {
                                        compCode[i] = "%0";
                                    }
                                }

                                if (compCode.length == 3 && !compCode[0].equals("call")) {
                                    String tempString = compCode[1];
                                    compCode[1] = compCode[2];
                                    compCode[2] = tempString;
                                }

                                if (regInstructions.containsKey(compCode[0])) {
                                    if (compCode[1].contains("r")) {
                                        compCode[1] = compCode[1].substring(0, 1) + "s" + compCode[1].substring(1);
                                    }
                                    if (compCode[2].contains("r")) {
                                        compCode[2] = compCode[2].substring(0, 1) + "t" + compCode[2].substring(1);
                                    }
                                } else {
                                    if (compCode[1].contains("r")) {
                                        compCode[1] = compCode[1].substring(0, 1) + "t" + compCode[1].substring(1);
                                    }
                                }

                                source.put(counter, compCode);
                                counter++;
                            } else {
                            }
                        } catch (java.lang.StringIndexOutOfBoundsException e) {
                            System.out.println("Therer is something wrong with the source code");
                        }
                    }
                }
            } while (!temp.equals("<End"));
        }

    }

    private static void compileFile() {

        compiledCode = new ArrayList<>();

        Compiler c = new Compiler();

        for (int i = 1; i < source.size() + 1; i++) {
            compiledCode.add(c.compile(source.get(i)));
        }
    }

    private static void makeExecutable() throws IOException {
        //outPath = "G:/My Drive/Programmering/Compiler/src/out.nexe";
        //outPath = "src/out.nexe";
        ByteArrayOutputStream writeByte = new ByteArrayOutputStream();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(outPath));
            // Write data to writeByte
            // Typ

            for (int i = 0; i < compiledCode.size(); i++) {
                writeByte.write(compiledCode.get(i));
            }
            writeByte.writeTo(out);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {

        } finally {
            out.close();
            writeByte.close();
        }

        Emulator e = new Emulator();

        e.readByteFile(outPath);
        e.executeProgram();

    }

}
