package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.CsvFileReaderImpl;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String FILE_PATH = "src/test/resources/reportToRead.csv";
    private static final String FILE_CONTENT = "type,fruit,quantity\n" + "b,banana,20";
    private static FileReader reader;
    private File file;

    @BeforeAll
    public static void setUp() {
        reader = new CsvFileReaderImpl();
    }

    @BeforeEach
    public void beforeEach() {
        file = new File(FILE_PATH);
        assertDoesNotThrow(() -> file.createNewFile());
    }

    @AfterEach
    public void afterEach() {
        file.delete();
    }

    @Test
    public void readAllLines_Null_NotOk() {
        assertThrows(NullPointerException.class, () -> reader.readAllLines(null));
    }

    @Test
    public void readAllLines_Empty_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.readAllLines(""));
    }

    @Test
    public void readAllLines_Empty_Ok() {
        List<String> actual = reader.readAllLines(file.getPath());
        System.out.println(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void readAllLines_Complete_Ok() {
        assertDoesNotThrow(() -> Files.write(file.toPath(), FILE_CONTENT.getBytes()));
        List<String> expected =
                Arrays.stream(FILE_CONTENT.split("\n")).collect(Collectors.toList());
        assertEquals(expected, reader.readAllLines(file.getPath()));
    }
}
