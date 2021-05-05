package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String OUTPUT_FILE_DIRECTION = "src/test/resources/OutputFileTest.csv";
    private static final String DATA_INTO_THE_OUTPUT_FILE = "fruit,quantity"
            + System.lineSeparator()
            + "cherry,150" + System.lineSeparator()
            + "banana,75" + System.lineSeparator()
            + "blueberry,200";
    private static WriterService writerService;
    private List<String> actual;
    private List<String> expected;

    @BeforeClass
    public static void before() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeCorrectDataIntoTheOutputFile_Ok() {
        writerService.write(OUTPUT_FILE_DIRECTION, DATA_INTO_THE_OUTPUT_FILE);

        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_DIRECTION));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from test file");
        }

        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("cherry,150");
        expected.add("banana,75");
        expected.add("blueberry,200");

        assertEquals(expected, actual);
    }
}