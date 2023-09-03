package core.basesyntax.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReadServiceImpl implements FileReadService {
    @Override
    public List<String> readDataFromFile(String file) {
        File inputtFile = new File(file);
        List<String> report;
        try {
            report = Files.readAllLines(inputtFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + file, e);
        }
        return report;
    }
}
