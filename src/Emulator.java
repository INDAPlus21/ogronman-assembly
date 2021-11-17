
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Scanner;

/**
 *
 * @author oGronman
 */
public class Emulator {

    private HashMap<Integer, String> source = new HashMap<>();
    private int[] registers;

    public Emulator() {
        registers = new int[5];
    }

    public void readByteFile(String inputPath) {
        try {

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(inputPath)));

            int counter = 1;
            while (in.available() != 0) {
                int temp = in.read();
                String tempS = Integer.toBinaryString(temp);
                if (tempS.length() != 8) {
                    String newS = "";
                    for (int i = tempS.length(); i < 8; i++) {
                        newS += "0";
                    }
                    newS += tempS;
                    tempS = newS;
                }
                /// Koden blir inte som den ska för att inledande nollor försvinner
                /// Lägg till nollor tills strängen blir 8 lång
                source.put(counter, tempS);
                counter++;
            }

        } catch (IOException ex) {
            Logger.getLogger(Emulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeProgram() {
        int currentLine = 1;
        registers[0] = 0;
        registers[4] = 1;
        while (currentLine < source.size() + 1) {
            String code = source.get(currentLine);
            String op = code.substring(0, 3);
            if (op.equals("000")) {
                int rs = Integer.parseInt(code.substring(3, 6), 2);
                int rt = Integer.parseInt(code.substring(6), 2);

                registers[rt] = registers[rt] + registers[rs];

            } else if (op.equals("001")) {
                int rs = Integer.parseInt(code.substring(3, 6), 2);
                int rt = Integer.parseInt(code.substring(6), 2);

                registers[rt] = registers[rt] - registers[rs];

            } else if (op.equals("010")) {
                int rs = Integer.parseInt(code.substring(3, 6), 2);
                int rt = Integer.parseInt(code.substring(6), 2);

                if (registers[rt] == registers[rs]) {
                    currentLine++;
                    //System.out.println("skipping");
                }

            } else if (op.equals("011")) {

                int rs = Integer.parseInt(code.substring(3, 6), 2);
                int rt = Integer.parseInt(code.substring(6), 2);

                registers[rt] = registers[rs];

            } else if (op.equals("100")) {
                int addr = Integer.parseInt(code.substring(3, 8), 2);
                //System.out.println("The adress is: " + addr);
                //System.out.println(currentLine);
                currentLine = addr;
                currentLine--;
                //System.out.println(currentLine);

            } else if (op.equals("101")) {

                int rs = Integer.parseInt(code.substring(3, 5), 2);
                int imm = Integer.parseInt(code.substring(5), 2);

            } else if (op.equals("110")) {

                int rs = Integer.parseInt(code.substring(3, 5), 2);
                int imm = Integer.parseInt(code.substring(5), 2);

            } else if (op.equals("111")) {
                int rs = Integer.parseInt(code.substring(3, 5), 2);
                int imm = Integer.parseInt(code.substring(5), 2);

                if (imm == 0) {
                    Scanner sc = new Scanner(System.in);
                    Boolean isTrue = true;
                    while (isTrue)
                    try {
                        registers[rs] = Integer.parseInt(sc.nextLine());
                        isTrue = false;
                    } catch (java.lang.NumberFormatException e) {

                    }
                    sc.close();
                } else if (imm == 1) {
                    System.out.println(registers[rs]);
                } else {
                    break;
                }
            }
            registers[0] = 0;
            registers[4] = 1;
            //System.out.println("Code is: " + code);
            //System.out.println("R1 = " + registers[1]);
            //System.out.println("R2 = " + registers[2]);
            //System.out.println("R3 = " + registers[3]);
            currentLine++;
        }

    }

}
