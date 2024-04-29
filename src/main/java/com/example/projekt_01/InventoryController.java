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

public class InventoryController implements Initializable
{

    @FXML
    private BorderPane scene;
    @FXML
    private CheckMenuItem darkModeButton;
    DarkMode darkMode;
    @FXML
    private TableView<Inventory> inventoryTable;
    @FXML
    private TableColumn<Inventory, Integer> id;
    @FXML
    private TableColumn<Inventory, String> productName;
    @FXML
    private TableColumn<Inventory, Integer> quantityOnHand;
    @FXML
    private TableColumn<Inventory, Integer> reorderPoint;
    @FXML
    private TableColumn<Inventory, Double> price;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button ordersButton;
    ModifyInventoryController modifyInventoryController;


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
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityOnHand.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        reorderPoint.setCellValueFactory(new PropertyValueFactory<>("reorderPoint"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        try
        {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection conn = connectNow.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");
            while (rs.next())
            {
                int id = rs.getInt("id");
                String product_name = rs.getString("product_name");
                int quantity_on_hand = rs.getInt("quantity_on_hand");
                int reorder_point = rs.getInt("reorder_point");
                double price = rs.getDouble("price");
                Inventory inventory = new Inventory(id, product_name, quantity_on_hand, reorder_point, price);
                inventoryList.add(inventory);


            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
        inventoryTable.setItems(inventoryList);
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
    private void openOrders(ActionEvent actionEvent)
    {
        WindowManager.openWindow("Orders.fxml", ordersButton);
    }
    @FXML
    private void refresh(ActionEvent actionEvent)
    {
        refreshTable();
    }

    private void refreshTable()
    {
        try
        {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection conn = connectNow.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");
            inventoryTable.getItems().clear();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String product_name = rs.getString("product_name");
                int quantity_on_hand = rs.getInt("quantity_on_hand");
                int reorder_point = rs.getInt("reorder_point");
                double price = rs.getDouble("price");
                Inventory inventory = new Inventory(id, product_name, quantity_on_hand, reorder_point, price);
                inventoryTable.getItems().add(inventory);

            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @FXML
    private void add(ActionEvent actionEvent)
    {
        WindowManager.addWindow("ModifyInventory.fxml", modifyInventoryController);
    }
    @FXML
    private void edit(ActionEvent actionEvent)
    {
        Inventory inventory = inventoryTable.getSelectionModel().getSelectedItem();
        ModifyInventoryController modifyInventoryController = WindowManager.loadModifyInventoryController();
        if (modifyInventoryController != null) {
            modifyInventoryController.setUpdate(true);
            modifyInventoryController.setTextFields(inventory.getId(), inventory.getProductName(), inventory.getQuantityOnHand(), inventory.getReorderPoint(), inventory.getPrice());
        }
    }
    @FXML
    private void delete(ActionEvent actionEvent)
    {
        try
        {
            Inventory inventory = inventoryTable.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM inventory WHERE id = " + inventory.getId();
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection conn = connectNow.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.execute();
            stmt.close();
            conn.close();
            refreshTable();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }


    }
    @FXML
    private void aboutMenuItemOnAction(ActionEvent actionEvent)
    {
        WindowManager.showAboutPage();
    }

}
