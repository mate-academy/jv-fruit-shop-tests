package core.basesyntax.service.read.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.read.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String DIRECTORY_PATH = "src/test/resources/";
    private static final String EMPTY_FILENAME = "empty.csv";
    private static final String NO_VALID_FILENAME = "no_valid.csv";
    private static final String LINES_1 = "type,fruit,quantity";
    private static final String LINES_2 = "b,banana,20";
    private static final String LINES_3 = "b,apple,100";
    private static final String LINES_4 = "s,banana,103";
    private static final String LINES_5 = "p,banana,13";
    private static final String LINES_6 = "r,apple,10";
    private static final String VALID_FILENAME = "valid.csv";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFromFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add(LINES_1);
        expected.add(LINES_2);
        expected.add(LINES_3);
        expected.add(LINES_4);
        expected.add(LINES_5);
        expected.add(LINES_6);
        List<String> actual = fileReader.readFromFile(DIRECTORY_PATH, VALID_FILENAME);
        assertEquals(expected, actual);

    }

    @Test
    public void readFromFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(DIRECTORY_PATH, NO_VALID_FILENAME));

    }

    @Test
    public void readFromFile_empty_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(DIRECTORY_PATH, EMPTY_FILENAME));
    }
}
