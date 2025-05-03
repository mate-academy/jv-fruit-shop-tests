package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReaderImplTest {
    private static final String PATH = "src/test/java/testResources/testFile.csv";
    private static final String emptyFilePath = "src/test/java/testResources/emptyFile.csv";
    private CsvReaderImpl csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void readFile_FileIsNotCorrect_NotOk() {
        String filePath = "randomName.txt";
        assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(filePath);
        });
    }

    @Test
    void readFile_FileIsEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(emptyFilePath);
        });
    }

    @Test
    void readFile_checkOutput_IsOk() throws IOException {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator();
        assertEquals(expected, csvReader.readFile(PATH));
    }
}
