package advanced.encryption.standard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdvancedEncryptionStandard extends Application {

    //Elaments of the lay out
    TextField plaintext1 = new TextField();
    TextField key1 = new TextField();
    TextField ciphertext1 = new TextField();
    Button readPlaintext = new Button("Read plaintext from file");
    Button readkey1 = new Button("Read key from file");
    Button writeCiphert = new Button("write ciphert text on file");
    Button enc = new Button("Encrypt");

    TextField ciphertext2 = new TextField();
    TextField key2 = new TextField();
    TextField plaintext2 = new TextField();
    Button readCiphertext = new Button("Read ciphertext from file");
    Button readkey2 = new Button("Read key from file");
    Button writePlaintext = new Button("write plaintext on file");
    Button dec = new Button("decrypt");

    Button btCancel = new Button("CLOSE");

    public static void main(String[] args) throws FileNotFoundException {
        Application.launch(args);
        //System.out.println(aes.Encryption("0123456789abcdeffedcba9876543210", "0f1571c947d9e8590cb7add6af7f6798"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileChooser fileChooser = new FileChooser();

        // Action of the buttins
        readPlaintext.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                String ss = readFile(selectedFile);
                plaintext1.setText(ss);
            } catch (IOException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        readkey1.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                String ss = readFile(selectedFile);
                key1.setText(ss);
            } catch (IOException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        writeCiphert.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                writeFile(selectedFile, ciphertext1.getText());
            } catch (IOException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        enc.setOnAction(e -> {
            try {
                doEncryption();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        readCiphertext.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                String ss = readFile(selectedFile);
                ciphertext2.setText(ss);
            } catch (IOException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        readkey2.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                String ss = readFile(selectedFile);
                key2.setText(ss);
            } catch (IOException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dec.setOnAction(e -> {
            try {
                doDecryption();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        writePlaintext.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                writeFile(selectedFile, plaintext2.getText());
            } catch (IOException ex) {
                Logger.getLogger(AdvancedEncryptionStandard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btCancel.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        //Put all elements together
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //*******************************Encryption
        gridPane.add(new Label(" plaintext "), 0, 0);
        gridPane.add(plaintext1, 1, 0);
        gridPane.add(readPlaintext, 2, 0);

        gridPane.add(new Label(" key "), 0, 1);
        gridPane.add(key1, 1, 1);
        gridPane.add(readkey1, 2, 1);

        gridPane.add(enc, 1, 2);

        gridPane.add(new Label(" ciphertext "), 0, 3);
        gridPane.add(ciphertext1, 1, 3);
        gridPane.add(writeCiphert, 2, 3);

        //************************************Decryption
        gridPane.add(new Label(" ciphertext "), 0, 10);
        gridPane.add(ciphertext2, 1, 10);
        gridPane.add(readCiphertext, 2, 10);

        gridPane.add(new Label(" key "), 0, 11);
        gridPane.add(key2, 1, 11);
        gridPane.add(readkey2, 2, 11);

        gridPane.add(dec, 1, 12);

        gridPane.add(new Label(" plaintext "), 0, 13);
        gridPane.add(plaintext2, 1, 13);
        gridPane.add(writePlaintext, 2, 13);

        //*************************************
        gridPane.add(btCancel, 2, 25);

        Scene scene = new Scene(gridPane, 500, 400);
        primaryStage.setTitle("Advanced Encryption Standard"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }

    public void doEncryption() throws FileNotFoundException {
        AES_Implementation aes = new AES_Implementation();
        String ciphertext = aes.Encryption(plaintext1.getText(), key1.getText());
        ciphertext1.setText(ciphertext);
    }

    public void doDecryption() throws FileNotFoundException {

        //AES_Implementation aes = new AES_Implementation();
        //String plaintext = aes.Encryption(ciphertext2.getText(), key2.getText());
        //plaintext2.setText(plaintext);
        plaintext2.setText("Not completed yet");
    }

    public String readFile(File file) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
        } catch (FileNotFoundException ex) {
            //  Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                //   Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stringBuffer.toString();
    }

    public void writeFile(File file, String s) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {

            FileWriter fw = new FileWriter(file);
            fw.write(s);
            fw.close();

            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
        } catch (FileNotFoundException ex) {
            //  Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                //   Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
