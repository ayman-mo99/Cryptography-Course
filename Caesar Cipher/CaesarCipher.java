package caesar.cipher;

public class CaesarCipher {

    public static void main(String[] args) {
        System.out.println(Encryption("a b c d e f g h i j k l m n o p q r s t u v w x y z", 3));
        System.out.println(Decryption("d e f g h i j k l m n o p q r s t u v w x y z a b c", 3));
        Attack("d e f g h i j k l m n o p q r s t u v w x y z a b c");

    }

    public static String Encryption(String plaintext, int Key) {
        String Ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if ((plaintext.charAt(i) + "").equals(" ")) {
                Ciphertext = Ciphertext.concat(" ");
            } else if (plaintext.charAt(i) + Key > 122) {
                Ciphertext = Ciphertext + (char) ((Key - 122 + plaintext.charAt(i) + 96) % 123);
            } else {
                Ciphertext = Ciphertext + (char) (((plaintext.charAt(i) + Key) % 123));
            }
        }
        return Ciphertext;
    }

    public static String Decryption(String Ciphertext, int Key) {
        String plaintext = "";
        for (int i = 0; i < Ciphertext.length(); i++) {
            if ((Ciphertext.charAt(i) + "").equals(" ")) {
                plaintext = plaintext.concat(" ");
            } else if (Ciphertext.charAt(i) - Key < 97) {
                plaintext = plaintext + (char) ((123 - (97 - Ciphertext.charAt(i) + Key) % 123));
            } else {
                plaintext = plaintext + (char) (((Ciphertext.charAt(i) - Key) % 123));
            }
        }
        return plaintext;

    }

    public static void Attack(String Ciphertext) {
        for (int i = 0; i < 26; i++) {
            System.out.println(i + " " + Decryption(Ciphertext, i));
        }
    }
}
