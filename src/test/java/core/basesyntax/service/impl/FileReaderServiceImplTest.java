package core.basesyntax.service.impl;

import core.basesyntax.exception.FileReadingException;
import core.basesyntax.service.FileReaderService;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService = new FileReaderServiceImpl();
    private static final String VALID_PATH = "src/test/resources/data.csv";

    @Test
    public void readFromFile_validPath_ok() {
        String actual = fileReaderService.readFromFile(VALID_PATH);
        String expected = "type,fruit,quantity\n"
                + "    b,banana,20";
        Assert.assertEquals("String from " + VALID_PATH + "have to be read", expected, actual);
    }

    @Test(expected = FileReadingException.class)
    public void readFromFile_invalidPath_notOk() {
        fileReaderService.readFromFile(VALID_PATH + "/notYourDayBro;)");
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_nullPath_notOk() {
        fileReaderService.readFromFile(null);
    }
}
