

import java.util.HashMap;

/**
 *
 * @author oGronman
 */
public class Compiler {

    private HashMap<String, String> inst = new HashMap<>() {
        {
            put("add", "000");
            put("sub", "001");
            put("put", "011");
            put("skip", "010");
            put("ju", "100");
            put("set", "101");
            put("counter", "110");
            put("call", "111");
            put("%0", "000");
            put("%1", "100");
            put("rs1", "001");
            put("rs2", "010");
            put("rs3", "011");
            put("rt1", "01");
            put("rt2", "10");
            put("rt3", "11");
            put("c1", "101");
            put("c2", "111");
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

        if (compiled.length() != 8) {
            System.out.println(compiled);
        }

        int convert = Integer.parseInt(compiled, 2);
        byte returnByte = (byte) convert;
        return returnByte;
    }

    private String checkLength(String code) {
        if ((code.substring(0, 3).equals("100") || code.substring(0, 3).equals("010") && code.length() < 8)) {
            String temp = "";
            for (int i = code.length(); i < 8; i++) {
                temp += "0";
            }
            code = code.substring(0, 3) + temp + code.substring(3);

        } else if ((code.substring(0, 3).equals("111")) && code.length() != 8) {
            if (code.length() > 5) {
                String temp = "";
                for (int i = code.length(); i < 8; i++) {
                    temp += "0";
                }
                code = code.substring(0, 6) + temp + code.substring(6);
            } else {
                String temp = "";
                for (int i = code.length(); i < 8; i++) {
                    temp += "0";
                }
                code = code.substring(0, 3) + temp + code.substring(3);
                System.out.println(code);
            }

        } else if (code.length() < 8) {
            for (int i = code.length(); i < 8; i++) {
                code += "0";
            }
        }
        if (code.length() > 8) {
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
