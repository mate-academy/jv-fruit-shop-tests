package core.basesyntax.filework.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.filework.ShopFileReader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopFileReaderImplTest {
    public static final String CORRECT_FILE_PATH = "src/main/resources/fruitShopStorageFile.csv";
    public static final String EMPTY_FILE_PATH = "src/main/resources/emptyFile.csv";
    public static final String NONEXISTENT_FILE_PATH = "src/main/resources/nonExistentFile.csv";
    private static ShopFileReader shopFileReader;

    @BeforeClass
    public static void beforeClass() {
        shopFileReader = new ShopFileReaderImpl();
    }

    @Test
    public void readFromFile_CorrectFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "    b,banana,20", "    b,apple,100", "    s,banana,100", "    p,banana,13",
                "    r,apple,10", "    p,apple,20", "    p,banana,5", "    s,banana,50");
        List<String> actual = shopFileReader.readFromFile(CORRECT_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NonExistentFile_notOk() {
        shopFileReader.readFromFile(NONEXISTENT_FILE_PATH);
    }

    @Test
    public void readFromFile_EmptyFile_Ok() {
        List<String> empty = shopFileReader.readFromFile(EMPTY_FILE_PATH);
        assertNotNull(empty);
        assertEquals(0, empty.size());
    }
}
