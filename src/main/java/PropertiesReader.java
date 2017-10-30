import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static InputStream in;
    private static Properties properties = new Properties();


    public static String getPropertiesByKey (String key) throws FileNotFoundException {
        try {
            in = new FileInputStream("src/main/resources/application.properties");
        } catch (FileNotFoundException e) {
            System.out.println("file application.properties not found");
            e.printStackTrace();
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("Exeption during loading properties from file");
            e.printStackTrace();
        }
        String propertiesValue = properties.getProperty(key);

        return propertiesValue;

    }

}
