package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import core.basesyntax.service.impl.ReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderImplTest {
    private static final String FILE_PATHNAME = "src/test/resources/fileone.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String NOT_EXISTING_FILE = "src/test/resources/noFile.csv";

    private void fillExpectedList(List<String> expectedList) {
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,10");
        expectedList.add("s,apple,20");
        expectedList.add("r,banana,40");
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        fillExpectedList(expectedList);
        List<String> actualList = new ReaderImpl().readFromFile(FILE_PATHNAME);
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_notExistingFile_NotOk() {
        Reader reader = new ReaderImpl();
        reader.readFromFile(NOT_EXISTING_FILE);
    }

    @Test(expected = NullPointerException.class)
    public void readFrom_nullPathname_NotOk() {
        Reader reader = new ReaderImpl();
        reader.readFromFile(null);
    }

    @Test
    public void readFrom_emptyFile_Ok() {
        Reader reader = new ReaderImpl();
        List<String> actualList = reader.readFromFile(EMPTY_FILE);
        assertEquals(0, actualList.size());
    }
}
