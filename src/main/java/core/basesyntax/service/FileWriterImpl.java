package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String toWriteFile, String toWriteData) {
        File fileToWrite = new File(toWriteFile);
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(fileToWrite, false));
            writer.write(toWriteData);
            writer.close();
        } catch (IOException exc) {
            throw new ValidationException("Can't write to file: " + fileToWrite);
        }
    }
}
