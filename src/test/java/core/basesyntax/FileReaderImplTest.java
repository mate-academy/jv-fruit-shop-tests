package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String TEST_FILE = "src/test/resources/test_input.csv";

    @BeforeAll
    static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_validFile_ok() {
        List<String> result = fileReader.read(TEST_FILE);

        assertNotNull(result, "Result cant be null");
        assertEquals(3, result.size(), "Result should contain 3 lines");
        assertEquals("fruit,quantity", result.get(0));
        assertEquals("banana,20", result.get(1));
        assertEquals("apple,50", result.get(2));
    }
}
