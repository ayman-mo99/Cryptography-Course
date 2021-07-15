package hill.cipher;

import Jama.Matrix;

public class HillCipher {

    public static void main(String[] args) {
        double[][] values1 = {{17, 17, 5}, {21, 18, 21}, {2, 2, 19}};
        System.out.println(Encryption("paymoremoney", values1));
        System.out.println(Decryption("rrlmwbkaspdh", values1));

        double[][] values2 = {{3, 2}, {8, 5}};
        System.out.println(Encryption("hillcipher", values2));
        System.out.println(Decryption("hcrzssxnsp", values2));
    }

    public static String Encryption(String plaintext, double key[][]) {
        String Ciphertext = "";
        Matrix keyMatrix = new Matrix(key);
        double[] vector = new double[key.length];

        for (int i = 0; i < plaintext.length(); i = i + key.length) {

            // we create a vector of m element -where m is according to the key matrix dimension-
            for (int j = 0; j < key.length; j++) {
                vector[j] = hash((int) plaintext.charAt(i + j));
            }

            // Here we apply the equation C = PK
            Matrix resultVector = (new Matrix(vector, 1)).times(keyMatrix);

            double[] result = resultVector.getRowPackedCopy();
            for (int j = 0; j < key.length; j++) {
                Ciphertext = Ciphertext + (char) unhash((((int) result[j]) % 26));
            }

        }

        return Ciphertext;
    }

    public static String Decryption(String Ciphertext, double key[][]) {
        String plaintext = "";
        key = inverse(key);//we need the inverse of the key to make Decryption (we create new key then do the same work)
        Matrix keyMatrix = new Matrix(key);
        double[] vector = new double[key.length];

        for (int i = 0; i < Ciphertext.length(); i = i + key.length) {
            // we create a vector of m element -where m is according to the key matrix dimension-
            for (int j = 0; j < key.length; j++) {
                vector[j] = hash((int) Ciphertext.charAt(i + j));
            }

            Matrix re = (new Matrix(vector, 1)).times(keyMatrix);

            double[] result = re.getRowPackedCopy();
            for (int j = 0; j < key.length; j++) {
                plaintext = plaintext + (char) unhash((((int) result[j]) % 26));
            }
        }

        return plaintext;

    }

    public static double[][] inverse(double[][] values) {
        Matrix matrix = new Matrix(values);
        int determinant = (int) matrix.det();

        //Make sure that the determinant is mod 26
        if (determinant <= 0) {
            while (determinant < 0) {
                determinant = (determinant) + 26;
            }
        } else {
            determinant = determinant % 26;
        }

        int determinant2 = modularMultiplicativeInverse(determinant, 26);
        Matrix matrixInverse = (matrix.inverse()).times(matrix.det());
        double[][] newValues = matrixInverse.getArray();// Final result will be saved 

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {

                if ((int) newValues[i][j] < 0) {
                    
                    while ((int) newValues[i][j] < 0) {
                        newValues[i][j] = (int) newValues[i][j] + 26;
                    }
                    newValues[i][j] = (determinant2 * ((int) newValues[i][j] % 26)) % 26;
                    
                } else {
                    
                    newValues[i][j] = (determinant2 * ((int) newValues[i][j] % 26)) % 26;
                
                }
            }

        }

        return newValues;
    }

    //This function from GeeksforGeeks
    public static int modularMultiplicativeInverse(int num, int mod) {
        for (int x = 1; x < mod; x++) {
            if (((num % mod) * (x % mod)) % mod == 1) {
                return x;
            }
        }
        return 1;
    }

    //Convert ASCII code of the letters from 97-122  to 0-26
    public static int hash(int num) {
        return num - 97;
    }

    //Get back the correct ASCII code of the letters 
    public static int unhash(int num) {
        return num + 97;
    }
}
