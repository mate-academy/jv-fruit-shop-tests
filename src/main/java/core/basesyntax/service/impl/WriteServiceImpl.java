package core.basesyntax.service.impl;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteServiceImpl implements WriteService {
    @Override
    public String writeReport(String report, String reportFilePath) {
        try {
            Files.writeString(Path.of(reportFilePath), report);
        } catch (IOException e) {
            throw new FruitShopException("Can`t write report. File not found");
        }
        return report;
    }
}
