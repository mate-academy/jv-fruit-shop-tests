package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String DATA_BASE = "src/test/java/core/basesyntax/resources/database.csv";
    private static final String WRONG_PATH = "wrongPath/database.csv";
    private static final String EMPTY_FILE =
            "src/test/java/core/basesyntax/resources/emptyFile.csv";
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
        List<String> actualList = fileReader.readData(DATA_BASE);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void readFromFile_wrongPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readData(WRONG_PATH));
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> emptyList = fileReader.readData(EMPTY_FILE);
        assertEquals(0, emptyList.size());
    }
}
