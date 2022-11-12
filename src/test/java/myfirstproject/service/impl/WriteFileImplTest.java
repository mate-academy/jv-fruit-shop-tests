package myfirstproject.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import myfirstproject.service.WriteFile;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String WRONG_PATH = "src/resources/reportFile.csv";
    private static final WriteFile WRITE_FILE = new WriteFileImpl();
    private static final String TEST_CONTENT = "banana,15";

    @Test(expected = RuntimeException.class)
    public void testWrongFile_not_ok() {
        File file = new File(WRONG_PATH);
        WRITE_FILE.writeToFile(WRONG_PATH, TEST_CONTENT);
        assertTrue(file.exists());
    }
}
