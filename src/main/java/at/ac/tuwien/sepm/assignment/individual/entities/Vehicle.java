package at.ac.tuwien.sepm.assignment.individual.entities;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Year;

public class Vehicle {

    private Long id;
    private String name;
    private Integer buildyear;
    private String description;
    private Integer seats;
    private String licenseplate;
    private PowerSource powerSource;
    private Double power;
    private Integer hourlyRateCents;
    private LocalDateTime createtime;
    private String picture;

    public Vehicle(String name, Integer buildyear, String description, Integer seats, String licenseplate, PowerSource powerSource, Double power, Integer hourlyRateCents, LocalDateTime createtime) {
        this.name = name;
        this.buildyear = buildyear;
        this.description = description;
        this.seats = seats;
        this.licenseplate = licenseplate;
        this.powerSource = powerSource;
        this.power = power;
        this.hourlyRateCents = hourlyRateCents;
        this.createtime = createtime;
        this.picture = picture;
    }

    public Vehicle(Long id, String name, Integer buildyear, String description, Integer seats, String licenseplate, PowerSource powerSource, Double power, Integer hourlyRateCents, LocalDateTime createtime) {
        this.id = id;
        this.name = name;
        this.buildyear = buildyear;
        this.description = description;
        this.seats = seats;
        this.licenseplate = licenseplate;
        this.powerSource = powerSource;
        this.power = power;
        this.hourlyRateCents = hourlyRateCents;
        this.createtime = createtime;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuildyear() {
        return buildyear;
    }

    public void setBuildyear(Integer buildyear) {
        this.buildyear = buildyear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public PowerSource getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Integer getHourlyRateCents() {
        return hourlyRateCents;
    }

    public void setHourlyRateCents(Integer hourlyRateCents) {
        this.hourlyRateCents = hourlyRateCents;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", buildyear=" + buildyear +
            ", description='" + description + '\'' +
            ", seats=" + seats +
            ", licenseplate='" + licenseplate + '\'' +
            ", powerSource=" + powerSource +
            ", power=" + power +
            ", hourlyRateCents=" + hourlyRateCents +
            ", createtime=" + createtime +
            ", picture='" + picture + '\'' +
            '}';
    }
}
