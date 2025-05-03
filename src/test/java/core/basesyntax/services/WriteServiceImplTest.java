package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriteServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteServiceImplTest {
    private static final String PATH_TO_FILE = "src/test/java/resources/report.csv";
    private static final String INVALID_PATH = "invalid/path/to/file";
    private static WriteServiceImpl writeService;

    @BeforeAll
    public static void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    public void writeToFile_Ok() throws IOException {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();
        writeService.writeToFile(PATH_TO_FILE, expectedReport);
        List<String> expectedLines = Arrays.asList(expectedReport.split(System.lineSeparator()));
        List<String> actualLines = Files.readAllLines(Path.of(PATH_TO_FILE));
        assertEquals(expectedLines.size(), expectedLines.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), actualLines.get(i));
        }
    }

    @Test
    public void writeToFile_InvalidPath_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "apple,20"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator()
                + "orange,30"
                + System.lineSeparator();
        assertThrows(RuntimeException.class, ()
                -> writeService.writeToFile(INVALID_PATH, report));
    }

    @Test
    public void writeToFile_NullReport_notOk() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> writeService.writeToFile(PATH_TO_FILE, null));
        assertEquals("Report cannot be null", exception.getMessage());
    }
}
