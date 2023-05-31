package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final List<String> EXPECTED_CONTENT = new ArrayList<>();
    private static final StringBuilder DATA = new StringBuilder();
    private static final String REPORT = "src/test/resources/input/report.cvs";
    private static final String OUTPUT = "src/test/resources/output/output.cvs";
    private static final String HEADER = "fruit,quantity";
    private static final String FIRST_ELEMENT = "banana,152";
    private static final String SECOND_ELEMENT = "apple,90";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
        EXPECTED_CONTENT.add(HEADER);
        EXPECTED_CONTENT.add(FIRST_ELEMENT);
        EXPECTED_CONTENT.add(SECOND_ELEMENT);
        DATA.append(HEADER).append(System.lineSeparator())
                .append(FIRST_ELEMENT).append(System.lineSeparator())
                .append(SECOND_ELEMENT);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_fileNameIsNull_notOk() {
        writerService.writeData(DATA.toString(), null);
        fail("You should throw RuntimeException when fileName is not existed");
    }

    @Test
    public void writeData_ok() {
        try {
            List<String> actual = Files.readAllLines(Path.of(REPORT));
            writerService.writeData(DATA.toString(), OUTPUT);
            int expectedSize = EXPECTED_CONTENT.size();
            int actualSize = actual.size();
            assertEquals("Expected size: " + expectedSize + " but was " + actualSize,
                    expectedSize, actualSize);
            assertEquals("Expected content: " + EXPECTED_CONTENT + " but was " + actual,
                    EXPECTED_CONTENT, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + REPORT, e);
        }
    }
}
