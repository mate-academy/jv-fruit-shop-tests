package core.basesyntax.service.impl;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.ReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadServiceImpl implements ReadService {
    @Override
    public List<String> readInputData(String inputFilePath) {
        try {
            return Files.readAllLines(Path.of(inputFilePath));
        } catch (IOException e) {
            throw new FruitShopException("Can't read this file " + inputFilePath);
        }

    }
}
