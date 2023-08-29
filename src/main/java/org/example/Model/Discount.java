package org.example.Model;

import java.time.LocalDate;

public class Discount {
    //    Fields
    private static int lastId = 0;
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int boatTypeId;
    private float percentage;
    private static final String FILENAME = "src/main/resources/data.json";

//    Constructor
    public Discount(int id, LocalDate startDate, LocalDate endDate, int boatTypeId, float percentage) {
        this.id = ++lastId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.boatTypeId = boatTypeId;
        this.percentage = percentage;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getBoatTypeId() {
        return boatTypeId;
    }

    public void setBoatTypeId(int boatTypeId) {
        this.boatTypeId = boatTypeId;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public static void main() {
        System.out.println("Discount");
    }
}
