package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CsvFileWriterImpl implements CsvFileWriter {

    @Override
    public boolean write(List<String> generatedFruitReport, String pathname) {
        generatedFruitReport.forEach(x -> fileWriteSupplier(Path.of(pathname), x));
        return true;
    }

    private void fileWriteSupplier(Path pathToFile, String x) {
        try {
            Files.write(pathToFile, (x + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file");
        }
    }
}
