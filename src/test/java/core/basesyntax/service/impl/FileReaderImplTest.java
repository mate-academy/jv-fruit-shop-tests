package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final FileReader reader = new FileReaderImpl();
    private static final String EXISTING_FILE_PATH = "src/test/resources/inputTest.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/noSuchFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";

    @Test
    public void readFromFile_ExistingFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "s,apple,15");
        assertEquals(expected, reader.readFromFile(EXISTING_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NonExistingFile_notOk() {
        reader.readFromFile(NON_EXISTING_FILE_PATH);
    }

    @Test
    public void readFromFile_EmptyFile() {
        List<String> actual = reader.readFromFile(EMPTY_FILE_PATH);
        assertTrue(actual.isEmpty());
    }
}
