package com.example.projekt_01;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.fxml.Initializable;
import javafx.application.Platform;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable
{

    @FXML
    private BorderPane scene;
    @FXML
    private CheckMenuItem darkModeButton;
    @FXML
    private TableView<DashboardData> dataTable;
    @FXML
    private TableColumn<DashboardData, String> sales;
    @FXML
    private TableColumn<DashboardData, String> orders;
    @FXML
    private TableColumn<DashboardData, String> reorderAlerts;
    @FXML
    private TableColumn<DashboardData, String> missingItems;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button ordersButton;
    @FXML
    private Button inventoryButton;
    DarkMode darkMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        Platform.runLater(() -> {
            darkMode = new DarkMode(scene, darkModeButton);
            darkMode.checkAndSetInitialDarkModeState();
            setTableData();
        });
        sales.setCellValueFactory(new PropertyValueFactory<>("sales"));
        orders.setCellValueFactory(new PropertyValueFactory<>("orders"));
        reorderAlerts.setCellValueFactory(new PropertyValueFactory<>("reorderAlerts"));
        missingItems.setCellValueFactory(new PropertyValueFactory<>("missingItems"));
        welcomeLabel.setText("Welcome, " + StartController.getUsername());
    }
    @FXML
    private void enableDarkMode(ActionEvent actionEvent)
    {
        darkMode.enableDarkMode();
    }
    @FXML
    private void openOrders(ActionEvent actionEvent)
    {
        WindowManager.openWindow("Orders.fxml", ordersButton);
    }
    @FXML
    private void openInventory(ActionEvent actionEvent)
    {
        WindowManager.openWindow("Inventory.fxml", inventoryButton);
    }


    private void setTableData() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Create an array of query strings
        String[] queries =
            {
            "SELECT SUM(total_cost) FROM orders WHERE order_date >= DATE(NOW()) - INTERVAL 1 WEEK;",
            "SELECT COUNT(*) FROM orders WHERE order_date >= DATE(NOW()) - INTERVAL 1 WEEK;",
            "SELECT COUNT(*) FROM inventory WHERE quantity_on_hand <= reorder_point;",
            "SELECT COUNT(*) FROM inventory WHERE quantity_on_hand = 0;"
            };
        try {
            Statement statement = connectDB.createStatement();
            String[] results = new String[queries.length];

            for (int i = 0; i < queries.length; i++)
            {
                ResultSet queryResult = statement.executeQuery(queries[i]);
                if (queryResult.next())
                {
                    results[i] = String.valueOf(queryResult.getInt(1));
                }
            }
            System.out.println("Results: " + Arrays.toString(results));
            DashboardData data = new DashboardData(results[0], results[1], results[2], results[3]);
            dataTable.getItems().add(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void aboutMenuItemOnAction(ActionEvent actionEvent)
    {
        WindowManager.showAboutPage();
    }
}