package one.time.pad.cipher;

public class OneTimePadCipher {

    public static void main(String[] args) {
        String key = randomKey(27);
        System.out.println(Encryption("wearediscoveredsaveyourself", key));
        System.out.println(Decryption(Encryption("wearediscoveredsaveyourself", key), key));

    }

    public static String Encryption(String plaintext, String Key) {
        String Ciphertext = "";
        int temp = 0;
        
        for (int i = 0; i < plaintext.length(); i++) {
            temp = 0;
            temp = ( hash((int) plaintext.charAt(i)) + hash((int) Key.charAt(i)) ) % 26;
            Ciphertext = Ciphertext + (char) unhash(temp);
        }
        
        return Ciphertext;
    }

    public static String Decryption(String Ciphertext, String Key) {
        String plaintext = "";
        int temp = 0;

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
    
    //Convert ASCII code of the letters from 97-122  to 0-26
    public static int hash(int num) {
        return num - 97;
    }
    
    //Get back the correct ASCII code of the letters 
    public static int unhash(int num) {
        return num + 97;
    }

    public static String randomKey(int n) {
        String key = "";
        int temp = 0;
        
        for (int i = 0; i < n; i++) {
            temp = (int) (Math.random()*27);
            key = key + (char) unhash(temp);
        }
        
        return key;
    }
}
