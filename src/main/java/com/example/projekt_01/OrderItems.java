package com.example.projekt_01;

public class OrderItems
{
    private int orderId;
    private String productName;
    private int quantity;
    private double price;

    public OrderItems(int order_id, String product_name, int quantity, double price) {
        this.orderId = order_id;
        this.productName = product_name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
