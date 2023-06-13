package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String VALID_FIRST_LINE = "fruit,quantity";
    private static final String VALID_SECOND_LINE = "cherry,20";
    private static final String VALID_INPUT_STRING = VALID_FIRST_LINE
            + System.lineSeparator() + VALID_SECOND_LINE;
    private static final List<String> EXPECTED_LIST = List.of(VALID_FIRST_LINE, VALID_SECOND_LINE);
    private static final File VALID_FILE = new File("src/test/java/resources/ToFile.csv");
    private static final File INVALID_FILE = new File("");
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
        try {
            new FileWriter(VALID_FILE, false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeDataToFile_validInputAndFile_ok() {
        writerService.writeDataToFile(VALID_FILE, VALID_INPUT_STRING);
        try {
            List<String> actual = Files.readAllLines(VALID_FILE.toPath());
            Assert.assertEquals(EXPECTED_LIST, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeDataToFile_invalidFile_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                writerService.writeDataToFile(INVALID_FILE, VALID_INPUT_STRING));
    }
}
