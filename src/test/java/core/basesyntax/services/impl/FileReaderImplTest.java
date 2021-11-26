package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileReader;

import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_validData_Ok() {
        List<String> actual = fileReader.read("src/test/resources/valid_file.csv");
        List<String> expected = List.of("line 1", "line 2", "line 3", "line 4");
        assertEquals(expected, actual);
    }

    @Test
    public void read_fileWithOneLine_Ok() {
        List<String> actual = fileReader.read("src/test/resources/oneLine_file.csv");
        List<String> expected = List.of("line 1");
        assertEquals(expected, actual);
    }

    @Test
    public void read_EmptyFile_Ok() {
        List<String> actual = fileReader.read("src/test/resources/empty_file.csv");
        List<String> expected = Collections.emptyList();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentFile_NotOk() {
        fileReader.read("src/test/resources/non-existent.csv");
    }

    @Test(expected = RuntimeException.class)
    public void read_nullPath_NotOk() {
        fileReader.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyPath_NotOk() {
        fileReader.read("");
    }
}
