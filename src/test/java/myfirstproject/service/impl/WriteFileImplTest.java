package myfirstproject.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import myfirstproject.service.WriteFile;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String PATH_TO_NEW_FILE = "src/test/resources/reportFile.csv";
    private static final String WRONG_PATH = "src/resources/reportFile.csv";
    private static final WriteFile WRITE_FILE = new WriteFileImpl();
    private static final String[] TEST_CONTENT = { "banana","15" };

    @Test(expected = RuntimeException.class)
    public void testWrongFile_not_ok() {
        File file = new File(WRONG_PATH);
        WRITE_FILE.writeToFile(WRONG_PATH, TEST_CONTENT[0]);
        assertTrue(file.exists());
    }

    @Test
    public void testContentOfFile_ok() {
        List<String[]> temporalList = new ArrayList<>();
        WRITE_FILE.writeToFile(PATH_TO_NEW_FILE, Arrays.toString(TEST_CONTENT));
        temporalList.add(TEST_CONTENT);
        assertEquals(Arrays.toString(temporalList.get(0)), Arrays.toString(TEST_CONTENT));
    }
}
