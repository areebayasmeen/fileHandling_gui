package com.example.myowngui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class HelloApplication extends Application {
    TextField nameField=new TextField() ;
    TextField ageField = new TextField();
     String line;
     TextArea textArea = new TextArea();
    RadioButton male=new RadioButton("male");

    RadioButton female=new RadioButton("female");
    ToggleGroup toggleGroup=new ToggleGroup();
    String selectedOption=null;
    String membershipType;
    ComboBox<String> comboBox=new ComboBox<>();
    DatePicker datePicker=new DatePicker();
  LocalDate date;

    @Override
    public void start(Stage stage) throws IOException {

        GridPane grid = new GridPane();
        // grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(10);
        Scene scene =new Scene(grid,600,500);


      Button backButtonforDetails=new Button("back");
      Button addMembers =new Button("Add Members");//button to take user to the form where he fills out his credentials
      Button button=new Button("add");// button to add user details to file
       Button show_details=new Button("show details");//button to take user to details form
       Button select_membership_type=new Button("select");
        Button SelectDate=new Button("date");
        datePicker.setValue(LocalDate.now());
        comboBox.setItems(FXCollections.observableArrayList(
                "Standard", "Premium", "VIP")); // Add items to the ComboBox


        male.setToggleGroup(toggleGroup); // Add RadioButton to the ToggleGroup
        male.setSelected(true);
        female.setToggleGroup(toggleGroup);

        //for setting gaps between buttons
        Insets buttonInsets = new Insets(10);
        Insets textInsets = new Insets(5);
        GridPane.setMargin(button, buttonInsets);
        GridPane.setMargin(nameField, textInsets);
        GridPane.setMargin(ageField, textInsets);
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                String selectedOption = selectedRadioButton.getText();
                System.out.println("Selected Option: " + selectedOption);


            }
        });


    addMembers.setOnAction( e ->{

    GridPane gridPane=new GridPane() ;
    Button backbutton=new Button("back");
    Scene scene1=new Scene(gridPane,600,500);
    gridPane.add(button, 3, 1);
    gridPane.add(nameField, 3, 2);
    gridPane.add(ageField, 3, 3);
    gridPane.add(backbutton,200,100);
   gridPane.add(male,3,5);
   gridPane.add(female,3,6);
gridPane.add(select_membership_type,3,7);
gridPane.add(comboBox,3,8);
gridPane.add(datePicker,3,9);
datePicker.setOnAction(actionEvent -> {
    datepicker();
});
select_membership_type.setOnAction(actionEvent -> {
    selectMembership();

});

   button.setOnAction( e1 -> {

            addMembers();

        });
backbutton.setOnAction(actionEvent -> {
    try {
        start(stage);
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
});
    stage.setScene(scene1);
   stage.show();

});

        show_details.setOnAction( e1 -> {

            GridPane gridPane1=new GridPane() ;
            Scene scene2=new Scene(gridPane1,600,500);

            gridPane1.add(textArea,3,5);
            textArea.setEditable(false);
             showMembers();
            backButtonforDetails.setOnAction(actionEvent -> {
                try {
                    start(stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            stage.setScene(scene2);
            stage.show();

        });

        nameField.setPromptText("name");
        ageField.setPromptText("age");


       grid.add(addMembers,1,2);
        grid.add(show_details,1,3);
        stage.setScene(scene);
        stage.show();
    }
    void selectMembership(){

            membershipType=comboBox.getValue();
  }
  void datepicker(){
        date=datePicker.getValue();
  }

    void addMembers(){

        HelloApplication helloApplication=new HelloApplication();
        String name = nameField.getText();
        String age = ageField.getText();



                try {
            FileWriter fileWriter = new FileWriter("memebers.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(" ");
            bufferedWriter.write(name);
            bufferedWriter.write(" ");
            bufferedWriter.write(age);
            bufferedWriter.write("");
                    if (male.isSelected()) {
                        selectedOption = male.getText();
                    } else if (female.isSelected()) {
                        selectedOption = female.getText();
                    }

             bufferedWriter.write(selectedOption);
                    if(membershipType!=null){
             bufferedWriter.write(membershipType);}
                    if(date!=null) bufferedWriter.write(String.valueOf(date));

            bufferedWriter.close();

        }


        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void showMembers() {
        StringBuilder content = new StringBuilder();

        try {
            FileReader   fileReader = new FileReader("memebers.txt");
            BufferedReader  bufferedReader=new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
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