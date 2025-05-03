package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.NullFileException;
import core.basesyntax.exceptions.WriteDataException;
import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter implements FileWriterService {
    @Override
    public void writeToFile(File file, List<String> data) {
        if (file == null) {
            throw new NullFileException("Incoming file is null!");
        } else if (data == null) {
            throw new NullDataException("Incoming data is null!");
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (String value : data) {
                writer.write(value);
            }
        } catch (IOException e) {
            throw new WriteDataException("Can't write data to file " + file);
        }
    }
}
