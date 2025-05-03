package core.basesyntax.service.impl;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteServiceImpl implements WriterService {
    @Override
    public String writeToFile(String filePath, String report) {
        try {
            Files.writeString(Path.of(filePath), report);
        } catch (IOException e) {
            throw new FruitShopException("Can't write to file " + filePath);
        }
        return report;
    }
}
