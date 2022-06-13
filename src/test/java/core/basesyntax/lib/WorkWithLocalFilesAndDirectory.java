package core.basesyntax.lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorkWithLocalFilesAndDirectory {
    public void createResourceDirectory(Path directoryName) {
        try {
            if (!Files.exists(directoryName)) {
                Files.createDirectories(directoryName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error create directory " + directoryName, e);
        }
    }

    public void createTestFile(Path fileName, String data) {
        try {
            Files.write(fileName, data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error write data to file " + fileName, e);
        }
    }

    public void deleteTestFile(Path fileName) {
        try {
            if (Files.exists(fileName)) {
                Files.delete(fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error remove file " + fileName, e);
        }
    }

    public String readFromFile(Path fileName) {
        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error read data from file " + fileName, e);
        }
    }
}
