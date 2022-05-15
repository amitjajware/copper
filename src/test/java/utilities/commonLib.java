package utilities;

import java.io.*;
import java.util.*;

public class commonLib {

    public static String readFileAsString(final String filePath) {
        byte[] buffer = null;
        BufferedInputStream inputStream = null;
        try {
            buffer = new byte[(int) (new File(filePath).length())];
            inputStream =
                    new BufferedInputStream(new FileInputStream(filePath));
            inputStream.read(buffer);
        } catch (FileNotFoundException e1) {
            System.out.println("File not found @ " + filePath);
        } catch (IOException e) {
            System.out.println("File couldn't be read from " + filePath + "]");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.out.println(
                        "Exception while closing file " + filePath);
            }
        }

        return new String(buffer);

    }
}
