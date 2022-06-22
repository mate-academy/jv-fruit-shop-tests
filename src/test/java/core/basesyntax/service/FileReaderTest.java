package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String FILE_EXIST = "src"
            + File.separator + "main" + File.separator
            + "resources" + File.separator + "ActionsPerDay.csv";
    private static final String FILE_NOT_EXIST = "src"
            + File.separator + "main" + File.separator
            + "resources" + File.separator + "wrong.csv";
    private static FileReader readFile;
    private static List<String> lines;

    @BeforeClass
    public static void beforeClass() throws Exception {
        readFile = new FileReaderImpl();
    }

    @Test
    public void readExistedFile_Ok() {
        lines = readFile.getData(FILE_EXIST);
        assertTrue(!lines.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void readNonExistedFile_NotOk() {
        readFile.getData(FILE_NOT_EXIST);
    }

    @After
    public void tearDown() throws Exception {
        Storage.data.clear();
    }
}
