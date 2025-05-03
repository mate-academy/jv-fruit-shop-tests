package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/input.csv";
    private static final String NON_EXISTENT_FILE_PATH =
            "src/test/resources/non_existent_file.csv";
    private static final String TEST_DATA = "apple,b,10\norange,s,15";
    private static final String FIRST_EXPECTED_DATA = "apple,b,10";
    private static final int INDEX_OF_FIRST_EXPECTED_DATA = 0;
    private static final int EXPECTED_SIZE = 2;

    private CsvReaderServiceImpl csvReaderService;

    @BeforeEach
    public void setUp() {
        Storage.inputData.clear();
        csvReaderService = new CsvReaderServiceImpl();
    }

    @Test
    public void readFromFile_successReading_ok() throws IOException {
        String filePath = FILE_PATH;
        createTestFile(filePath, TEST_DATA);
        List<String> result = csvReaderService.readFromFile(filePath);
        assertEquals(EXPECTED_SIZE, result.size());
        assertEquals(FIRST_EXPECTED_DATA, result.get(INDEX_OF_FIRST_EXPECTED_DATA));
    }

    @Test
    public void readFromFile_fileNotFound_exceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> csvReaderService.readFromFile(NON_EXISTENT_FILE_PATH));
    }

    private void createTestFile(String filePath, String data) throws IOException {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        }
    }
}
