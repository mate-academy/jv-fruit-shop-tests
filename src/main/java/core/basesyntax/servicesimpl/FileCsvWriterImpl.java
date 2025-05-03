package core.basesyntax.servicesimpl;

import core.basesyntax.services.FileCsvWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileCsvWriterImpl implements FileCsvWriter {
    public void writeInFile(String message, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(message);
        } catch (IOException e) {
            throw new RuntimeException("Can not write in file " + filePath);
        }
    }
}
