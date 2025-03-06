package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.CsvReaderImpl;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReaderImplTest {
    private CsvReaderImpl csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void file_isNull_NotOk() {
        String filePath = null;
        assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(filePath);
        });
    }

    @Test
    void file_checkOutput_IsOk() throws IOException {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator();
        System.out.println(csvReader.readFile("src/test/java/testResources/testFile.csv"));
        assertEquals(expected, csvReader.readFile("src/test/java/testResources/testFile.csv"));
    }
}
