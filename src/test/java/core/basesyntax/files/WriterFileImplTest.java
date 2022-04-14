package core.basesyntax.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterFileImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static List<String> dataList;
    private static final String TITLE = "fruit,quantity";
    private static final String LINE = "banana,152";
    private static Reader readerFile;
    private static Writer writerFile;

    @BeforeClass
    public static void beforeAll() {
        readerFile = new ReaderFileImpl(TEST_FILE);
        writerFile = new WriterFileImpl(TEST_FILE);
        dataList = new ArrayList<>();
        dataList.add(TITLE);
        dataList.add(LINE);
    }

    @Test
    public void writeDataToFile_Ok() {
        writerFile.write(dataList);
        List<String> actualList = readerFile.read();
        Assert.assertEquals(actualList, dataList);
    }

    @AfterClass
    public static void afterAll() {
        try {
            Files.delete(Path.of(TEST_FILE));
        } catch (IOException e) {
            Assert.fail("Can't delete file " + TEST_FILE);
        }
    }
}
