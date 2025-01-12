package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.NotCsvFileException;
import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter = new FileWriterImpl();
    private static final String OUTPUT_PATH = "src/test/resources/testReport.csv";

    @BeforeAll
    static void beforeAll() {
        File outpurFile = new File(OUTPUT_PATH);
        if (!outpurFile.exists()) {
            try {
                outpurFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void writeReportToFile_Ok() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,107"
                + System.lineSeparator()
                + "apple,110"
                + System.lineSeparator();
        fileWriter.write(report, OUTPUT_PATH);
        String actualResult;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_PATH))) {
            actualResult = bufferedReader.lines().collect(Collectors.joining(
                    System.lineSeparator()
            )) + System.lineSeparator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(report, actualResult);
    }

    @Test
    void writeToNonCsvFileTest_NotOk() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,107"
                + System.lineSeparator()
                + "apple,110"
                + System.lineSeparator();
        assertThrows(NotCsvFileException.class, () -> {
            fileWriter.write(report, "notCsv.txt");
        });
    }
}
