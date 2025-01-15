package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReaderImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final String TEMP_FILE_NAME = "src/test/resources/test_report.csv";
    private static final String WRONG_FILE_NAME = "invalid_path/test.csv";
    private static final String HEADER = "type,fruit,quantity";
    private static final String FIRST_ENTRY = "b,banana,30";
    private static final String SECOND_ENTRY = "b,apple,50";
    private static final String THIRD_ENTRY = "p,banana,10";

    private CsvFileReader fileReader;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = new File(TEMP_FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String[] lines = {
                    HEADER,
                    FIRST_ENTRY,
                    SECOND_ENTRY,
                    THIRD_ENTRY
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
    void getAll_validDataFile_Ok() {
        fileReader = new CsvFileReaderImpl(TEMP_FILE_NAME);
        List<String> actualTransactions = fileReader.getAll();
        List<String> expectedTransactions = List.of(
                FIRST_ENTRY,
                SECOND_ENTRY,
                THIRD_ENTRY
        );
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void getAll_wrongDataFile_NotOk() {
        fileReader = new CsvFileReaderImpl(WRONG_FILE_NAME);
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                fileReader::getAll);

        String expectedMessage = "Error reading file: " + WRONG_FILE_NAME;
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
