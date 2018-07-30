package fileutil;

import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by User on 28.07.2018.
 */
public class ReadProperty {
    public static Properties readDataFromProperties(){
        System.out.println(getFolderToResoureces());

        Properties prop = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(getFolderToResoureces())) {
            prop.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }



    private static String getFolderToResoureces() {
        String folder = null;
        try {
            folder = new File(".").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return folder + "/src/main/resources/application.properties";
    }
}
