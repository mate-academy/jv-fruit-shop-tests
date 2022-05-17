package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class WriterServiceImplTest {
    private final WriterService writerService = new WriterServiceImpl();

    @Test
    public void writeToFile_Ok() throws IOException {
        String filePath = "src/main/resources/test_report.csv";
        StringBuilder stringBuilderExpected = new StringBuilder();
        stringBuilderExpected.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,90");
        String expectedReport = stringBuilderExpected.toString();
        writerService.writeToFile(filePath, expectedReport);

        StringBuilder stringBuilderActual = new StringBuilder();
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String st;
        while ((st = bufferedReader.readLine()) != null) {
            stringBuilderActual.append(st).append(System.lineSeparator());
        }
        String actualReport = stringBuilderActual.substring(0, stringBuilderActual
                .lastIndexOf(System.lineSeparator()));

        assertEquals(expectedReport, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullFileName_NotOk() {
        writerService.writeToFile(null, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        writerService.writeToFile("src/resources/test_report.csv", "");
    }
}
