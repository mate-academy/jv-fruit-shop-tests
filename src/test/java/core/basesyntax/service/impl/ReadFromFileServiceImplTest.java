package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFromFileService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileServiceImplTest {
    private static final String FILE_WITH_VALID_DATA = "src/test/java/core"
            + "/basesyntax/valid_data_file.csv";
    private static final String FILE_WITH_EMPTY_LINE_INPUT = "src/test/java/core"
            + "/basesyntax/empty_file_input.csv";
    private static final String NON_EXISTING_FILE = "src/test/java/core"
            + "/basesyntax/no_existing_file.csv";
    private static ReadFromFileService readFromFileService;

    @BeforeClass
    public static void beforeClass() {
        readFromFileService = new ReadFromFileServiceImpl();
    }

    @Test
    public void readFromFile_ValidDataFile_Ok() {
        String expected = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50\n";

        String actual = readFromFileService.readFromFile(FILE_WITH_VALID_DATA);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_EmptyFileInput_Ok() {
        String expected = "";
        String actual = readFromFileService.readFromFile(FILE_WITH_EMPTY_LINE_INPUT);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NonExistingFile_NotOk() {
        readFromFileService.readFromFile(NON_EXISTING_FILE);
    }

    @Test(expected = NullPointerException.class)
    public void readFromNullFile_NotOk() {
        readFromFileService.readFromFile(null);
    }
}




