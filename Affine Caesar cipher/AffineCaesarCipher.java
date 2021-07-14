package affine.caesar.cipher;

public class AffineCaesarCipher {

    public static void main(String[] args) {
        System.out.println(Encryption("a b c d e f g h i j k l m n o p q r s t u v w x y z", 5, 8));
        System.out.println(Decryption("i n s x c h m r w b g l q v a f k p u z e j o t y d", 5, 8));
        Attack("i n s x c h m r w b g l q v a f k p u z e j o t y d");
    }

    public static String Encryption(String plaintext, int a, int b) {
        String Ciphertext = "";
        int temp = 0;
        
        for (int i = 0; i < plaintext.length(); i++) {
            
            if ((plaintext.charAt(i) + "").equals(" ")) {
                Ciphertext = Ciphertext.concat(" ");
            } else {
                temp = hash((int) plaintext.charAt(i));
                Ciphertext = Ciphertext + (char) unhash((a * temp + b) % 26);
            }
            
        }
        return Ciphertext;
    }

    public static String Decryption(String Ciphertext, int a, int b) {
        String plaintext = "";
        int temp = 0;
        int invers = 0;
        
        for (int i = 0; i < Ciphertext.length(); i++) {
            if ((Ciphertext.charAt(i) + "").equals(" ")) {
                plaintext = plaintext.concat(" ");
            } else {

                temp = hash((int) Ciphertext.charAt(i));
                invers = modularMultiplicativeInverse(a, 26);
                
                if ((invers * (temp - b)) % 26 < 0) {
                    plaintext = plaintext + (char) unhash((invers * (temp - b)) % 26 + 26);
                } else {
                    plaintext = plaintext + (char) unhash((invers * (temp - b)) % 26);
                }
                
            }
        }
        
        return plaintext;
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

    public static void Attack(String Ciphertext) {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                System.out.println("a=" + i + " " + "b=" + j + " " + Decryption(Ciphertext, i, j));
            }
        }
    }
}
