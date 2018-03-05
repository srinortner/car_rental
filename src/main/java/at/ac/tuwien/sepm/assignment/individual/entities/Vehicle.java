package at.ac.tuwien.sepm.assignment.individual.entities;

import java.time.LocalDateTime;
import java.time.Year;

public class Vehicle {

    private long id;
    private String name;
    private int buildyear;
    private String description;
    private int seats;
    private String licenseplate;
    private boolean hasEngine;
    private double power;
    private int hourlyRateCents;
    private LocalDateTime createtime;

    public Vehicle(long id, String name, int buildyear, String description, int seats, String licenseplate, boolean hasEngine, double power, int hourlyRateCents, LocalDateTime createtime) {
        this.id = id;
        this.name = name;
        this.buildyear = buildyear;
        this.description = description;
        this.seats = seats;
        this.licenseplate = licenseplate;
        this.hasEngine = hasEngine;
        this.power = power;
        this.hourlyRateCents = hourlyRateCents;
        this.createtime = createtime;
    }
    public Vehicle(String name, int buildyear, String description, int seats, String licenseplate, boolean hasEngine, double power, int hourlyRateCents) {
        this.name = name;
        this.buildyear = buildyear;
        this.description = description;
        this.seats = seats;
        this.licenseplate = licenseplate;
        this.hasEngine = hasEngine;
        this.power = power;
        this.hourlyRateCents = hourlyRateCents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuildyear() {
        return buildyear;
    }

    public void setBuildyear(int buildyear) {
        this.buildyear = buildyear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public boolean isHasEngine() {
        return hasEngine;
    }

    public void setHasEngine(boolean hasEngine) {
        this.hasEngine = hasEngine;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public int getHourlyRateCents() {
        return hourlyRateCents;
    }

    public void setHourlyRateCents(int hourlyRateCents) {
        this.hourlyRateCents = hourlyRateCents;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }
}
