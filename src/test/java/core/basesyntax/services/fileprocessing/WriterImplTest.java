package core.basesyntax.services.fileprocessing;

import static core.basesyntax.services.Constants.FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.services.fileprocessing.impl.ReaderCsvImpl;
import core.basesyntax.services.fileprocessing.impl.WriterImpl;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterImplTest {
    private static final String FILE_NAME = "Test file";
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
    private static Reader readerCsvImpl;

    @BeforeAll
    static void initFileWriter() {
        writerImpl = new WriterImpl();
        readerCsvImpl = new ReaderCsvImpl();
    }

    @AfterAll
    static void closeFileWriter() {
        writerImpl = null;
        readerCsvImpl = null;
    }

    @Test
    void writeToFile_thenRetrieveNormalData_Ok() {
        writerImpl.writeToFile(FILE_NAME, BUILDER_FOR_WRITER);
        List<String> listFromReader = readerCsvImpl.readFile(FILE_PATH);
        StringBuilder builderFromList = appendListElementsToBuilder(listFromReader);
        assertEquals(BUILDER_FOR_WRITER.toString(), builderFromList.toString());
    }

    private StringBuilder appendListElementsToBuilder(List<String> list) {
        StringBuilder builderFromList = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                builderFromList.append(list.get(i)).append(System.lineSeparator());
            } else {
                builderFromList.append(list.get(i));
            }
        }
        return builderFromList;
    }
}
