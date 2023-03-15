package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private ReaderService readerService;
    private String filePath;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_nullFilePath_notOk() {
        filePath = null;
        try {
            readerService.readFromFile(filePath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Exception should be thrown if file path is null");
    }

    @Test
    public void readFromFile_emptyFilePath_notOk() {
        filePath = "";
        try {
            readerService.readFromFile(filePath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Exception should be thrown if file path is empty");
    }

    @Test
    public void readFromFile_readData_ok() {
        filePath = "src/main/resources/input_file.csv";
        String expected = "type,fruit,quantity,";
        String actual = readerService.readFromFile(filePath).stream()
                .findFirst()
                .get();
        assertEquals(expected, actual);
    }
}
