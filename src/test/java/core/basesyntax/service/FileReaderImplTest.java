package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReaderImpl fileReader;
    private static final String EMPTY_PATH = "";
    private static final String CORRECT_PATH = "src/test/resources/filesFruitShop.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_emptyPath_NotOk() {
        try {
            fileReader.read(EMPTY_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'The writing path is not correct' + path");
    }

    @Test
    public void read_correctPath_Ok() {
        boolean expected = true;
        assertEquals(expected, fileReader.read(CORRECT_PATH));
    }
}
