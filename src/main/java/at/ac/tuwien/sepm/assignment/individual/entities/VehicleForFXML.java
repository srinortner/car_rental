package at.ac.tuwien.sepm.assignment.individual.entities;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class VehicleForFXML {

    private final LongProperty id;
    private final StringProperty name;
    private final IntegerProperty buildyear;
    private final StringProperty description;
    private final IntegerProperty seats;
    //List of license Requirements converted to String
    private final StringProperty licenseRequirements;
    private final StringProperty licenseplate;
    private final StringProperty powersource;
    private final DoubleProperty power;
    private final IntegerProperty hourlyrate;
    private final ObjectProperty<LocalDateTime> createtime;
    private final StringProperty picture;

    public VehicleForFXML(Long id, String name, Integer buildyear, String description, Integer seats, String licenseRequirements, String licenseplate, String powersource, Double power, Integer hourlyrate, LocalDateTime createtime, String picture) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.buildyear = new SimpleIntegerProperty(buildyear);
        this.description = new SimpleStringProperty(description);
        this.seats = new SimpleIntegerProperty(seats);
        this.licenseRequirements = new SimpleStringProperty(licenseRequirements);
        this.licenseplate = new SimpleStringProperty(licenseplate);
        this.powersource = new SimpleStringProperty(powersource);
        this.power = new SimpleDoubleProperty(power);
        this.hourlyrate = new SimpleIntegerProperty(hourlyrate);
        this.createtime = new SimpleObjectProperty<>(createtime);
        this.picture = new SimpleStringProperty(picture);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getBuildyear() {
        return buildyear.get();
    }

    public IntegerProperty buildyearProperty() {
        return buildyear;
    }

    public void setBuildyear(int buildyear) {
        this.buildyear.set(buildyear);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getSeats() {
        return seats.get();
    }

    public IntegerProperty seatsProperty() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats.set(seats);
    }

    public String getLicenseRequirements() {
        return licenseRequirements.get();
    }

    public StringProperty licenseRequirementsProperty() {
        return licenseRequirements;
    }

    public void setLicenseRequirements(String licenseRequirements) {
        this.licenseRequirements.set(licenseRequirements);
    }

    public String getLicenseplate() {
        return licenseplate.get();
    }

    public StringProperty licenseplateProperty() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate.set(licenseplate);
    }

    public String getPowersource() {
        return powersource.get();
    }

    public StringProperty powersourceProperty() {
        return powersource;
    }

    public void setPowersource(String powersource) {
        this.powersource.set(powersource);
    }

    public double getPower() {
        return power.get();
    }

    public DoubleProperty powerProperty() {
        return power;
    }

    public void setPower(double power) {
        this.power.set(power);
    }

    public int getHourlyrate() {
        return hourlyrate.get();
    }

    public IntegerProperty hourlyrateProperty() {
        return hourlyrate;
    }

    public void setHourlyrate(int hourlyrate) {
        this.hourlyrate.set(hourlyrate);
    }

    public LocalDateTime getCreatetime() {
        return createtime.get();
    }

    public ObjectProperty<LocalDateTime> createtimeProperty() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime.set(createtime);
    }

    public String getPicture() {
        return picture.get();
    }

    public StringProperty pictureProperty() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture.set(picture);
    }
}
