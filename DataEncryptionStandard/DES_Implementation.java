package dataencryptionstandard;

import java.math.BigInteger;

public class DES_Implementation {

    int[] pc1 = {57, 49, 41, 33, 25,
        17, 9, 1, 58, 50, 42, 34, 26,
        18, 10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36, 63,
        55, 47, 39, 31, 23, 15, 7, 62,
        54, 46, 38, 30, 22, 14, 6, 61,
        53, 45, 37, 29, 21, 13, 5, 28,
        20, 12, 4};
    int[] pc2 = {14, 17, 11, 24, 1, 5, 3,
        28, 15, 6, 21, 10, 23, 19, 12,
        4, 26, 8, 16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55, 30, 40,
        51, 45, 33, 48, 44, 49, 39, 56,
        34, 53, 46, 42, 50, 36, 29, 32};
    int[] shiftBits = {1, 1, 2, 2, 2, 2, 2, 2,
        1, 2, 2, 2, 2, 2, 2, 1};
    int[] ip = {58, 50, 42, 34, 26, 18,
        10, 2, 60, 52, 44, 36, 28, 20,
        12, 4, 62, 54, 46, 38,
        30, 22, 14, 6, 64, 56,
        48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17,
        9, 1, 59, 51, 43, 35, 27,
        19, 11, 3, 61, 53, 45,
        37, 29, 21, 13, 5, 63, 55,
        47, 39, 31, 23, 15, 7};
    int[] ep = {32, 1, 2, 3, 4, 5, 4,
        5, 6, 7, 8, 9, 8, 9, 10,
        11, 12, 13, 12, 13, 14, 15,
        16, 17, 16, 17, 18, 19, 20,
        21, 20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29, 28,
        29, 30, 31, 32, 1};
    int[][][] sbox = {
        {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
        {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
        {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
        {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
        {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
        {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
        {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
        {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};
    int[] pf = {16, 7, 20, 21, 29, 12, 28,
        17, 1, 15, 23, 26, 5, 18,
        31, 10, 2, 8, 24, 14, 32,
        27, 3, 9, 19, 13, 30, 6,
        22, 11, 4, 25};
    int[] inip = {40, 8, 48, 16, 56, 24, 64,
        32, 39, 7, 47, 15, 55,
        23, 63, 31, 38, 6, 46,
        14, 54, 22, 62, 30, 37,
        5, 45, 13, 53, 21, 61,
        29, 36, 4, 44, 12, 52,
        20, 60, 28, 35, 3, 43,
        11, 51, 19, 59, 27, 34,
        2, 42, 10, 50, 18, 58,
        26, 33, 1, 41, 9, 49,
        17, 57, 25};

    public DES_Implementation() {
    }
    
     public String Encryption(String plaintext, String key) {
        plaintext = hexadecimalToBinary(plaintext);
        key = hexadecimalToBinary(key);
        String keys[] = new16key(key);
        //InitialPermutation
        plaintext = anyPermutation(plaintext, ip);
        String left = plaintext.substring(0, 32);
        String right = plaintext.substring(32, plaintext.length());

        String leftOld = left;
        for (int i = 0; i < 16; i++) {
            leftOld = left;
            left = right;
            right = XOR(leftOld, F(right, keys[i]));
        }
        String ciphertext = anyPermutation((right + left), inip);

        return binaryToHexadecimal(ciphertext);
    }

    public String Decryption(String ciphertext, String key) {
        ciphertext = hexadecimalToBinary(ciphertext);
        key = hexadecimalToBinary(key);
        String keys[] = new16key(key);
        //InitialPermutation
        ciphertext = anyPermutation(ciphertext, ip);
        String left = ciphertext.substring(0, 32);
        String right = ciphertext.substring(32, ciphertext.length());

        String leftOld = left;
        for (int i = 15; i >= 0; i--) {
            leftOld = left;
            left = right;
            right = XOR(leftOld, F(right, keys[i]));
        }
        String plaintext = anyPermutation((right + left), inip);

        return binaryToHexadecimal(plaintext);
    }
    
    
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

    public String anyPermutation(String text, int[] arr) {
        String newtext = "";
        for (int i = 0; i < arr.length; i++) {
            newtext = newtext + text.charAt(arr[i] - 1);
        }
        return newtext;
    }

   
    public String sBoxes(String text) {
        String divided[] = new String[8];
        String result = "";
        //divide
        for (int i = 0; i < 8; i++) {
            divided[i] = text.substring((6 * i), (6 * i) + 6);
        }

        for (int i = 0; i < 8; i++) {
            String row = divided[i].charAt(0) + "" + divided[i].charAt(5);
            String col = divided[i].substring(1, 5) + "";

            int rownum = Integer.parseInt(row, 2);
            int colnum = Integer.parseInt(col, 2);
            int temp = sbox[i][rownum][colnum];

            divided[i] = Integer.toBinaryString(temp);
            if (divided[i].length() == 3) {
                divided[i] = '0' + divided[i];
            }
            if (divided[i].length() == 2) {
                divided[i] = "00" + divided[i];
            }
            if (divided[i].length() == 1) {
                divided[i] = "000" + divided[i];
            }
            result = result + divided[i];

        }

        String newtext = anyPermutation(result, pf);
        return newtext;
    }

    public String F(String r, String k) {
        //ExpansionPermutation
        r = anyPermutation(r, ep);
        String t = XOR(r, k);
        return sBoxes(t);
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

    public String[] new16key(String key) {
        //convert64to56
        key = anyPermutation(key, pc1);
        // divid the key 
        String l = key.substring(0, key.length() / 2);
        String r = key.substring(key.length() / 2, key.length());
        //craete Result keys
        String[] key16 = new String[16];
        // shift then combain
        for (int i = 0; i < 16; i++) {
            l = shift(l, shiftBits[i]);
            r = shift(r, shiftBits[i]);
            key16[i] = l + r;
        }
        // apply  PC2 for each key (1 ... 16)
        for (int i = 0; i < 16; i++) {
            key16[i] = anyPermutation(key16[i], pc2);
        }
        return key16;
    }

    public String shift(String key, int n) {
        String newkey = "";
        newkey = key.substring(n, key.length()) + key.substring(0, n);
        return newkey;
    }
}
