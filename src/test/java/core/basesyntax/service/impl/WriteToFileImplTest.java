package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class WriteToFileImplTest {
    private static final String PATH = "src/test/resources/test_writeToFile.csv";
    private static final String INCORRECT_PATH = "src/main/resources/report.csv";
    private WriteToFileImpl writeToFile;

    @Before
    public void setUp() throws Exception {
        writeToFile = new WriteToFileImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String expected = null;
        expected = getString();
        String actual = "fruit,quantity";
        writeToFile.writeToFile(PATH, actual);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeReport_NullValue_NotOk() {
        writeToFile.writeToFile(PATH, null);
    }

    @Test (expected = RuntimeException.class)
    public void wrongPath_WriteToFile_NotOK() {
        writeToFile.writeToFile(INCORRECT_PATH, "fruit,quantity");
    }

    private String getString() {
        String expected;
        try {
            expected = Files.readString(Path.of(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expected;
    }
}
