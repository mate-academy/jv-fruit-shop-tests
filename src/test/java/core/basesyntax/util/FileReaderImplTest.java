package core.basesyntax.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    void fileReader() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void fileReaderIsNotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read("invalid/path/to/file.csv"));
    }

    @Test
    void fileReaderIsOk() {
        List<String> expectedLines = List.of(
                "type,fruit,quantity",
                "b,banana,150",
                "s,apple,100"
        );
        List<String> actualLines = fileReader.read("src/test/test_data.csv");

        assertEquals(expectedLines, actualLines);
    }

}

