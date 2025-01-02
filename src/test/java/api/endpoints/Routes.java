package api.endpoints;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Routes {

    // Load properties from the config file
    private static Properties properties = new Properties();

    static {
        try (FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration properties.", e);
        }
    }

    // Base URL (from properties)
    public static String baseUrl = properties.getProperty("base_url");

    // Video model endpoints
    public static String getVideoByIdUrl(String videoId) {
        String fullUrl = baseUrl + properties.getProperty("video.getById.url") + videoId;
        System.out.println("Full URL for Get Video by ID: " + fullUrl);  // Print the full URL for debugging
        return fullUrl;  // Return the full URL as a string
    }

    public static String postVideoUrl() {
        String fullUrl = baseUrl + properties.getProperty("video.post.url");
        System.out.println("Full URL for Post Video: " + fullUrl);  // Print the full URL for debugging
        return fullUrl;  // Return the full URL as a string
    }

    public static String updateVideoUrl(String videoId) {
        String fullUrl = baseUrl + properties.getProperty("video.update.url") + videoId;
        System.out.println("Full URL for Update Video: " + fullUrl);  // Print the full URL for debugging
        return fullUrl;  // Return the full URL as a string
    }

    public static String deleteVideoUrl(String videoId) {
        String fullUrl = baseUrl + properties.getProperty("video.delete.url") + videoId;
        System.out.println("Full URL for Delete Video: " + fullUrl);  // Print the full URL for debugging
        return fullUrl;  // Return the full URL as a string
    }

    // Method to get the Authorization header
    public static String getAuthHeader() {
        return "Bearer " + api.endpoints.AuthService.getToken();  // Return Authorization header
    }

    public static String getAuthUrl() {
        String fullUrl = baseUrl + properties.getProperty("auth.url");
        System.out.println("Full URL for Auth: " + fullUrl);  // Print the full URL for debugging
        return fullUrl;  // Return the full URL as a string
    }
}
