package playfair.cipher;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PlayfairCipher {

    public static void main(String[] args) {

        System.out.println(Encryption("ballon", "occurence"));
        System.out.println(Decryption("dbizgebv", "occurence"));

    }

    public static String Encryption(String plaintext, String Key) {
        String newkey = duplicates(Key);
        char playfairMatrix[][] = new char[5][5];
        int counter = 0;
        String alphabet = "abcdefghiklmnopqrstuvwxyz";

        //Here we fill in the playfair matrix
        for (int i = 0; i < 5; i++) {
            // We put the key letters first in the matrix then the reminaning letters of the alphabet
            for (int j = 0; j < 5; j++) {
                if (counter < newkey.length()) {
                    playfairMatrix[i][j] = newkey.charAt(counter);
                    counter++;
                    alphabet = alphabet.replace(playfairMatrix[i][j] + "", "");
                } else {
                    playfairMatrix[i][j] = alphabet.charAt(0);
                    alphabet = alphabet.replace(playfairMatrix[i][j] + "", "");
                }
            }

        }

        String Newtext = newPlaintext(plaintext);
        String ciphertext = "";
        for (int c = 0; c < Newtext.length(); c = c + 2) {
            //we hold 2 letters in each iteration and their index in the matrix
            char char1 = Newtext.charAt(c);
            int i1 = 0;
            int j1 = 0;

            char char2 = Newtext.charAt(c + 1);
            int i2 = 0;
            int j2 = 0;

            //Here we find the index in the matrix
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairMatrix[i][j] == char1) {
                        i1 = i;
                        j1 = j;
                    }
                    if (playfairMatrix[i][j] == char2) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }

            //According to the index we encrypt these 2 letters
            if (i1 == i2) {
                ciphertext = ciphertext + playfairMatrix[i1][(j1 + 1) % 5] + playfairMatrix[i1][(j2 + 1) % 5];
            } else if (j1 == j2) {
                ciphertext = ciphertext + playfairMatrix[(i1 + 1) % 5][j1] + playfairMatrix[(i2 + 1) % 5][j2];
            } else {
                ciphertext = ciphertext + playfairMatrix[i1][j2] + playfairMatrix[i2][j1];
            }
        }

        return ciphertext;
    }

    public static String Decryption(String ciphertext, String Key) {
        String newkey = duplicates(Key);
        char playfairMatrix[][] = new char[5][5];

        int counter = 0;
        String alphabet = "abcdefghiklmnopqrstuvwxyz";

        //Here we fill in the playfair matrix
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (counter < newkey.length()) {
                    playfairMatrix[i][j] = newkey.charAt(counter);
                    counter++;
                    alphabet = alphabet.replace(playfairMatrix[i][j] + "", "");
                } else {
                    playfairMatrix[i][j] = alphabet.charAt(0);
                    alphabet = alphabet.replace(playfairMatrix[i][j] + "", "");
                }
            }
        }

        String Newtext = ciphertext;
        String plaintext = "";
        for (int c = 0; c < Newtext.length(); c = c + 2) {
            //we hold 2 letters in each iteration and their index in the matrix
            char char1 = Newtext.charAt(c);
            int i1 = 0;
            int j1 = 0;

            char char2 = Newtext.charAt(c + 1);
            int i2 = 0;
            int j2 = 0;

            //Here we find the index in the matrix
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairMatrix[i][j] == char1) {
                        i1 = i;
                        j1 = j;
                    }
                    if (playfairMatrix[i][j] == char2) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }

            //According to the index we decrypt these 2 letters
            if (i1 == i2) {
                plaintext = plaintext + playfairMatrix[i1][(j1 - 1 + 5) % 5] + playfairMatrix[i1][(j2 - 1 + 5) % 5];
            } else if (j1 == j2) {
                plaintext = plaintext + playfairMatrix[(i1 - 1 + 5) % 5][j1] + playfairMatrix[(i2 - 1 + 5) % 5][j2];
            } else {
                plaintext = plaintext + playfairMatrix[i1][j2] + playfairMatrix[i2][j1];
            }
        }

        return plaintext;

    }

    //This function is created to achieve the following rule in the algorithm:
    //"Repeating plaintext letters that are in the same pair are separated with a filler letter"
    public static String newPlaintext(String plaintext) {
        //Plaintext should be of even length since we encrypte two letters at a time
        if (plaintext.length() % 2 != 0) {
            plaintext = plaintext + ' ';
        }

        for (int i = 0; i < plaintext.length() - 1; i = i + 2) {
            if (plaintext.charAt(i) == plaintext.charAt(i + 1)) {
                plaintext = plaintext.substring(0, i + 1) + 'x' + plaintext.substring(i + 1, plaintext.length());
                i = 0;
            }
        }
        plaintext = plaintext.replace(" ", "");

        //Plaintext should be of even length since we encrypte two letters at a time
        if (plaintext.length() % 2 != 0) {
            plaintext = plaintext + 'x';
        }

        return plaintext;
    }

    //This function from stackoverflow
    //Docs: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
    public static String duplicates(String key) {
        String noDuplicates = Arrays.asList(key.split(""))
                .stream()
                .distinct()
                .collect(Collectors.joining());
        return noDuplicates;
    }

}
