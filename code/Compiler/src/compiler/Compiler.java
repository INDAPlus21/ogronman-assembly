/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.HashMap;

/**
 *
 * @author Win7
 */
public class Compiler {

    private HashMap<String, String> inst = new HashMap<>() {
        {
            put("add", "000");
            put("sub", "001");
            put("put", "011");
            put("skip", "010");
            put("ju", "100");
            put("", "101");
            put("", "110");
            put("call", "111");
            put("%0", "000");
            put("%1", "100");
            put("rs1", "001");
            put("rs2", "010");
            put("rs3", "011");
            put("rt1", "01");
            put("rt2", "10");
            put("rt3", "11");
        }
    };

    public byte compile(String[] src) {
        String compiled = "";
        for (int i = 0; i < src.length; i++) {
            if (inst.containsKey(src[i].toLowerCase())) {
                compiled += inst.get(src[i]);
            } else {
                try {
                    compiled += decToBin(src[i]);
                } catch (java.lang.NumberFormatException e) {

                }
            }
        }
        compiled = checkLength(compiled);

        int convert = Integer.parseInt(compiled, 2);
        byte returnByte = (byte) convert;
        return returnByte;
    }

    private String checkLength(String code) {
        if (code.substring(0, 3).equals("100") || code.substring(0, 3).equals("010")) {
            String temp = "";
            for (int i = code.length(); i < 8; i++) {
                temp += "0";
            }
            code = code.substring(0, 3) + temp + code.substring(3);

        } else if ((code.substring(0, 3).equals("111"))) {
            String temp = "";
            for (int i = code.length(); i < 8; i++) {
                temp += "0";
            }
            code = code.substring(0, 5) + temp + code.substring(5);
        } else if (code.length() < 8) {
            for (int i = code.length(); i < 8; i++) {
                code += "0";
            }
        } else if (code.length() > 8) {
            String newCode = "";
            for (int i = code.length(); i >= 8; i--) {
                newCode = code.substring(0, i);
            }
            code = newCode;
        }

        return code;
    }

    private String decToBin(String s) throws java.lang.NumberFormatException {
        int dec = Integer.parseInt(s);
        String bin = Integer.toBinaryString(dec);
        return bin;
    }

}
