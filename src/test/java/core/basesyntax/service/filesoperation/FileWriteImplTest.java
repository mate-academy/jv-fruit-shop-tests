package core.basesyntax.service.filesoperation;

import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class FileWriteImplTest {
    private static final String FILE_PATH = "src/test/resources/report_file";
    private static final String DATA = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    private FileWrite fileWrite;

    @Before
    public void setUp() {
        fileWrite = new FileWriteImpl();
    }

    @Test
    public void writeFilePath_Ok() {
        fileWrite.write(FILE_PATH, DATA);
        assertTrue(new File(FILE_PATH).exists());
        assertTrue(new File(FILE_PATH).canRead());
    }

    @Test(expected = RuntimeException.class)
    public void writeFilePath_NotOk() {
        fileWrite.write("", DATA);
    }

    @Test(expected = RuntimeException.class)
    public void writeFilePathNull_NotOk() {
        fileWrite.write(null, DATA);
    }
}
