package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {
    private static final String FORMAT_OF_FILE = "csv";
    private static final int FORMAT_OF_FILE_INDEX = 1;

    @Override
    public void writeToFile(String report, String filePath) {
        String formatOfInputFile = filePath.split("\\.")[FORMAT_OF_FILE_INDEX];
        if (!formatOfInputFile.equals(FORMAT_OF_FILE)) {
            throw new RuntimeException("Can not write into " + formatOfInputFile + " format");
        }
        File file = new File(filePath);
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(file))) {
            buff.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can not write data to " + filePath);
        }
    }
}
