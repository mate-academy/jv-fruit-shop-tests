package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFromFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFromFileImpl implements ReadFromFile {
    @Override
     public List<String> readFormFile(String filePath) {
        if (filePath == null) {
            throw new RuntimeException("No path has been put");
        }
        try {
            List<String> activitiesOfDay = Files.readAllLines(Paths.get(filePath));
            return activitiesOfDay;
        } catch (IOException e) {
            throw new RuntimeException("No such file found!");
        }
    }
}
