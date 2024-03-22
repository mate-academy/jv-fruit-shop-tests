package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import java.util.List;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String REPORT_PATH = "src/test/resources/report.csv";
    private static final List<String> VALID_REPORT = List.of(
            "fruit,quantity",
            "banana,107",
            "apple,100"
    );
    private static final List<String> EMPTY_REPORT = List.of(
            "fruit,quantity"
    );
    private final WriterService writerService = new WriterServiceImpl();
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void writeToFile_validReport_Ok() {
        writerService.writeToFile(VALID_REPORT, REPORT_PATH);

        List<String> actual = readerService.readFromFile(REPORT_PATH);
        assertEquals(VALID_REPORT, actual);
    }

    @Test
    void writeToFile_EmptyReport_Ok() {
        writerService.writeToFile(EMPTY_REPORT, REPORT_PATH);

        List<String> actual = readerService.readFromFile(REPORT_PATH);
        assertEquals(EMPTY_REPORT, actual);
    }
}
