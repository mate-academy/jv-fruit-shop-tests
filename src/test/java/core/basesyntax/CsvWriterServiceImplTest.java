package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.impl.CsvWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvWriterServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/report.csv";
    private static final String NON_EXISTENT_PATH =
            "src/test/resources/non_existent_directory/output.csv";
    private static final String OUTPUT_DATA = "fruit,quantity\napple,10\nbanana,15";
    private static final String TEST_DATA = "fruit,quantity\napple,10\nbanana,15";
    private CsvWriterServiceImpl csvWriterService;

    @BeforeEach
    public void setUp() {
        csvWriterService = new CsvWriterServiceImpl();
    }

    @Test
    public void testWriteToFile_Success() throws IOException {
        csvWriterService.writeToFile(FILE_PATH, TEST_DATA);
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        assertEquals(TEST_DATA, content);
    }

    @Test
    public void testWriteToFile_Exception() {
        assertThrows(RuntimeException.class,
                () -> csvWriterService.writeToFile(NON_EXISTENT_PATH, OUTPUT_DATA));
    }
}
