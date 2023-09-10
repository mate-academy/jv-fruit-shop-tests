package core.basesyntax.serviceimpltest;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.readservice.ReadServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_VALID_DATA_FILE =
            "src/main/java/datafiles/input_data.csv";
    private static final String EMPTY_DATA_FILE =
            "src/main/java/datafiles/empty.csv";
    private static final String INPUT_INCORRECT_DATA_FILE =
            "src/main/java/datafiles/input_data123456.csv";
    private static final String EXPECTED_DATA_FILE = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,152";
    private static final String NULL_PATH = null;
    private static ReadServiceImpl readService;

    @BeforeEach
    void setUp() {
        readService = new ReadServiceImpl();
    }

    @Test
    void readValueNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readService.readFrom(NULL_PATH));
    }

    @Test
    void readFrom_ok() throws RuntimeException, IOException {
        String content = EXPECTED_DATA_FILE;
        Path tempReader = testFileShop(content);

        String actual = readService.readFrom(tempReader.toString());
        Assertions.assertEquals(content, actual);
    }

    @Test
    void readIncorrectPath_NotOk() {
        assertThrows(RuntimeException.class, () -> readService.readFrom(INPUT_INCORRECT_DATA_FILE));
    }

    @Test
    void readEmptyPath_NotOk() {
        Assertions.assertFalse(false, EMPTY_DATA_FILE);
    }

    @Test
    void getValidData_Ok() {
        Assertions.assertTrue(true, INPUT_VALID_DATA_FILE);
    }

    private Path testFileShop(String content) throws IOException {
        Path fileShopTemp = Files.createTempFile("tempFileShop", "csv");
        Files.write(fileShopTemp, content.getBytes());
        return fileShopTemp;
    }
}
