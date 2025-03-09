package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.impl.FileReaderImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String REPORT_TO_READ = "src/main/resources/input.csv";
    private static final String EMPTY_REPORT = "src/test/resources/emptyReport.csv";
    private static final String CHAR_REPORT = "src/test/resources/reportWithSpecialChars.csv";
    private static FileReaderImpl fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validReport_Ok() {
        List<String> read = fileReader.read(REPORT_TO_READ);
        assertNotNull(read);
        assertFalse(read.isEmpty());

        List<String> expectedContent = Arrays.asList(
                "type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50"
        );
        assertEquals(expectedContent, read);
    }

    @Test
    void read_nonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read("randomFile"));
        List<String> read = fileReader.read(EMPTY_REPORT);
        assertTrue(read.isEmpty());
        assertEquals(0, read.size());
    }

    @Test
    void read_FileWithSpecialCharacters_Ok() {
        List<String> read = fileReader.read(CHAR_REPORT);
        assertNotNull(read);
        assertFalse(read.isEmpty());

        String expectedContent = "!@#$%^&|!@&#!*_)!*(^(^+!+(%";
        String actualContent = String.join("", read);
        assertEquals(expectedContent, actualContent);
    }
}
