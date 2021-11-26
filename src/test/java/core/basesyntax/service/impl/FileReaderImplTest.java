package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String INPUT_PATH = "src/test/resources/testfruits-log.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty_fileTest.csv";
    private static final String INVALID_PATH_EXAMPLE = "123123123123asd";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_pathIsValid_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,apple,120",
                "s,banana,50",
                "r,apple,10");
        assertEquals(expected, fileReader.read(INPUT_PATH));
    }

    @Test (expected = RuntimeException.class)
    public void read_pathsIsValid_notOk() {
        fileReader.read(INVALID_PATH_EXAMPLE);
    }

    @Test (expected = RuntimeException.class)
    public void read_pathIsNull_notOk() {
        fileReader.read(null);
    }

    @Test (expected = RuntimeException.class)
    public void read_pathIsEmpty_ok() {
        fileReader.read("");
    }

    @Test
    public void read_fileIsEmpty_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertEquals(expected, actual);
    }
}
