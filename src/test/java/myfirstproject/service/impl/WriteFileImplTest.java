package myfirstproject.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import myfirstproject.service.ReadFile;
import myfirstproject.service.WriteFile;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String PATH_TO_NEW_FILE = "src/test/resources/reportFile.csv";
    private static final String WRONG_PATH = "src/resources/reportFile.csv";
    private static final WriteFile WRITE_FILE = new WriteFileImpl();
    private static final String TEST_CONTENT = "banana,15";

    @Test(expected = RuntimeException.class)
    public void readWrongFile_not_Ok() {
        WRITE_FILE.writeToFile(WRONG_PATH, "");
    }

    @Test
    public void testContentOfFile_Ok() {
        ReadFile reader = new ReadFileImpl();
        List<String[]> temporalList = new ArrayList<>();
        WRITE_FILE.writeToFile(PATH_TO_NEW_FILE, TEST_CONTENT);
        reader.readFile(temporalList, PATH_TO_NEW_FILE);
        String expected = temporalList.get(0)[0] + "," + temporalList.get(0)[1];
        assertEquals(TEST_CONTENT,expected);
    }
}
