package core.basesyntax;

import core.basesyntax.service.FileReader;
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
    private static final String TEMP_FILE_NAME = "test_transactions.csv";
    private static final String WRONG_FILE_NAME = "test.csv";
    private static FileReader fileReader;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = new File(TEMP_FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String[] lines = {
                    "type,fruit,quantity",
                    "b,banana,30",
                    "b,apple,50",
                    "p,banana,10"
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
    void getAll_ReturnTransactionsList_Ok() {
        fileReader = new CsvFileReaderImpl(TEMP_FILE_NAME);
        List<String> actualTransactions = fileReader.getAll();
        List<String> expectedTransactions = List.of(
                "b,banana,30",
                "b,apple,50",
                "p,banana,10"
        );
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void getAll_ReturnTransactionsList_NotOk() {
        fileReader = new CsvFileReaderImpl(WRONG_FILE_NAME);
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                fileReader::getAll);

        String expectedMessage = "Error reading file: " + WRONG_FILE_NAME;
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
