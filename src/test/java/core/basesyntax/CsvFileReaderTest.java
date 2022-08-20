package core.basesyntax;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Reader;
import core.basesyntax.service.impl.CsvFileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CsvFileReaderTest {
    private final String fromFilePath = "src/test/resources/test-database.csv";
    private final Reader fileReader = new CsvFileReader();

    @Test
    public void fileExists_Ok() {
        File file = new File(fromFilePath);
        boolean exists = file.exists();
        assertTrue(exists);
    }

    @Test
    public void cantReadFile_NotOk() {
        try {
            fileReader.read("");
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void fileNotNull_Ok() {
        List<String[]> actual = fileReader.read(fromFilePath);
        assertNotNull(actual);
    }

    @Test
    public void fileNotEmpty_Ok() {
        List<String[]> lines = fileReader.read(fromFilePath);
        boolean isEmpty = lines.isEmpty();
        assertFalse(isEmpty);
    }

    @Test
    public void fileContentEquals_Ok() {
        List<String[]> expected = new ArrayList<>();
        expected.add("b,banana,20".split(","));
        expected.add("b,apple,100".split(","));
        expected.add("s,banana,100".split(","));
        expected.add("p,banana,13".split(","));
        expected.add("r,apple,10".split(","));
        expected.add("p,apple,20".split(","));
        expected.add("p,banana,5".split(","));
        expected.add("s,banana,50".split(","));
        List<String[]> actual = fileReader.read(fromFilePath);
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), actual.get(i));
        }
    }
}
