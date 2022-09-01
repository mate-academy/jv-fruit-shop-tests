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
    private static final String FRUIT_FILE = "src/test/resources/fruits_report_test.csv";
    private static final String FRUIT_EMPTY_FILE =
            "src/test/resources/fruits_empty_report_test.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullFileNameValidText_NotOk() {
        String text = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator() + "b,apple,100";
        writerService.writeToFile(null, text);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_validFileNameNullText_NotOk() {
        writerService.writeToFile(FRUIT_FILE, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullFileNameNullText_NotOk() {
        writerService.writeToFile(null, null);
    }

    @Test
    public void writeToFile_validFileNameEmptyText_Ok() {
        String text = "";
        writerService.writeToFile(FRUIT_EMPTY_FILE, text);
        List<String> expected = List.of();
        List<String> actual = readFromFile(FRUIT_EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_validFileNameValidText_Ok() {
        String text = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator() + "b,apple,100";
        writerService.writeToFile(FRUIT_FILE, text);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> actual = readFromFile(FRUIT_FILE);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from file " + fileName, e);
        }
        return lines;
    }
}
