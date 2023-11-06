package core.basesyntax.service.impl;

import core.basesyntax.service.DataWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CsvDataWriter implements DataWriter {
    private static final String EXCEPTION_GENERAL_MESSAGE = "Can't write to the file ";

    public void writeToFile(String destinationFileName, String reportData) {
        if (reportData.isEmpty()) {
            throw new RuntimeException(EXCEPTION_GENERAL_MESSAGE + "empty report data!");
        }
        File destinationfile = new File(destinationFileName);
        try {
            Files.write(destinationfile.toPath(), reportData.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_GENERAL_MESSAGE + destinationFileName, e);
        }
    }
}
