package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReaderImplTest {
    private static final String PATH = "src/test/java/testResources/testFile.csv";
    private CsvReaderImpl csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void file_IsNotCorrect_NotOk() {
        String filePath = "randomName.txt";
        assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(filePath);
        });
    }

    @Test
    void file_IsEmpty_NotOk() {
        String filePath = "src/test/java/testResources/emptyFile.csv";
        assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(filePath);
        });
    }

    @Test
    void file_checkOutput_IsOk() throws IOException {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator();
        assertEquals(expected, csvReader.readFile(PATH));
    }
}
