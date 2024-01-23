package core.basesyntax.services.fileprocessing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.services.fileprocessing.impl.WriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.Constants;

public class WriterImplTest {
    private static final String TEST_FILE_NAME = "Test";
    private static final String TEST_FILE_PATH =
            Constants.FILE_PATH + TEST_FILE_NAME + Constants.REPORT_TIME;
    private static final String EXPECTED_INITIAL_LINE = "fruit,quantity";
    private static final String EXPECTED_APPLE_LINE = "apple,10";
    private static final String EXPECTED_BANANA_LINE = "banana,20";
    private static final String EXPECTED_ORANGE_LINE = "orange,30";
    private static final StringBuilder BUILDER_FOR_WRITER =
            new StringBuilder().append(EXPECTED_INITIAL_LINE).append(System.lineSeparator())
                    .append(EXPECTED_APPLE_LINE).append(System.lineSeparator())
                    .append(EXPECTED_BANANA_LINE).append(System.lineSeparator())
                    .append(EXPECTED_ORANGE_LINE);
    private static Writer writerImpl;

    @BeforeAll
    static void initObjects() {
        writerImpl = new WriterImpl();
    }

    @Test
    void writeToFile_thenRetrieveNormalData_Ok() {
        writerImpl.writeToFile(TEST_FILE_NAME, BUILDER_FOR_WRITER);
        Path path = Paths.get(TEST_FILE_PATH);
        List<String> actualResult;
        try {
            actualResult = Files.readAllLines(path);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        String expectedResult = BUILDER_FOR_WRITER.toString();
        assertEquals(expectedResult, String.join(System.lineSeparator(), actualResult));
    }
}
