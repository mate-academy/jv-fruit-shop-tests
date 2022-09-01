package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_PATH = "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator
            + "test.csv";
    private static final String TEST_DATA = "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";
    private static final List<String> EXPECTED_DATA = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writerService_equalsWriteToFileFile() {
        writerService.writeToFile(TEST_DATA, FILE_PATH);
        List<String> actual = readFromFile();
        assertEquals(EXPECTED_DATA, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeFileNameNull_NotOk() {
        writerService.writeToFile(TEST_DATA, null);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeFileWithNullReport_NotOk() {
        writerService.writeToFile(null, FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeInvalidPath_NotOk() {
        writerService.writeToFile(TEST_DATA, ".......");
    }

    private List<String> readFromFile() {
        List<String> list;
        try {
            list = Files.readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + FILE_PATH, e);
        }
        return list;
    }
}
