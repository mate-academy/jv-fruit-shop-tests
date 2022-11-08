package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriteData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.Test;

public class WriteDataImplTest {
    private static final String FILE_PATH = "src/test/resources/report.csv";
    private String report = "fruit,quantity"
            + System.lineSeparator()
            + "banana,500"
            + System.lineSeparator()
            + "apple,18";
    private WriteData writeData = new WriteDataImpl();

    @Test
    public void writeDataToFile_Ok() {
        String exceptedReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,500"
                + System.lineSeparator()
                + "apple,18";
        writeData.writeToFile(report, FILE_PATH);
        String actualReport = null;
        try {
            actualReport = Files.readAllLines(Paths.get(FILE_PATH))
                    .stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(exceptedReport,actualReport);
    }

    @Test
    public void writeDataToFile_nullPath_notOk() {
        String filePath = null;
        assertThrows(RuntimeException.class, () ->
                writeData.writeToFile(filePath, report), "IncorrectPath" + filePath);
    }
}
