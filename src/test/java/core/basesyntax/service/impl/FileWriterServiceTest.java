package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitException;
import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String NAME_FILE_FOR_TEST = "src/test/resources/writer_test.csv";
    private static final String PART1_DATA = "123";
    private static final String PART2_DATA = "456";
    private static final String DATA_FOR_TEST = PART1_DATA + System.lineSeparator() + PART2_DATA;
    private static WriterService writerService;

    @Before
    public void setUp() {
        writerService = new FileWriterService();
    }

    @Test
    public void write_Ok_validNameAndData() throws IOException {
        writerService.write(NAME_FILE_FOR_TEST, DATA_FOR_TEST);
        List<String> actualData = Files.readAllLines(Path.of(NAME_FILE_FOR_TEST));
        List<String> expectedData = new ArrayList<>();
        expectedData.add(PART1_DATA);
        expectedData.add(PART2_DATA);
        assertEquals(expectedData, actualData);
    }

    @Test (expected = FruitException.class)
    public void write_NotOk_nullNameFila() {
        writerService.write(null, DATA_FOR_TEST);
    }

    @Test (expected = FruitException.class)
    public void write_NotOk_nullData() {
        writerService.write(NAME_FILE_FOR_TEST, null);

    }
}
