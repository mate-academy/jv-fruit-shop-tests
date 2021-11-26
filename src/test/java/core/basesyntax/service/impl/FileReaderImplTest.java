package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String INPUT_PATH = "src/test/java/testresources/testfruits-log.csv";
    private static final String EMPTY_FILE = "src/test/java/testresources/empty_fileTest.csv";
    private static final String INVALID_PATH_EXAMPLE = "123123123123asd";
    private static FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void file_path_isValid_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,apple,120",
                "s,banana,50",
                "r,apple,10");
        assertEquals(expected, fileReader.read(INPUT_PATH));
    }

    @Test (expected = RuntimeException.class)
    public void file_path_isNotValid_notOk() {
        fileReader.read(INVALID_PATH_EXAMPLE);
    }

    @Test (expected = RuntimeException.class)
    public void file_path_isNull_notOk() {
        fileReader.read(null);
    }

    @Test (expected = RuntimeException.class)
    public void file_path_isEmpty_ok() {
        fileReader.read("");
    }

    @Test
    public void file_isEmpty_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertEquals(expected, actual);
    }
}
