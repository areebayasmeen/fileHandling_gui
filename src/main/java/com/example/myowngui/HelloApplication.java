package com.example.myowngui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class HelloApplication extends Application {
    Members members=new Members();
    TextField nameField=new TextField() ;
    TextField ageField = new TextField();
    // TextArea textArea = new TextArea();
    RadioButton male=new RadioButton("male");
    RadioButton female=new RadioButton("female");
    ToggleGroup toggleGroup=new ToggleGroup();
    String selectedOption=null;
    String membershipType;
    ComboBox<String> comboBox=new ComboBox<>();
    DatePicker datePicker=new DatePicker();
    LocalDate date;
    TableView<Members> tableView = new TableView<>();
    ObservableList<Members> membersList ;
    


//start method
    @Override
    public void start(Stage stage) throws IOException {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        Scene scene =new Scene(grid,600,500);

        membersList = FXCollections.observableArrayList(new Members());

        TableColumn<Members, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Members, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Members, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Members, String> membershipColumn = new TableColumn<>("Membership");
        membershipColumn.setCellValueFactory(new PropertyValueFactory<>("membership"));

        TableColumn<Members, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date1"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(ageColumn);
        tableView.getColumns().add(genderColumn);
        tableView.getColumns().add(membershipColumn);
        tableView.getColumns().add(dateColumn);
        membersList = readMembers();
        tableView.setItems(membersList);
        tableView.refresh();

        Button backButtonforDetails=new Button("back");
        Button addMembers =new Button("Add Members");//button to take user to the form where he fills out his credentials
        Button button=new Button("add");// button to add user details to file
        Button show_details=new Button("show details");//button to take user to details form
        Button select_membership_type=new Button("select");
        datePicker.setValue(LocalDate.now());
        comboBox.setItems(FXCollections.observableArrayList("Standard", "Premium", "VIP")); // Add items to the ComboBox
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

//button to add members on main scene
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

   button.setOnAction( e1 -> {//button to save member details to file

            addMembers();

        });
backbutton.setOnAction(actionEvent -> {//button to go back to main scene
    try {
        start(stage);
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
});
   stage.setScene(scene1);
   stage.show();

});
//button to show member details on main scene
show_details.setOnAction(e1 -> {
    GridPane gridPane1 = new GridPane();
    Scene scene2 = new Scene(gridPane1, 600, 500);
    gridPane1.add(tableView, 1, 4);
    gridPane1.add(backButtonforDetails, 100, 100);

    backButtonforDetails.setOnAction(actionEvent -> {
        // Clear existing items from the TableView
        tableView.getItems().clear();
        
        // Reload members from the file
        tableView.setItems(readMembers());
        
        // Switch back to the main scene
        stage.setScene(scene);
    });

    stage.setScene(scene2);
    stage.show();
});

        nameField.setPromptText("name");
        ageField.setPromptText("age");

        grid.add(addMembers,1,2);
        grid.add(show_details,1,3);//adding buttons to main scene
        stage.setScene(scene);
        stage.show();
    }
    void selectMembership(){

            membershipType=comboBox.getValue();
  }
  void datepicker(){
        date=datePicker.getValue();
  }
//method to write member to file
    void addMembers(){

        String name = nameField.getText();
        String age = ageField.getText();

                try {
            FileWriter fileWriter = new FileWriter("memebers.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(name  );
            bufferedWriter.write(" ");
            bufferedWriter.write(age  );

                    if (male.isSelected()) {
                        selectedOption = male.getText();
                    } else if (female.isSelected()) {
                        selectedOption = female.getText();
                    }
                    bufferedWriter.write(" ");
                    bufferedWriter.write(selectedOption  );

                    if(membershipType!=null){
                        bufferedWriter.write(" ");
                        bufferedWriter.write(membershipType);
                      }
                    if(date!=null){
                        bufferedWriter.write(" ");
                        bufferedWriter.write(String.valueOf(date  ));}

                    bufferedWriter.newLine();
                    bufferedWriter.close();

        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

// method to read members from file and return a list of members
    //this could contain error too
    public ObservableList<Members> readMembers() {
        try {
            FileReader fileReader = new FileReader("memebers.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
    
            String line;
    
            while ((line = bufferedReader.readLine()) != null) {
                // Ignore lines that are null or only contain whitespace
                if (line.trim().isEmpty()) {
                    continue;
                }
    
                String[] parts = line.split("\\s+");
    
                if (parts.length == 5) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String gender = parts[2];
                    String membership = parts[3];
                    String date1 = parts[4];
    
                    // Ignore entries where any part is null or age is zero
                    if (name == null || age == 0 || gender == null || membership == null || date1 == null) {
                        continue;
                    }
    
                    membersList.add(new Members(name, age, gender, membership, date1));
                }
            }
    
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    
        // Remove the first item if it's null
        if (membersList.get(0).age == 0) {
            membersList.remove(0);
        }
    
        return membersList;
    }
    
    
    

    public static void main(String[] args) {
        launch();
    }
}