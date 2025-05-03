package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileWriterImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterImplTest {
    private static final String TEMP_PATH = "src/test/resources/test_report.csv";
    private static final String WRONG_PATH = "invalid_path/test.csv";
    private static final String HEADER = "fruit,quantity";
    private static final String FIRST_ENTRY = "banana,20";
    private static final String SECOND_ENTRY = "apple,50";

    private CsvFileWriter fileWriter;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new CsvFileWriterImpl();
        tempFile = new File(TEMP_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String[] lines = {
                    HEADER,
                    FIRST_ENTRY,
                    SECOND_ENTRY
            };
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void write_validPath_Ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(HEADER).append(System.lineSeparator());
        builder.append(FIRST_ENTRY).append(System.lineSeparator());
        builder.append(SECOND_ENTRY).append(System.lineSeparator());
        String expectedResult = builder.toString();

        fileWriter.write(expectedResult, TEMP_PATH);

        StringBuilder actualContentBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(Path.of(TEMP_PATH))) {
            while (scanner.hasNextLine()) {
                actualContentBuilder.append(scanner.nextLine())
                        .append(System.lineSeparator());
            }
        }
        String actualContent = actualContentBuilder.toString();
        Assertions.assertEquals(expectedResult, actualContent);
    }

    @Test
    void write_invalidPath_NotOk() {
        StringBuilder builder = new StringBuilder();
        builder.append(HEADER).append(System.lineSeparator());
        builder.append(FIRST_ENTRY).append(System.lineSeparator());
        String data = builder.toString();
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fileWriter.write(data, WRONG_PATH);
        });
        String expectedMessage = "Error writing to file: " + WRONG_PATH;
        Assertions.assertEquals(exception.getMessage(), expectedMessage);
    }
}
