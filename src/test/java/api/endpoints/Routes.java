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
    public static String baseUrl = properties.getProperty("base.url");

    // Video model endpoints
    public static String getVideoByIdUrl(String videoId) {
        return baseUrl + properties.getProperty("video.getById.url") + videoId;
    }

    public static String postVideoUrl() {
        return baseUrl + properties.getProperty("video.post.url");
    }

    public static String updateVideoUrl(String videoId) {
        return baseUrl + properties.getProperty("video.update.url") + videoId;
    }

    public static String deleteVideoUrl(String videoId) {
        return baseUrl + properties.getProperty("video.delete.url") + videoId;
    }

    // Method to get the Authorization header
    public static String getAuthHeader() {
        return "Bearer " + api.endpoints.AuthService.getToken();
    }

    public static String getAuthUrl() {
        return baseUrl + properties.getProperty("auth.url");
    }
}
