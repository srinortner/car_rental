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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    /**
     * copies file from user source to destiantion directory, is called by addVehicleToPersistence
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public String addPicture(File file) throws IOException {
        if (file != null) {
            String title = createSha1Hash(file);
            File destinationFile = new File(imageDestinationPath.toFile(), title);
            if(destinationFile.exists()) {
                LOG.debug("File already exists, using existing file");
            } else {
                try {
                    BufferedImage picture = ImageIO.read(file);
                    int height = picture.getHeight();
                    int width = picture.getWidth();
                    if (SUPPORTED_FILE_TYPES.stream().noneMatch(ft -> file.getName().toLowerCase().endsWith(ft))) {
                        throw new IllegalPictureException("Filetype not supported");
                    }
                    if (file.length() < FIVE_MEGABYTES) {
                        if (height >= MIN_HEIGHT && width >= MIN_WIDTH) {
                            Files.copy(file.toPath(), destinationFile.toPath());
                        } else {
                            LOG.error("The Image doesn't have the required mesurements");
                            throw new IllegalPictureException("The picture entered is invalid");
                        }
                    } else {
                        LOG.error("The Image is too big " + file.length() + "byte");
                        throw new IllegalPictureException("The picture entered is invalid");
                    }
                } catch (IllegalPictureException e) {
                    LOG.error("Picture could not be saved ");
                }
            }
            return title;
        } else {
            throw new IllegalPictureException("The picture entered is invalid");
        }
    }

    /**
     * Create SHA-1 Hash of a File.
     * Compares the hash instead of the name and finds out if the picture is already saved to avoid doubles
     * Original Source: https://stackoverflow.com/questions/6293713/java-how-to-create-sha-1-for-a-file
     * @param file to calculate hash code from
     * @return SHA-1 hash of given file
     */
    private String createSha1Hash(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            InputStream fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                if ((0xff & aHash) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & aHash)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & aHash));
                }
            }
            return hexString.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            LOG.warn("Cannot generate SHA-1 hash", e);
            return UUID.randomUUID().toString();
        }
    }

    public List<Vehicle> getListOfVehiclesFromPersistence() {
        List<Vehicle> newVehicleList = vehicleDAO.getAllVehiclesFromDatabase();
        return  newVehicleList;
    }


}
