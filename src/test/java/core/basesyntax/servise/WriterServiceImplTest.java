package core.basesyntax.servise;

import core.basesyntax.files.Reader;
import core.basesyntax.files.ReaderFileImpl;
import core.basesyntax.files.Writer;
import core.basesyntax.files.WriterFileImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_FILE = "src/main/resources/test.csv";
    private static final String TITLE = "fruit,quantity";
    private static final String LINE_01 = "banana,152";
    private static final String LINE_02 = "apple,90";
    private static final String TEST_MAP_01_FRUIT = "banana";
    private static final Integer TEST_MAP_01_QUANTITY = 152;
    private static final String TEST_MAP_02_FRUIT = "apple";
    private static final Integer TEST_MAP_02_QUANTITY = 90;
    private static List<String> expectedList;
    private static Map<String, Integer> testMap;
    private static Reader reader;
    private static Writer writer;
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        expectedList = new ArrayList<>();
        expectedList.add(TITLE);
        expectedList.add(LINE_01);
        expectedList.add(LINE_02);
        testMap = new HashMap<>();
        testMap.put(TEST_MAP_01_FRUIT, TEST_MAP_01_QUANTITY);
        testMap.put(TEST_MAP_02_FRUIT, TEST_MAP_02_QUANTITY);
        reader = new ReaderFileImpl(TEST_FILE);
        writer = new WriterFileImpl(TEST_FILE);
        writerService = new WriterServiceImpl(writer);
    }

    @AfterClass
    public static void afterClass() {
        try {
            Files.delete(Path.of(TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + TEST_FILE);
        }
    }

    @Test
    public void writeTestDateAndCheckWithTemplate_Ok() {
        writerService.writeData(testMap);
        List<String> actualList = reader.read();
        Assert.assertEquals(expectedList, actualList);
    }
}
