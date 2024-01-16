package core.basesyntax.services.fileprocessing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.services.fileprocessing.impl.WriterImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.Constants;
import utilities.UtilityReader;
import utilities.UtilityReaderImpl;

public class WriterImplTest {
    private static final String TEST_FILE_NAME = "Test";
    private static final String TEST_FILE_PATH = Constants.FILE_PATH + TEST_FILE_NAME
            + Constants.REPORT_TIME;
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
    private static UtilityReader utilityReaderImpl;

    @BeforeAll
    static void initObjects() {
        writerImpl = new WriterImpl();
        utilityReaderImpl = new UtilityReaderImpl();
    }

    @Test
    void writeToFile_thenRetrieveNormalData_Ok() {
        writerImpl.writeToFile(TEST_FILE_NAME, BUILDER_FOR_WRITER);
        assertEquals(BUILDER_FOR_WRITER.toString(),
                utilityReaderImpl.getDataFromList(utilityReaderImpl.readFile(TEST_FILE_PATH)));
    }
}
