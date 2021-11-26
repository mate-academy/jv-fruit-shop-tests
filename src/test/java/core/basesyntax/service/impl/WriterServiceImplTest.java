package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String PATH_TO_WRITER_TEST = "src/test/resources/testWriter.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void writer_pathIsNull_notOk() {
        writerService.writeToFile(null, List.of("20"));
    }

    @Test (expected = RuntimeException.class)
    public void writer_pathIsEmpty_notOk() {
        writerService.writeToFile("", List.of("20"));
    }

    @Test
    public void writer_emptyList_ok() {
        List<String> expected = List.of();
        writerService.writeToFile(PATH_TO_WRITER_TEST, expected);
        assertEquals(expected, readFromFile(PATH_TO_WRITER_TEST));
    }

    @Test
    public void writer_oneLineDifferentCharacters_ok() {
        List<String> expected = List.of("@#$%^&(*^$@");
        writerService.writeToFile(PATH_TO_WRITER_TEST, expected);
        assertEquals(expected, readFromFile(PATH_TO_WRITER_TEST));
    }

    @Test
    public void writer_validData_ok() {
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        writerService.writeToFile(PATH_TO_WRITER_TEST, expected);
        assertEquals(expected, readFromFile(PATH_TO_WRITER_TEST));
    }

    private List<String> readFromFile(String filePath) {
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write to file");
        }
        return actual;
    }
}
