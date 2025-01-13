package core.basesyntax;

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
    private static final String TEMP_FILE_NAME = "test_report.csv";
    private static core.basesyntax.service.FileWriter fileWriter;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new CsvFileWriterImpl();
        tempFile = new File(TEMP_FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String[] lines = {
                    "fruit,quantity",
                    "banana,20",
                    "apple,50"
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
    void write_Ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity").append(System.lineSeparator());
        builder.append("banana,152").append(System.lineSeparator());
        builder.append("apple,110").append(System.lineSeparator());
        String expectedResult = builder.toString();

        fileWriter.write(expectedResult, TEMP_FILE_NAME);

        StringBuilder actualContentBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(Path.of(TEMP_FILE_NAME))) {
            while (scanner.hasNextLine()) {
                actualContentBuilder.append(scanner.nextLine())
                        .append(System.lineSeparator());
            }
        }

        String actualContent = actualContentBuilder.toString();
        Assertions.assertEquals(expectedResult, actualContent);
    }

    @Test
    void write_InvalidPath_ThrowsException() {
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity").append(System.lineSeparator());
        builder.append("banana,152").append(System.lineSeparator());
        String data = builder.toString();
        String invalidPath = "invalid_path/test_output.csv";

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            fileWriter.write(data, invalidPath);
        });

        String expectedMessage = "Error writing to file: " + invalidPath;

        Assertions.assertEquals(exception.getMessage(), expectedMessage);
    }
}
