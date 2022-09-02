package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        String fileInput = "src/test/resources/test_input.csv";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        List<String> actual = readerService.readFromFile(fileInput);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_UncorrectFilePath_NotOk() {
        String data = "banana";
        readerService.readFromFile(data);
    }

    @Test
    public void read_EmptyFile_NotOk() {
        String empty = "src/test/resources/test_emptyFile.csv";
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(empty);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void read_FileAsNull_NotOk() {
        readerService.readFromFile(null);
    }
}
