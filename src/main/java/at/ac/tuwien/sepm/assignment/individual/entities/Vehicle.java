package at.ac.tuwien.sepm.assignment.individual.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for vehicle information
 */

public class Vehicle {

    private Long id;
    private String name;
    private Integer buildyear;
    private String description;
    private Integer seats;

    private List<LicenseType> licenseType;
    private String licenseplate;
    private PowerSource powerSource;
    private Double power;
    private Integer hourlyRateCents;
    private LocalDateTime createtime;
    private String picture;

    //stays the same in every new version of the vehicle if it is edited
    private String UUIDForEditing;


    private LocalDateTime edittime;


    public Vehicle(String name, Integer buildyear, String description, Integer seats, List<LicenseType> licenseType, String licenseplate, PowerSource powerSource, Double power, Integer hourlyRateCents, LocalDateTime createtime, LocalDateTime edittime) {
        this.name = name;
        this.buildyear = buildyear;
        this.description = description;
        this.seats = seats;
        this.licenseType = licenseType;
        this.licenseplate = licenseplate;
        this.powerSource = powerSource;
        this.power = power;
        this.hourlyRateCents = hourlyRateCents;
        this.createtime = createtime;
        this.UUIDForEditing = UUID.randomUUID().toString();
        this.edittime = edittime;
    }

    public Vehicle(Long id, String name, Integer buildyear, String description, Integer seats, String licenseplate, PowerSource powerSource, Double power, Integer hourlyRateCents, String picture, LocalDateTime createtime, LocalDateTime edittime) {
        this.id = id;
        this.name = name;
        this.buildyear = buildyear;
        this.description = description;
        this.seats = seats;
        //this.licenseType = licenseType;
        this.licenseplate = licenseplate;
        this.powerSource = powerSource;
        this.power = power;
        this.hourlyRateCents = hourlyRateCents;
        this.createtime = createtime;
        this.picture = picture;
        this.UUIDForEditing = UUID.randomUUID().toString();
        this.edittime = edittime;
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

    public List<LicenseType> getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(List<LicenseType> licenseType) {
        this.licenseType = licenseType;
    }

    public String getUUIDForEditing() {
        return UUIDForEditing;
    }

    public void setUUIDForEditing(String UUIDForEditing) {
        this.UUIDForEditing = UUIDForEditing;
    }

    public LocalDateTime getEdittime() {
        return edittime;
    }

    public void setEdittime(LocalDateTime edittime) {
        this.edittime = edittime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) &&
            Objects.equals(name, vehicle.name) &&
            Objects.equals(buildyear, vehicle.buildyear) &&
            Objects.equals(description, vehicle.description) &&
            Objects.equals(seats, vehicle.seats) &&
            Objects.equals(licenseType, vehicle.licenseType) &&
            Objects.equals(licenseplate, vehicle.licenseplate) &&
            powerSource == vehicle.powerSource &&
            Objects.equals(power, vehicle.power) &&
            Objects.equals(hourlyRateCents, vehicle.hourlyRateCents) &&
            Objects.equals(createtime, vehicle.createtime) &&
            Objects.equals(picture, vehicle.picture) &&
            Objects.equals(UUIDForEditing, vehicle.UUIDForEditing) &&
            Objects.equals(edittime, vehicle.edittime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, buildyear, description, seats, licenseType, licenseplate, powerSource, power, hourlyRateCents, createtime, picture, UUIDForEditing, edittime);
    }

    @Override
    public String toString() {
        return name + " ";
    }
}
