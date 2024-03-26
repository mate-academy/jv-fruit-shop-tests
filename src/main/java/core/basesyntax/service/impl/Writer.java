package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public void writeInFile(String toFileName, String report) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new InvalidDataException("Can't write info");
        }
    }
}
