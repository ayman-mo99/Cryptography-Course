package dataencryptionstandard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DataEncryptionStandard extends Application {

    TextField plaintext = new TextField();
    TextField key1 = new TextField();
    TextField result1 = new TextField();
    Button Encrypt = new Button("Encrypt");

    TextField ciphertext = new TextField();
    TextField key2 = new TextField();
    TextField result2 = new TextField();
    Button Decrypt = new Button("Decrypt");

    static DES_Implementation des = new DES_Implementation();

    public static void main(String[] args) {

        System.out.println(des.Encryption("02468aceeca86420", "0f1571c947d9e859"));//da02ce3a89ecac3b
        System.out.println(des.Decryption("da02ce3a89ecac3b", "0f1571c947d9e859"));//02468aceeca86420
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Action of the buttins
        Encrypt.setOnAction(e -> doEncryption());
        Decrypt.setOnAction(e -> doDecryption());

        //Put all elements together
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        gridPane.add(new Label("  plaintext  "), 0, 0);
        gridPane.add(plaintext, 1, 0);
        gridPane.add(new Label("  key  "), 0, 1);
        gridPane.add(key1, 1, 1);
        gridPane.add(new Label("  Result:  "), 0, 2);
        gridPane.add(result1, 1, 2);
        gridPane.add(Encrypt, 1, 4);

        gridPane.add(new Label("  ciphertext  "), 0, 5);
        gridPane.add(ciphertext, 1, 5);
        gridPane.add(new Label("  key  "), 0, 6);
        gridPane.add(key2, 1, 6);
        gridPane.add(new Label("  Result:  "), 0, 7);
        gridPane.add(result2, 1, 7);
        gridPane.add(Decrypt, 1, 8);

        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("DES"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }

    public void doEncryption() {
        String interest = plaintext.getText();
        String year = key1.getText();
        result1.setText(des.Encryption(interest, year));
    }

    public void doDecryption() {
        String interest = ciphertext.getText();
        String year = key2.getText();
        result2.setText(des.Decryption(interest, year));
    }
}
