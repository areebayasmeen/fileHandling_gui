package com.example.myowngui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.*;

public class HelloApplication extends Application {
    TextField nameField=new TextField() ;

     TextField ageField = new TextField();
     String line;
    TextArea textArea = new TextArea();


    @Override
    public void start(Stage stage) throws IOException {
//        StackPane stackPane=new StackPane();
        GridPane grid = new GridPane();

       // grid.setGridLinesVisible(true);
        grid.setHgap(10); // Horizontal gap between nodes
        grid.setVgap(10);
        Scene scene =new Scene(grid,600,500);



        Button button=new Button("add");
        button.setPrefHeight(10);
        button.setPrefWidth(150);
        Insets buttonInsets = new Insets(10);
        Insets textInsets = new Insets(5);
        GridPane.setMargin(button, buttonInsets);
        GridPane.setMargin(nameField, textInsets);
        GridPane.setMargin(ageField, textInsets);
        Button button1=new Button("show details");

        textArea.setEditable(false);

        button.setOnAction( e -> {

          addMembers();

        });
        button1.setOnAction( e -> {

          showMembers();

        });

        nameField.setPromptText("name");
        ageField.setPromptText("age");


        grid.add(button, 3, 1);
        grid.add(nameField, 3, 2);
        grid.add(ageField, 3, 3);
        grid.add(button1,3,4);
        grid.add(textArea,3,5);


        stage.setScene(scene);
        stage.show();
    }
    void addMembers(){

        HelloApplication helloApplication=new HelloApplication();
        String name = nameField.getText();
        String age = ageField.getText();
//        nameField = new TextField();

        try {
            FileWriter fileWriter=new FileWriter("memebers.txt",true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(" ");
            bufferedWriter.write(name);
            bufferedWriter.write(" ");
            bufferedWriter.write(age);
            bufferedWriter.close();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void showMembers() {
        StringBuilder content = new StringBuilder();

        try {
            FileReader   fileReader = new FileReader("memebers.txt");
            BufferedReader  bufferedReader=new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n"); // Append line to content
            }
            textArea.setText(content.toString());
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        launch();
    }
}