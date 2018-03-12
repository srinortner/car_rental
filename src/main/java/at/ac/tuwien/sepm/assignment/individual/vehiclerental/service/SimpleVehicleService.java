package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalPictureException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Validator.validateVehicle;
import static java.util.List.of;

public class SimpleVehicleService implements VehicleService {

    private static final int FIVE_MEGABYTES = 5 * 1024 * 1024;
    private static final int MIN_WIDTH = 500;
    private static final int MIN_HEIGHT = 500;
    private VehicleDAO vehicleDAO;
    private static final Path imageDestinationPath = Paths.get(System.getProperty("user.home"),"/.sepm/images");
    private String currentPictureTitle = null;
    private static final List<String> SUPPORTED_FILE_TYPES = of(".jpg", ".png");

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());


    public SimpleVehicleService(VehicleDAO vehicleDAO) throws IOException {
        if(!imageDestinationPath.toFile().exists() || !imageDestinationPath.toFile().isDirectory()) {
            Files.createDirectories(imageDestinationPath);
        }
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws InvalidVehicleException, IOException {
        if (file != null) {
            currentPictureTitle = addPicture(file);
            vehicle.setPicture(currentPictureTitle);
        }
        validateVehicle(vehicle);
        return vehicleDAO.addVehicleToDatabase(vehicle);
    }

    @Override
    public String addPicture(File file) throws IOException {
        String title;
        UUID uuid = UUID.randomUUID();
        title = uuid.toString();
        File destinationFile = new File(imageDestinationPath.toFile(),title);
        if (file != null) {
            try {
                BufferedImage picture = ImageIO.read(file);
                int height = picture.getHeight();
                int width = picture.getWidth();
                if(SUPPORTED_FILE_TYPES.stream().noneMatch(ft -> file.getName().toLowerCase().endsWith(ft))) {
                    throw new IllegalPictureException("Filetype not supported");
                }
                if (file.length() < FIVE_MEGABYTES) {
                    if (height >= MIN_HEIGHT && width >= MIN_WIDTH) {
                        Files.copy(file.toPath(), destinationFile.toPath());
                    } else {
                        LOG.error("The Image doesn't have the required mesurements");
                        throw new IllegalPictureException("The picture entered is invalid");
                    }
                }

            } catch (IllegalPictureException e) {
                LOG.error("Picture could not be saved ");
            }
        } else {
            LOG.error("The Image is too big " + file.length() + "byte");
            throw new IllegalPictureException("The picture entered is invalid");
        }
        return title;
    }

}
