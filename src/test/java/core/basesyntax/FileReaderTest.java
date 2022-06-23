package core.basesyntax;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.opencsv.exceptions.CsvException;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String VALID_FILE = "src/test/resources/input.csv";
    private static final String INVALID_FILE_HEAD = "src/test/resources/input2.csv";
    private static final String WRONG_FILE = "src/test/resources/wrong.csv";
    private static final String ABSENT_FILE = "src/test/resources/input23.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeAll() {
        fileReaderService = new FileReaderImpl();
    }

    @Test
    public void readRightFile_OK() throws IOException, CsvException {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"b", "banana", "20"});
        expected.add(new String[]{"b", "apple", "140"});
        expected.add(new String[]{"s", "banana", "100"});
        expected.add(new String[]{"p", "banana", "50"});
        expected.add(new String[]{"r", "apple", "10"});
        expected.add(new String[]{"p", "apple", "30"});
        expected.add(new String[]{"p", "banana", "15"});
        expected.add(new String[]{"s", "banana", "50"});

        List<String[]> actual = fileReaderService.read(VALID_FILE);

        for (int i = 0; i < expected.size(); i++) {
            String[] fromActual = actual.get(i);
            String[] fromExpected = expected.get(i);
            for (int j = 0; j < fromActual.length; j++) {
                assertTrue(fromExpected[j].equals(fromActual[j]));
            }
        }
    }

    @Test
    public void readWrongLineSize_notOk() throws IOException, CsvException {
        List<String[]> actual = fileReaderService.read(WRONG_FILE);
        assertFalse(actual.stream().allMatch(b -> b.length == 3));
    }

    @Test
    public void readAbsentFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.read(ABSENT_FILE));
    }

    @Test
    public void readFileWithWrongHead_Ok() throws IOException {
        String[] expectedHead = new String[]{"type", "fruit", "quantity"};
        List<String> lines = Files.readAllLines(Path.of(VALID_FILE));
        String[] gotHead = lines.get(0).split(",");
        for (int i = 0; i < expectedHead.length; i++) {
            assertTrue("the head of file is wrong in cell: "
                    + i, expectedHead[i].equals(gotHead[i]));
        }
    }

    @Test
    public void readFileWithWrongHead_notOk() throws IOException {
        String[] expectedHead = new String[]{"type", "fruit", "quantity"};
        List<String> lines = Files.readAllLines(Path.of(VALID_FILE));
        String[] gotHead = lines.get(0).split(",");
        for (int i = 0; i < expectedHead.length; i++) {
            if (!expectedHead[i].equals(gotHead[i])) {
                assertNotEquals("the head of file is wrong in cell: "
                        + i, expectedHead[i], gotHead[i]);
            }
        }
    }
}
