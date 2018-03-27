package at.ac.tuwien.sepm.assignment.individual.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class License {

    private LicenseType licenseType;
    private LocalDate licenseDate;
    private String licenseNumber;

    public License(LicenseType licenseType, LocalDate licenseDate, String licenseNumber) {
        this.licenseType = licenseType;
        this.licenseDate = licenseDate;
        this.licenseNumber = licenseNumber;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public LocalDate getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(LocalDate licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
