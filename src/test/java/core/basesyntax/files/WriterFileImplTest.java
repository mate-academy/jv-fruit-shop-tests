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
    private static final String TEST_FILE = "src/main/resources/test.csv";
    private static List<String> dataList;
    private static final String TITLE = "fruit,quantity";
    private static final String LINE = "banana,152";
    private Reader readerFile = new ReaderFileImpl(TEST_FILE);
    private Writer writerFile = new WriterFileImpl(TEST_FILE);

    @BeforeClass
    public static void beforeAll() {
        dataList = new ArrayList<>();
        dataList.add(TITLE);
        dataList.add(LINE);
    }

    @Test
    public void writeDataToFile_Ok() {
        writerFile.write(dataList);
        List<String> actualList = readerFile.read();
        Assert.assertTrue(actualList.equals(dataList));
    }

    @AfterClass
    public static void afterAll() {
        try {
            Files.delete(Path.of(TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + TEST_FILE);
        }
    }
}
