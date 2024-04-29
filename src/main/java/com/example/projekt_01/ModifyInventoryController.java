package com.example.projekt_01;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyInventoryController implements Initializable
{
    @FXML
    private BorderPane scene;
    ModifyWindowDarkMode darkMode;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField quantityOnHandTextField;
    @FXML
    private TextField reorderPointTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Label errorLabel;
    private boolean update = false;
    private int id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        darkMode = new ModifyWindowDarkMode(scene);
        Platform.runLater(() -> darkMode.checkAndSetInitialDarkModeState());
    }
    @FXML
    private void save(ActionEvent actionEvent)
    {
        if(productNameTextField.getText().isEmpty() || quantityOnHandTextField.getText().isEmpty() || reorderPointTextField.getText().isEmpty() || priceTextField.getText().isEmpty())  //check if any field is empty
        {
            System.out.println("Please fill in all fields");
            errorLabel.setText("Please fill in all fields");
        }
        else
        {
            errorLabel.setText("Done!");
            insert();
            exit();
        }
    }
    private void insert()
    {
        String query;
        if(update)
        {
            query = "UPDATE inventory SET product_name = ?, quantity_on_hand = ?, reorder_point = ?, price = ? WHERE id = '"+id+"'";
        }
        else
        {
            query = "INSERT INTO inventory (product_name, quantity_on_hand, reorder_point, price) VALUES (?,?,?,?)";
        }
        try
        {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection conn = connectNow.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, productNameTextField.getText());
            preparedStatement.setString(2, quantityOnHandTextField.getText());
            preparedStatement.setString(3, reorderPointTextField.getText());
            preparedStatement.setString(4, priceTextField.getText());
            preparedStatement.execute();
            conn.close();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }

    }
    private void exit()
    {
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.close();
    }
    public void setUpdate(boolean update)
    {
        this.update = update;
    }
    void setTextFields(int id, String productName, int quantityOnHand, int reorderPoint, double price)
    {
        this.id = id;
        productNameTextField.setText(productName);
        quantityOnHandTextField.setText(String.valueOf(quantityOnHand));
        reorderPointTextField.setText(String.valueOf(reorderPoint));
        priceTextField.setText(String.valueOf(price));
    }

}
