package core.basesyntax.service.impl;

import core.basesyntax.service.CsvWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvWriterImpl implements CsvWriter {
    @Override
    public void write(String text, String filePath) {
        try {
            Files.write(Paths.get(filePath + ".csv"), text.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to write report",e);
        }
    }
}
