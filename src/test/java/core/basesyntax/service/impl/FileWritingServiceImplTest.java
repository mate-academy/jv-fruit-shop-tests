package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWritingService;
import java.io.File;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWritingServiceImplTest {
    private static final String DATA = "fruit,quantity\n"
            + "banana,100\n"
            + "apple,100";
    private static final String FILE_NAME = "src/test/resources/report.csv";
    private static FileWritingService fileWriter;
    private static File expectedFile;

    @BeforeClass
    public static void setFileHandler() {
        fileWriter = new FileWritingServiceImpl();
        expectedFile = new File(FILE_NAME);
    }

    @Before
    public void deleteFile() {
        expectedFile.deleteOnExit();
    }

    @Test
    public void writeFile_ok() {
        File actualFile = fileWriter.writeFile(FILE_NAME, DATA);
        assertTrue(actualFile.exists());
        assertEquals(DATA.length(), actualFile.length());
    }
}
