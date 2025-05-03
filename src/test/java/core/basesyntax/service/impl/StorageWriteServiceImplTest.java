package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.StorageWriteService;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageWriteServiceImplTest {
    private static StorageWriteService storageWriteService;
    private static final String VALID_REPORT_EXAMPLE = "banana,50\ncherry,40";
    private static final String FILE_PATH_WRITE = "src/main/resources/report.csv";
    private static final String SUCCESS_MESSAGE = "Success! The report was "
            + "successfully saved to the file ";
    private static final String NULL_VALUE = null;

    @BeforeClass
    public static void beforeClass() {
        storageWriteService = new StorageWriteServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeFromDb_notValidFilePath_notOK() {
        storageWriteService.writeFromDb(VALID_REPORT_EXAMPLE, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeFromDb_nullValue_notOk() {
        storageWriteService.writeFromDb(NULL_VALUE, FILE_PATH_WRITE);
    }

    @Test(expected = RuntimeException.class)
    public void writeFromDb_nullFilePath_notOk() {
        storageWriteService.writeFromDb(VALID_REPORT_EXAMPLE, NULL_VALUE);
    }

    @Test
    public void writeFromDb_validParameters_ok() {
        String actual = storageWriteService.writeFromDb(VALID_REPORT_EXAMPLE, FILE_PATH_WRITE);
        assertEquals(SUCCESS_MESSAGE + FILE_PATH_WRITE, actual);
    }
}
