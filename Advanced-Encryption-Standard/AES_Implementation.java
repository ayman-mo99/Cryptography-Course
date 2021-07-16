package advanced.encryption.standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class AES_Implementation {

    String[][] sbox = new String[16][16];
    String[][] invSbox = new String[16][16];
    int[][] MixColumns = {
        {2, 3, 1, 1},
        {1, 2, 3, 1},
        {1, 1, 2, 3},
        {3, 1, 1, 2}
    };

    String[][] inversMixColumns = {
        {"0E", "0B", "0D", "09"},
        {"09", "0E", "0B", "0D"},
        {"0D", "09", "0E", "0B"},
        {"0B", "0D", "09", "0E"}
    };

    String[] RC = {"01000000", "02000000", "04000000", "08000000", "10000000", "20000000", "40000000", "80000000", "1b000000", "36000000"};
    String key[][][] = new String[11][4][4];
    String words[] = new String[44];

    AES_Implementation() throws FileNotFoundException {
        readSbox();
    }

    // ********** Helper functions **************
    public String hexadecimalToBinary(String text) {
        String newtext = "";
        String temp = "";
        for (int i = 0; i < text.length(); i++) {
            temp = new BigInteger(text.charAt(i) + "", 16).toString(2);
            if (temp.length() == 1) {
                newtext = newtext + "000" + temp;
            } else if (temp.length() == 2) {
                newtext = newtext + "00" + temp;
            } else if (temp.length() == 3) {
                newtext = newtext + "0" + temp;
            } else {
                newtext = newtext + temp;
            }
        }
        return newtext;
    }

    public String binaryToHexadecimal(String text) {
        String newtext = "";
        String temp = "";
        for (int i = 0; i < text.length(); i = i + 4) {
            temp = new BigInteger(text.substring(i, i + 4), 2).toString(16);
            newtext = newtext + temp;
        }
        return newtext;
    }

    public String XOR(String s1, String s2) {
        String result = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                result = result + '0';
            } else {
                result = result + '1';
            }
        }
        return result;
    }

    public String[][] fillState(String s) {
        String result[][] = new String[4][4];
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[j][i] = s.substring(counter, counter + 2);
                counter += 2;
            }
        }
        return result;
    }

    public String extractState(String[][] x) {
        String result = "";
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result = result + x[j][i];
                counter += 2;
            }
        }
        return result;
    }

    public void readSbox() throws FileNotFoundException {
        // Two files containing the subbytes/invsubbytes table
        Scanner sc = new Scanner(new File("Sbox.txt"));
        for (int i = 0; i < sbox.length; i++) {
            for (int j = 0; j < 16; j++) {
                sbox[i][j] = sc.next();
            }
        }
        sc.close();
        sc = new Scanner(new File("invSbox.txt"));
        for (int i = 0; i < invSbox.length; i++) {
            for (int j = 0; j < 16; j++) {
                invSbox[i][j] = sc.next();
            }
        }
        sc.close();
    }

    public void printMatrix(String state[][]) {
        System.out.println();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ******  Common function in Encryption and Decryption   *****
    public String[][] addRoundKey(String state[][], String key[][]) {
        String result[][] = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = binaryToHexadecimal(XOR(hexadecimalToBinary(state[i][j]), hexadecimalToBinary(key[i][j])));
            }
        }
        return result;
    }

    // ************ Encryption *****************
    public String Encryption(String plaintext, String k) throws FileNotFoundException {
        String cipertext;
        keyExpansion(k);
        String[][] state = fillState(plaintext);

        state = addRoundKey(state, key[0]);
        for (int i = 1; i < 10; i++) {
            state = substituteBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, key[i]);
        }
        state = substituteBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, key[10]);

        printMatrix(state);
        cipertext = extractState(state);
        return cipertext;
    }

    public String[][] shiftRows(String[][] x) {
        String[][] result = new String[4][4];

        for (int i = 0; i < 4; i++) {
            result[i][0] = x[i][(0 + i) % 4];
            result[i][1] = x[i][(1 + i) % 4];
            result[i][2] = x[i][(2 + i) % 4];
            result[i][3] = x[i][(3 + i) % 4];
        }

        return result;
    }

    public String[][] substituteBytes(String[][] x) {
        String[][] result = new String[4][4];
        char temp1 = '0';
        char temp2 = '0';
        int n1 = 0;
        int n2 = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                temp1 = x[i][j].charAt(0);
                temp2 = x[i][j].charAt(1);

                if (Character.isDigit(temp1)) {
                    n1 = Character.getNumericValue(temp1);
                } else {
                    n1 = (int) temp1 - 97 + 10;
                }

                if (Character.isDigit(temp2)) {
                    n2 = Character.getNumericValue(temp2);
                } else {
                    n2 = (int) temp2 - 97 + 10;
                }
                result[i][j] = sbox[n1][n2];

            }
        }
        return result;
    }

    public String[][] mixColumns(String[][] x) {
        String[][] result = new String[4][4];
        int[] tempRow = new int[4];
        String[] tempCol = new String[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    tempRow[k] = MixColumns[i][k];
                    tempCol[k] = x[k][j];
                }
                result[i][j] = binaryToHexadecimal(mixColumns2(tempRow, tempCol));
            }
        }
        return result;
    }

    public String mixColumns2(int[] n, String[] s) {
        String s0 = gfMultiplication(hexadecimalToBinary(s[0]), n[0]);
        String s1 = gfMultiplication(hexadecimalToBinary(s[1]), n[1]);
        String s2 = gfMultiplication(hexadecimalToBinary(s[2]), n[2]);
        String s3 = gfMultiplication(hexadecimalToBinary(s[3]), n[3]);
        return XOR(XOR(XOR(s0, s1), s2), s3);
    }

    public String gfMultiplication(String s, int n) {
        if (n == 1) {
            return s;
        }
        if (n == 2) {
            if (s.charAt(0) == '0') {
                return s.substring(1) + '0';
            } else {
                return XOR((s.substring(1) + '0'), "00011011");
            }
        }
        if (n == 3) {
            String temp1 = s;
            String temp2 = "";
            if (s.charAt(0) == '0') {
                temp2 = s.substring(1) + '0';
            } else {
                temp2 = XOR((s.substring(1) + '0'), "00011011");
            }
            return XOR(temp1, temp2);
        }

        return s;
    }

    // **************** Decryption ******************
    public String Decryption(String ciphertext, String k) throws FileNotFoundException {
        String plaintext;
        keyExpansion(k);
        String[][] state = fillState(ciphertext);

        state = addRoundKey(state, key[10]);
        for (int i = 9; i > 0; i--) {
            state = inversShiftRows(state);
            state = inversSubstituteBytes(state);
            state = addRoundKey(state, key[i]);
            state = inversMixColumns(state);

        }
        state = inversShiftRows(state);
        state = inversSubstituteBytes(state);
        state = addRoundKey(state, key[0]);

        printMatrix(state);
        plaintext = extractState(state);
        return plaintext;
    }

    public String[][] inversShiftRows(String[][] x) {
        String[][] result = new String[4][4];

        for (int i = 0; i < 4; i++) {
            result[i][0] = x[i][(0 + 4 - i) % 4];
            result[i][1] = x[i][(1 + 4 - i) % 4];
            result[i][2] = x[i][(2 + 4 - i) % 4];
            result[i][3] = x[i][(3 + 4 - i) % 4];
        }

        return result;
    }

    public String[][] inversSubstituteBytes(String[][] x) {
        String[][] result = new String[4][4];
        char temp1 = '0';
        char temp2 = '0';
        int n1 = 0;
        int n2 = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                temp1 = x[i][j].charAt(0);
                temp2 = x[i][j].charAt(1);

                if (Character.isDigit(temp1)) {
                    n1 = Character.getNumericValue(temp1);
                } else {
                    n1 = (int) temp1 - 97 + 10;
                }

                if (Character.isDigit(temp2)) {
                    n2 = Character.getNumericValue(temp2);
                } else {
                    n2 = (int) temp2 - 97 + 10;
                }
                result[i][j] = invSbox[n1][n2];

            }
        }
        return result;
    }

    // Uncompleted function
    public String[][] inversMixColumns(String[][] x) {
        String[][] result = new String[4][4];

        return result;
    }

    // *******  keyExpansion *******
    public void keyExpansion(String key) {
        for (int i = 0; i < 4; i++) {
            words[i] = key.substring((i * 8), (i * 8) + 8);
        }
        String temp;
        for (int i = 4; i < 44; i++) {
            temp = words[i - 1];
            if (i % 4 == 0) {
                temp = binaryToHexadecimal(XOR(hexadecimalToBinary(SubWord(temp.substring(2) + temp.substring(0, 2))), hexadecimalToBinary(RC[(i / 4) - 1])));
            }
            words[i] = binaryToHexadecimal(XOR(hexadecimalToBinary(words[i - 4]), hexadecimalToBinary(temp)));
        }
        fillKey();
    }

    public String SubWord(String s) {
        String result = "";
        char temp1 = '0';
        char temp2 = '0';
        int n1 = 0;
        int n2 = 0;
        for (int i = 0; i < 8; i = i + 2) {
            temp1 = s.charAt(i);
            temp2 = s.charAt(i + 1);
            if (Character.isDigit(temp1)) {
                n1 = Character.getNumericValue(temp1);
            } else {
                n1 = (int) temp1 - 97 + 10;
            }
            if (Character.isDigit(temp2)) {
                n2 = Character.getNumericValue(temp2);
            } else {
                n2 = (int) temp2 - 97 + 10;
            }
            result = result + sbox[n1][n2];
        }

        return result;
    }

    public void fillKey() {
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    key[k][j][i] = words[(k * 4) + i].substring((j * 2), (j * 2) + 2);
                }
            }
        }
    }

}
