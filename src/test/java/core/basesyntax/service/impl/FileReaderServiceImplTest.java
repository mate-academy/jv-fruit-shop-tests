package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/test_file.csv";
    private static final String FAKE_FILE_PATH = "src/test/test_file.csv";
    private final List<String> expectedFileContent = List.of(
            "banana,5",
            "apple,4",
            "orange,3"
    );
    private FileReaderService fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    void read_Ok() {
        List<String> actualFileContent = fileReader.read(FILE_PATH);
        assertEquals(expectedFileContent, actualFileContent);
    }

    @Test
    void read_invalidPath_notOk() {
        assertThrows(FruitShopException.class,
                () -> fileReader.read(FAKE_FILE_PATH));
    }
}
