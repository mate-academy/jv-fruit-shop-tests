package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CreateReportImpl;
import core.basesyntax.service.impl.FileWriterCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String FILE_PATH = "src/test/resources/exportFile.csv";
    private static final String INVALID_PATH_TO_FILE = "/error/exportFile.csv";
    private static final String HEADER_FRUIT = "fruitTransaction,";
    private static final String HEADER_QUANTITY = "quantity";
    private static final String BANANA_REPORT = "banana 152";
    private static final String APPLE_REPORT = "apple 90";
    private static CreateReportService createReport;
    private static FileWriterService fileWriterCsv;

    @BeforeClass
    public static void beforeClass() {
        createReport = new CreateReportImpl();
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        fileWriterCsv = new FileWriterCsvImpl(createReport);
    }

    @Test
    public void writeFile_validFilePath_ok() {
        fileWriterCsv.writeFile(FILE_PATH);
        String expectedString = generateExpectedString();
        try {
            String actualString = String.join(System.lineSeparator(),
                    Files.readAllLines(Path.of(FILE_PATH)));

            Assert.assertEquals(expectedString, actualString);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + FILE_PATH, e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_invalidFilePath_notOk() {
        fileWriterCsv.writeFile(INVALID_PATH_TO_FILE);
    }

    private String generateExpectedString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER_FRUIT).append(HEADER_QUANTITY).append(System.lineSeparator())
                .append(BANANA_REPORT).append(System.lineSeparator())
                .append(APPLE_REPORT);
        return stringBuilder.toString();
    }
}
