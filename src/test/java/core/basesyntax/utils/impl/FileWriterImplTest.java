package core.basesyntax.utils.impl;

import core.basesyntax.exceptions.NotCsvFileException;
import core.basesyntax.utils.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter = new FileWriterImpl();

    @Test
    void writeReportToFile_Ok() {
        String report = "fruit,quantity\nbanana,107\napple,110\n";
        fileWriter.write(report, "testReport.csv");
        String actualResult;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("testReport.csv"))) {
            actualResult = bufferedReader.lines().collect(Collectors.joining("\n")) + "\n";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(report, actualResult);
    }

    @Test
    void writeToNonCsvFileTest_NotOk() {
        String report = "fruit,quantity\nbanana,107\napple,110\n";
        Assertions.assertThrows(NotCsvFileException.class, () -> {
            fileWriter.write(report, "testReport.txt");
        });
    }
}
