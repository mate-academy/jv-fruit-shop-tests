package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderCsvImplTest {
    private static final String RESOURCES_DIRECTORY = "src/test/resources/";
    private static final String EMPTY_FILE_PATHNAME = RESOURCES_DIRECTORY + "emptyFile.csv";
    private static final String NOT_EXISTING_FILE_PATHNAME = RESOURCES_DIRECTORY
            + "notExistingFile.csv";
    private static final String OK_FILE_PATHNAME = RESOURCES_DIRECTORY + "fileOk.csv";

    @Test
    public void readFromFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        fillExpectedList(expectedList);
        Reader reader = new ReaderCsvImpl();
        List<String> actualList = reader.readFromFile(OK_FILE_PATHNAME);
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistingFile_NotOk() {
        Reader reader = new ReaderCsvImpl();
        reader.readFromFile(NOT_EXISTING_FILE_PATHNAME);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_nullFilePathname_NotOk() {
        Reader reader = new ReaderCsvImpl();
        reader.readFromFile(null);
    }

    @Test
    public void readFromFile_emptyFile_Ok() {
        Reader reader = new ReaderCsvImpl();
        List<String> actualList = reader.readFromFile(EMPTY_FILE_PATHNAME);
        assertEquals(0, actualList.size());
    }

    private void fillExpectedList(List<String> expectedList) {
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
    }
}
