package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.Test;

public class WriterServiceImplTest {
    private final WriterService writerService = new WriterServiceImpl();

    @Test
    public void writeToFile_Ok() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,90");
        String report = stringBuilder.toString();
        writerService.writeToFile("src/main/resources/test_report.csv", report);
    }
}
