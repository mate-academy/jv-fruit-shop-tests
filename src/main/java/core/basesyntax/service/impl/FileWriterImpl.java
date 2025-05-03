package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String filePath, List<String> lines) {
        if (filePath == null) {
            throw new RuntimeException("Path to file can not be null!");
        }
        if (lines == null) {
            throw new RuntimeException("Error! Received data is null!");
        }
        if (lines.isEmpty()) {
            throw new RuntimeException("Nothing to write in report file! No data received!");
        }
        File toFile = new File(filePath);
        try (BufferedWriter bufferedWriter
                    = new BufferedWriter(new java.io.FileWriter(toFile, true))) {
            for (int i = 0; i < lines.size(); i++) {
                bufferedWriter.write(lines.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException("Write error to file " + filePath + "!", e);
        }
    }
}
