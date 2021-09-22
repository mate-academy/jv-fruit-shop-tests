package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final Path DAILY_SHOP_CSV = Path.of("dailyShopCsv.csv");
    private static final Path INVALID_FILE_PATH = Path.of("");
    private static FileService fileService;
    private static List<String> emptyList;
    private static List<String> fullList;
    private List<String> expected;
    private List<String> actual;

    @BeforeClass
    public static void setUp() {
        fileService = new FileServiceImpl();
        emptyList = new ArrayList<>();
        fullList = new ArrayList<>(List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13"));
    }

    @After
    public void clearResults() {
        try {
            Files.deleteIfExists(DAILY_SHOP_CSV);
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    public void writeAndReadFile_fileWithEmptyList_Ok() {
        fileService.writeToFile(emptyList, DAILY_SHOP_CSV);
        assertTrue("Test failed! File isn`t exist", Files.exists(DAILY_SHOP_CSV));
        expected = readFromTestFile(DAILY_SHOP_CSV);
        actual = fileService.readFromFile(DAILY_SHOP_CSV);
        assertEquals("Test failed! You should returned empty list.", expected, actual);
    }

    @Test
    public void writeAndReadFile_fileWithCorrectDataList_Ok() {
        fileService.writeToFile(fullList, DAILY_SHOP_CSV);
        assertTrue("Test failed! File isn`t exist", Files.exists(DAILY_SHOP_CSV));
        expected = readFromTestFile(DAILY_SHOP_CSV);
        expected.remove(0);
        actual = fileService.readFromFile(DAILY_SHOP_CSV);
        assertEquals("Test failed! You should returned "
                + expected + ", but was "
                + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileWithInvalidFilePath_NotOk() {
        fileService.writeToFile(fullList, INVALID_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileWithInvalidFilePath_NotOk() {
        fileService.readFromFile(DAILY_SHOP_CSV);
    }

    private List<String> readFromTestFile(Path path) {
        try {
            return Files.readAllLines(DAILY_SHOP_CSV);
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + DAILY_SHOP_CSV, e);
        }
    }
}
