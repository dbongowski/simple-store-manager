package com.example.projekt_01;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OrdersController implements Initializable
{
    @FXML
    private BorderPane scene;
    @FXML
    private CheckMenuItem darkModeButton;
    DarkMode darkMode;

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> id;
    @FXML
    private TableColumn<Order, String> customerName;
    @FXML
    private TableColumn<Order, String> orderDate;
    @FXML
    private TableColumn<Order, String> orderStatus;
    @FXML
    private TableColumn<Order, Double> totalCost;
    @FXML
    private TableView<OrderItems> orderItemsTable;
    @FXML
    private TableColumn<OrderItems, Integer> orderId;
    @FXML
    private TableColumn<OrderItems, String> productName;
    @FXML
    private TableColumn<OrderItems, Integer> quantity;
    @FXML
    private TableColumn<OrderItems, Double> price;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button inventoryButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        darkMode = new DarkMode(scene, darkModeButton);
        Platform.runLater(() -> darkMode.checkAndSetInitialDarkModeState());
        setTables();

    }

    private void setTables()
    {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        totalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        // Set up the columns in the order items table
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Retrieve orders and order items from the database
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        ObservableList<OrderItems> orderItemsList = FXCollections.observableArrayList();
        try
        {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection conn = connectNow.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
            while (rs.next())
            {
                int orderId = rs.getInt("order_id");
                String customerName = rs.getString("customer_name");
                String orderDate = rs.getString("order_date");
                String orderStatus = rs.getString("order_status");
                double totalCost = rs.getDouble("total_cost");
                Order order = new Order(orderId, customerName, orderDate, orderStatus, totalCost);
                ordersList.add(order);

                // Retrieve order items for this order from the database
                Statement stmt2 = conn.createStatement(); // Create a separate Statement object for the inner query
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM order_items WHERE order_id = " + orderId);
                while (rs2.next())
                {
                    String productName = rs2.getString("product_name");
                    int quantity = rs2.getInt("quantity");
                    double price = rs2.getDouble("price");
                    OrderItems orderItem = new OrderItems(orderId, productName, quantity, price);
                    orderItemsList.add(orderItem);
                }
                rs2.close(); // Close the ResultSet after usage
                stmt2.close(); // Close the Statement after usage
            }
            rs.close(); // Close the ResultSet after usage
            stmt.close(); // Close the Statement after usage
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }

        // Populate the tableviews with the data
        ordersTable.setItems(ordersList);
        orderItemsTable.setItems(orderItemsList);

    }

    @FXML
    private void enableDarkMode(ActionEvent actionEvent)
    {
        darkMode.enableDarkMode();
    }
    @FXML
    private void openMainMenu(ActionEvent actionEvent)
    {
        WindowManager.openWindow("MainMenu.fxml", mainMenuButton);
    }
    @FXML
    private void openInventory(ActionEvent actionEvent)
    {
        WindowManager.openWindow("Inventory.fxml", inventoryButton);
    }
    @FXML
    private void aboutMenuItemOnAction(ActionEvent actionEvent)
    {
        WindowManager.showAboutPage();
    }
}
