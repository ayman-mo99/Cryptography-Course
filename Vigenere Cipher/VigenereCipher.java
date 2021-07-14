package vigenere.cipher;

public class VigenereCipher {

    public static void main(String[] args) {
        System.out.println(Encryption("hello", "it"));
        System.out.println(Decryption("pxtew", "it"));
    }

    public static String Encryption(String plaintext, String Key) {
        String Ciphertext = "";
        Key = newKey(plaintext, Key);
        int temp = 0;
        
        for (int i = 0; i < plaintext.length(); i++) {
            temp = (hash((int) plaintext.charAt(i)) + hash((int) Key.charAt(i))) % 26;
            Ciphertext = Ciphertext + (char) unhash(temp);
        }
        
        return Ciphertext;
    }
    
     public static String Decryption(String Ciphertext, String Key) {
        String plaintext = "";
        int temp = 0;
        Key = newKey(Ciphertext, Key);
        
        for (int i = 0; i < Ciphertext.length(); i++) {
            temp = 0;
            temp = ((hash((int) Ciphertext.charAt(i)) - hash((int) Key.charAt(i))));
            
            if (temp < 0) {
                temp = temp + 26;
            }
            plaintext = plaintext + (char) unhash(temp);

        }

        return plaintext;
    }

    public static String newKey(String plaintext, String key) {
        int n = plaintext.length();
        String newKey = key;

        while (newKey.length() < n) {
            newKey = newKey + key;
            
            if (newKey.length() + key.length() > n) {
                break;
            }
            
        }
        
        newKey = newKey + key.substring(0, n - newKey.length());
        return newKey;
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
