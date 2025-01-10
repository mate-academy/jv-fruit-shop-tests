package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NotCsvFileException;
import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter = new FileWriterImpl();
    private static final String OUTPUT_PATH = "src/main/resources/testReport.csv";

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
        String report = "fruit,quantity\nbanana,107\napple,110\n";
        fileWriter.write(report, OUTPUT_PATH);
        String actualResult;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_PATH))) {
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
            fileWriter.write(report, "notCsv.txt");
        });
    }
}
