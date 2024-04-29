package com.example.projekt_01;

public class Inventory
{
    private int id;
    private String productName;
    private int quantityOnHand;
    private int reorderPoint;
    private double price;

    public Inventory(int id, String productName, int quantityOnHand, int reorderPoint, double price)
    {
        this.id = id;
        this.productName = productName;
        this.quantityOnHand = quantityOnHand;
        this.reorderPoint = reorderPoint;
        this.price = price;
    }

    public int getId()
    {
        return id;
    }

    public String getProductName()
    {
        return productName;
    }

    public int getQuantityOnHand()
    {
        return quantityOnHand;
    }

    public int getReorderPoint()
    {
        return reorderPoint;
    }

    public double getPrice()
    {
        return price;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public void setQuantityOnHand(int quantityOnHand)
    {
        this.quantityOnHand = quantityOnHand;
    }

    public void setReorderPoint(int reorderPoint)
    {
        this.reorderPoint = reorderPoint;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }


}
