package at.ac.tuwien.sepm.assignment.individual.vehiclerental.util;

public class Parser {

    private Parser() {
        // intentionally empty cause Validator is a Utility class
    }

    public static Integer parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
