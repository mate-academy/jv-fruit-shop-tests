package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderCsvImplTest {
    private static final String RESOURCES_DIRECTORY = "src/test/resources/";
    private static final String EMPTY_FILE_PATHNAME = RESOURCES_DIRECTORY + "emptyFile.csv";
    private static final String NOT_EXISTING_FILE_PATHNAME = RESOURCES_DIRECTORY
            + "notExistingFile.csv";
    private static final String OK_FILE_PATHNAME = RESOURCES_DIRECTORY + "fileOk.csv";
    private static Reader reader;

    @Before
    public void setUp() throws Exception {
        reader = new ReaderCsvImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
        List<String> actualList = reader.readFromFile(OK_FILE_PATHNAME);
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void readFromFile_notExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(NOT_EXISTING_FILE_PATHNAME));
    }

    @Test
    public void readFromFile_nullFilePathname_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(null));
    }

    @Test
    public void readFromFile_emptyFile_Ok() {
        List<String> actualList = reader.readFromFile(EMPTY_FILE_PATHNAME);
        assertEquals(0, actualList.size());
    }
}
