package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String PATH = "src/test/resources/test.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void init() {
        writerService = new WriterServiceImpl();
    }

    @After
    public void clearFile() {
        try {
            Files.write(Path.of(PATH), Collections.EMPTY_LIST);
        } catch (IOException e) {
            throw new RuntimeException("Can't clear file", e);
        }
    }

    @Test
    public void writeToFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual;
        expected.add("type,fruit,quantity");
        expected.add("b,orange,30");
        expected.add("b,kiwi,50");
        expected.add("r,orange,15");
        writerService.write(PATH, expected);

        try {
            actual = Files.readAllLines(Path.of(PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file in test", e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidPath_NotOk() {
        String path = "src/test/res/file.csv";
        writerService.write(path, Collections.EMPTY_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void writeToNullPath_NotOk() {
        writerService.write(null, Collections.EMPTY_LIST);
    }
}
