package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ReportFileTest {
    private final ReportFile reportFile = new ReportFile();

    @Test
    public void writeDataToReportFile_Ok() {
        String actualResult = readFromFile().trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,142" + System.lineSeparator() + "apple,90";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void madeReportFile_NotOk() {
        String path = "";
        Assertions.assertThrows(RuntimeException.class, () -> {
            reportFile.madeReportFile(path);
        });
    }

    private String readFromFile() {
        try {
            return Files.readString(Path.of("report.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + "report.csv.", e);
        }
    }
}
