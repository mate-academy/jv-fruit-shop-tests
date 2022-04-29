package core.basesyntax.service.read;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;
    private static final String VALID_FILE = "src/test/resources/testFile.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_emptyFile_Ok() {
        List<String> actual = readerService.readFile(EMPTY_FILE);
        List<String> expected = Collections.emptyList();
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_emptyPath_NotOk() {
        readerService.readFile("");
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_nullFilePath_NotOk() {
        readerService.readFile(null);
    }

    @Test
    public void readFromFile_validCase_Ok() {
        write(VALID_FILE,
                "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,18" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,14" + System.lineSeparator()
                + "p,banana,15" + System.lineSeparator()
                + "r,apple,100000" + System.lineSeparator()
                + "p,apple,50" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,544");
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,18",
                "b,apple,100",
                "s,banana,14",
                "p,banana,15",
                "r,apple,100000",
                "p,apple,50",
                "p,banana,5",
                "s,banana,544");
        List<String> actual = readerService.readFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    private void write(String fileName, String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + fileName, e);
        }
    }
}
