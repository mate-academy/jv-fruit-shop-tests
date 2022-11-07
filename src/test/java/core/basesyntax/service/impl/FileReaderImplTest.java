package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String DATA_BASE = "src/main/resources/database.csv";
    private static final String NO_EXIST_FILE = "noExistPath/database.csv";
    private FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFromFile_correctFile_ok() {
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
        List<String> actualList = fileReader.readFromFile(DATA_BASE);
        assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_noExistFile_notOk() {
        fileReader.readFromFile(NO_EXIST_FILE);
    }

    @Test
    public void readFromFile_emptyFile_notOk() {
        List<String> emptyList = new ArrayList<>();
        assertNotNull(emptyList);
        assertEquals(0, emptyList.size());
    }
}
