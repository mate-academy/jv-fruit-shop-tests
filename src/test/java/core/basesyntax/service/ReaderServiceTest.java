package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String PATH = "src/test/resources/test.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @AfterClass
    public static void clearFile() {
        try {
            Files.write(Path.of(PATH), Collections.EMPTY_LIST);
        } catch (IOException e) {
            throw new RuntimeException("Can't clear file", e);
        }
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("s,orange,12");
        expected.add("p,orange,5");

        try {
            Files.write(Path.of(PATH), expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file in test", e);
        }
        List<String> actual = readerService.read(PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromInvalidPath_NotOk() {
        String path = "src/test/oops";
        readerService.read(path);
    }

    @Test(expected = RuntimeException.class)
    public void readFromNullPath_NotOk() {
        readerService.read(null);
    }
}
