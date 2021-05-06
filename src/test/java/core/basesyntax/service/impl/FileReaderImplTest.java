package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_FILE_SOURCE = "src/test/resources/test_file_reader.csv";
    private static final String INVALID_FILE_SOURCE = "src/resources/test_file_reader.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFileWithValidFileSource_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit ,quantity");
        expected.add("b   ,banana,120");
        expected.add("b   ,apple ,100");
        List<String> actual = fileReader.readFile(VALID_FILE_SOURCE);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFileWithInvalidFileSource_NotOk() {
        fileReader.readFile(INVALID_FILE_SOURCE);
    }
}
