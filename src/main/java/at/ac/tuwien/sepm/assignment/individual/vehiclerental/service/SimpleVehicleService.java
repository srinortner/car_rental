package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalPictureException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.UUID;

public class SimpleVehicleService  implements VehicleService{

    private VehicleDAO vehicleDAO;
    private Validator validator;
    //TODO: absoluten Path ersetzen?
    private static final String imageDirectory = "/media/susi/PersData/Eigene Dateien/UNI/6.Semester/SEPM PR/sepm-individual-assignment-java/src/main/resources/images";
    private String currentPictureTitle = null;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());


    public SimpleVehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws IllegalVehicleException, IOException {
        if(file != null) {
            currentPictureTitle = addPicture(file);
            vehicle.setPicture(currentPictureTitle);
        }
        //TODO: find out why validator doesn't work
     //   if(validator.validateProduct(vehicle)){
            vehicleDAO.addVehicleToDatabase(vehicle);
            if(vehicle.getHourlyRateCents() < 0) {
                LOG.error("Hourly Price for {} is negative!", vehicle);
                throw new IllegalVehicleException("The vehicle entered is invalid!");
  /*          }
            LOG.debug("Vehicle was added to pesistence.");
        } else {
            LOG.error("Vehicle is invalid.");
            throw new IllegalVehicleException("The vehicle entered is invalid!"); */
        }
        return vehicle;
    }

    @Override
    public String addPicture(File file) throws IOException {
        String title;
        UUID uuid = UUID.randomUUID();
        title = uuid.toString();
        File destination = new File(imageDirectory + "/" + title);
        try {
            BufferedImage picture = ImageIO.read(file);
            int height = picture.getHeight();
            int width = picture.getWidth();
            if(file.length() < 5242880) {
                if (file != null) {
                    if (height >= 500 && width >= 500) {
                        Files.copy(file.toPath(), destination.toPath());
                    } else {
                        LOG.error("The Image doesn't have the required mesurements");
                        throw new IllegalPictureException("The picture entered is invalid");
                    }
                }
            } else {
                LOG.error("The Image is too big " + file.length() + "byte");
                throw new IllegalPictureException("The picture entered is invalid");
            }
        } catch (IllegalPictureException e) {
            LOG.error("Picture could not be saved ");
        }
        return title;
    }

}
