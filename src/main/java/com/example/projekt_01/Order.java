package com.example.projekt_01;

import java.time.LocalDate;

public class Order {
    private int id;
    private String customerName;
    private LocalDate orderDate;
    private String orderStatus;
    private double totalCost;

    public Order(int id, String customer_name, String order_date, String order_status, double total_cost)
    {
        this.id = id;
        this.customerName = customer_name;
        this.orderDate = LocalDate.parse(order_date);
        this.orderStatus = order_status;
        this.totalCost = total_cost;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate)
    {
        this.orderDate = orderDate;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public double getTotalCost()
    {
        return totalCost;
    }

    public void setTotalCost(double totalCost)
    {
        this.totalCost = totalCost;
    }
}