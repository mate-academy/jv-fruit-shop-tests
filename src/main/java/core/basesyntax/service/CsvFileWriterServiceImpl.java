package core.basesyntax.service;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.interfaces.CsvFileWriterService;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileWriterServiceImpl implements CsvFileWriterService {
    @Override
    public void writeInFile(String fileName, String message) {
        try {
            Files.writeString(Path.of(fileName), message);
        } catch (Exception e) {
            throw new FruitShopException("Can`t write in file: " + fileName);
        }
    }
}
