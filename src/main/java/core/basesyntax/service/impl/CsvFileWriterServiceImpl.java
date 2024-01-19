package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class CsvFileWriterServiceImpl implements FileWriterService {
    private static final String SEPARATOR = System.lineSeparator();
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String write(File fileName, Map<String, Integer> fruitAndQuantityMap) {
        StringBuilder report = new StringBuilder(REPORT_HEADER + SEPARATOR);
        fruitAndQuantityMap.forEach((key, value) -> report.append(key)
                .append(COMMA)
                .append(value)
                .append(SEPARATOR));
        try {
            Files.writeString(fileName.toPath(), report.toString());
        } catch (IOException e) {
            throw new RuntimeException(
                    "non-valid input parameter " + report);
        }
        return report.toString();
    }
}
