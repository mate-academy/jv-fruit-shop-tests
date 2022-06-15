package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriteServiceImplTest {
    private static final FileWriterService fileWriterService = new CsvFileWriteServiceImpl();
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "melon,20" + System.lineSeparator() + "orange,100";
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void writeToFile_nullReport_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Report cannot be null");
        fileWriterService.writeToFile(null, "src/test/resources/outputEmptyReport.csv");
    }

    @Test
    public void writeToFile_nullFilepath_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Filepath cannot be null");
        fileWriterService.writeToFile(report, null);
    }

    @Test
    public void writeToFile_validReport_Ok() {
        fileWriterService.writeToFile(report, "src/test/resources/outputValidReport.csv");
        String actualReport = readFromFile("src/test/resources/outputValidReport.csv");
        assertEquals(report, actualReport);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        String emptyReport = "";
        fileWriterService.writeToFile(emptyReport, "src/test/resources/outputEmptyReport.csv");
        String actualReport = readFromFile("src/test/resources/outputEmptyReport.csv");
        assertEquals(emptyReport, actualReport);
    }

    private String readFromFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read data from file: " + filePath, e);
        }
    }
}
