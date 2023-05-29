package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {
    @Override
    public void writeReportToCsvFile(String report, String pathToFile) {
        if (pathToFile == null) {
            throw new RuntimeException("File path cannot be null");
        }
        File file = new File(pathToFile);
        if (!file.exists()) {
            throw new RuntimeException("The file does not exist" + pathToFile);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            writer.write(report);
        } catch (IOException exception) {
            throw new RuntimeException("Can't find file by path: " + pathToFile);
        }
    }
}
