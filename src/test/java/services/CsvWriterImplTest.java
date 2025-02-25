package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.CsvWriterImpl;
import core.basesyntax.service.CsvFileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterImplTest {
    public static final String VALID_FILE_NAME = "src/test/java/resources/WriteFileTest.csv";
    public static final String INVALID_FILE_NAME = "";
    public static final String REPORT = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20";
    private CsvFileWriter csvFileWriter;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CsvWriterImpl();
    }

    @Test
    void testWrite_Ok() throws IOException {
        File file = new File(VALID_FILE_NAME);
        csvFileWriter.writeFile(file.getAbsolutePath(), REPORT);
        String actualContent = Files.readString(file.toPath());
        assertEquals(REPORT, actualContent);
    }

    @Test
    void testWrite_invalidPath() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> csvFileWriter.writeFile(INVALID_FILE_NAME, REPORT));
        assertEquals("Can't write report to file ", runtimeException.getMessage());
    }
}
