package core.basesyntax;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderServiceTest extends FruitShopTest {
    @Test
    public void readInfoFromFile_OK() {
        List<String> expected;
        try {
            expected = Files.readAllLines(testFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> actual = readerService.readFileToList(testFile);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readInfoFromFile_NotExistFile_NotOk() {
        File file = new File("not_exist.csv");
        readerService.readFileToList(file);
    }

    @Test
    public void readInfoFromFile_EmptyFile_NotOk() {
        List<String> expected = new ArrayList<>();
        File file = new File("emptyFile.csv");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> actual = readerService.readFileToList(file);
        assertEquals("Test failed, List must be empty!", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readInfoFromFile_FileIsNull_NotOk() {
        readerService.readFileToList(null);
    }
}
