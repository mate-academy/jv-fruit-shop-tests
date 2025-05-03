package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileAdministrating {
    public static void writeDataToFile(String filePath, String data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new java.io.FileWriter(filePath, false))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }

    public static void renewInputFile(String filePath) {
        FileAdministrating.writeDataToFile(filePath, "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50");
    }

    public static String getDataFromFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.readAllLines(Path.of(filePath))
                    .forEach(s -> stringBuilder.append(s).append("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath);
        }
        return stringBuilder.toString().trim();
    }
}
