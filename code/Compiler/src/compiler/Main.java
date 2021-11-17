/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Win7
 */
public class Main {

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // if (args[0].substring(args[0].lastIndexOf(".") + 1).equals("noel")) {
            // filePath = args[0];
            // outPath = args[1];
            // }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide two arguments...");
            System.out.println("input/source file and directory to output file");
        }

        try {
            preReadFile();
            compileFile();
            makeExecutable();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find source file");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void preReadFile() throws FileNotFoundException {
        //filePath = "G:/My Drive/Programmering/Compiler/src/test2.noel";
        filePath = "src/test.noel";
        Scanner preReader = new Scanner(new File(filePath));

        String temp = "";
        do {
            temp = preReader.nextLine();
        } while (!temp.equals("<Start"));

        int counter = 1;

        flags.put(temp.substring(1), counter);

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

    private static void compileFile() {

        compiledCode = new ArrayList<>();

        Compiler c = new Compiler();

        for (int i = 1; i < source.size() + 1; i++) {
            compiledCode.add(c.compile(source.get(i)));
        }
    }

    private static void makeExecutable() throws IOException {
        //outPath = "G:/My Drive/Programmering/Compiler/src/out.nexe";
        outPath = "src/out.nexe";
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
