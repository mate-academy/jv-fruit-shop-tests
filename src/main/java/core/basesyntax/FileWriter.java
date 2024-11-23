package core.basesyntax;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriter {
    public void write(List<String> data, String filePath) throws AccessDeniedException {
        try {
            Files.write(Path.of(filePath), data);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }
}
