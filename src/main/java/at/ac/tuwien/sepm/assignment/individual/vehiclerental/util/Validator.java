package at.ac.tuwien.sepm.assignment.individual.vehiclerental.util;

import at.ac.tuwien.sepm.assignment.individual.entities.*;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.commons.validator.routines.IBANValidator;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class Validator {


    private static final CreditCardValidator CREDIT_CARD_VALIDATOR = new CreditCardValidator();
    private static final IBANValidator IBAN_VALIDATOR = new IBANValidator();

    private Validator() {
        // intentionally empty cause Validator is a Utility class
    }

    public static void validateVehicle(Vehicle vehicle) throws InvalidVehicleException {
        List<String> constraintViolations = new ArrayList<>();
        if (vehicle == null) {
            constraintViolations.add("Vehicle must not be null");
        } else {
            if (isNull(vehicle.getName()) || vehicle.getName().isEmpty()) {
                constraintViolations.add("Name must not be empty!");
            }
            if (isNull(vehicle.getBuildyear()) || vehicle.getBuildyear() <= 0) {
                constraintViolations.add("Buildyear must be a valid integer greater than zero!");
            }
            if (isNull(vehicle.getHourlyRateCents()) || vehicle.getHourlyRateCents() < 0) {
                constraintViolations.add("Hourly price must be a valid number greater than zero!");
            }
            if (vehicle.getPowerSource().equals(PowerSource.ENGINE) && isNull(vehicle.getPower())) {
                constraintViolations.add("The power mustn't be null!");
            }
            if(isNull(vehicle.getPowerSource())) {
                constraintViolations.add("Power source mustn't be emptry!");
            }
            if(!vehicle.getLicenseType().isEmpty() && vehicle.getLicenseplate().equals("")){
                constraintViolations.add("This vehicle must have a licenseplate!");
            }
        }
        if (!constraintViolations.isEmpty()) {
            throw new InvalidVehicleException(constraintViolations);
        }
    }

    public static void validateBooking(Booking booking) throws InvalidBookingException {
        List<String> constraintViolations = new ArrayList<>();
        if (booking == null) {
            constraintViolations.add("Booking must not be null");
        } else {
            if(booking.getName() == null){
                constraintViolations.add("Please enter the name of the person booking!");
            }
            if(booking.getPaymentNumber() == null) {
                constraintViolations.add("Please enter payment information!");
            }
            if (booking.getPaymentType().equals(PaymentType.CREDITCARD)) {
                if (!CREDIT_CARD_VALIDATOR.isValid(booking.getPaymentNumber())) {
                    constraintViolations.add("Creditcardnumber is invalid!");
                }
            } else {
                if (!IBAN_VALIDATOR.isValid(booking.getPaymentNumber())) {
                    constraintViolations.add("IBAN is invalid!");
                }
            }
            if(booking.getStartDate().isAfter(booking.getEndDate())) {
                constraintViolations.add("FROM-Date has to be before TO-Date!");
            }
            if(booking.getEndDate().isBefore(booking.getStartDate())) {
                constraintViolations.add("TO-Date has to be after FROM-Date!");
            }
            if(!(booking.getLicensedateA() == null)){
                if(booking.getLicensedateA().isAfter(LocalDate.now())){
                    constraintViolations.add("Licensedate must be before today!");
                }
            }
            if(!(booking.getLicensedateB() == null)){
                if(booking.getLicensedateB().isAfter(LocalDate.now())){
                    constraintViolations.add("Licensedate must be before today!");
                }
            }
            if(!(booking.getLicensedateC() == null)){
                if(booking.getLicensedateC().isAfter(LocalDate.now())){
                    constraintViolations.add("Licensedate must be before today!");
                }
            }
        }

        //checks if the person booking the vehicle has the license for it for more than 3 years
        for (Vehicle vehicle: booking.getBookedVehicles()) {
            for(int i = 0; i <= vehicle.getLicenseType().size()-1; i++) {
                boolean hasA = false;
                boolean hasB = false;
                boolean hasC = false;
                if(vehicle.getLicenseType().get(i) == LicenseType.A) {
                    if(booking.getPersonLicenseList().contains(LicenseType.A)) {
                        hasA = true;
                        if(booking.getLicensenumberA() == null) {
                            constraintViolations.add("Please add A-licensenumber!");
                        }
                        if(booking.getLicensedateA() == null){
                            constraintViolations.add("Please add A-licensedate!");
                        }

                    } else {
                        constraintViolations.add("You are missing a A-license to book this vehicle!");
                    }
                }
                if(vehicle.getLicenseType().get(i) == LicenseType.B) {
                    hasB = true;
                    if(!(booking.getPersonLicenseList().contains(LicenseType.B))) {
                        constraintViolations.add("You are missing a B-license to book this vehicle!");
                    }
                    if(booking.getLicensenumberB() == null) {
                        constraintViolations.add("Please add B-licensenumber!");
                    }
                    if(booking.getLicensedateB() == null){
                        constraintViolations.add("Please add B-licensedate!");
                    }
                }
                if(vehicle.getLicenseType().get(i) == LicenseType.C) {
                    hasC = true;
                    if(booking.getPersonLicenseList().contains(LicenseType.C)) {
                        if(booking.getLicensenumberC() == null) {
                            constraintViolations.add("Please add C-licensenumber!");
                        }
                        if(booking.getLicensedateC() == null){
                            constraintViolations.add("Please add C-licensedate!");
                        }

                    } else {
                        constraintViolations.add("You are missing a C-license to book this vehicle!");
                    }
                }

                if(hasA && !hasB && !hasC) {
                    LocalDate today = LocalDate.now();
                    if (today.minusYears(3).isBefore(booking.getLicensedateA())) {
                        constraintViolations.add("You are not allowed to book this vehicle!");
                    }
                } else if(hasC && !hasB && !hasA) {
                    LocalDate today = LocalDate.now();
                    if (today.minusYears(3).isBefore(booking.getLicensedateC())) {
                        constraintViolations.add("You are not allowed to book this vehicle!");
                    }
                }
            }

        }
        if (!constraintViolations.isEmpty()) {
            throw new InvalidBookingException(constraintViolations);
        }
    }

}
