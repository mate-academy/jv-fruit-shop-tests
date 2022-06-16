package core.basesyntax.service.imp;

import core.basesyntax.service.ReaderService;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImpTest {
    private static final String VALID_FILE_SOURCE = "src/main/resources/DayRecords.csv";
    private static final String INVALID_FILE_SOURCE = "src/main/resources/DayRr.csv";
    private static ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImp();
    }

    @Test
    public void readRecordsFromValidSource_ok() {
        Assert.assertTrue(readerService.readRecords(Path.of(VALID_FILE_SOURCE)).size() > 0);
    }

    @Test(expected = RuntimeException.class)
    public void readRecordsFromInvalidSource_notOk() {
        readerService.readRecords(Path.of(INVALID_FILE_SOURCE));
    }
}
