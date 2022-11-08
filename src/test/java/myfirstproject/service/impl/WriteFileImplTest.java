package myfirstproject.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;
import myfirstproject.service.WriteFile;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String PATH_TO_NEW_FILE = "src/test/resources/reportFile.csv";
    private static final String WRONG_PATH = "src/resources/reportFile.csv";
    private static final WriteFile writer = new WriteFileImpl();

    @Test(expected = RuntimeException.class)
    public void readWrongFile_not_Ok() {
        writer.writeToFile(WRONG_PATH, Collections.EMPTY_MAP);
    }

    @Test
    public void isCreatedFileExist_Ok() {
        final File file = new File(PATH_TO_NEW_FILE);
        assertTrue(file.exists());
    }
}
