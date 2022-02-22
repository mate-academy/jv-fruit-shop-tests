package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.file.FIleWriterService;
import core.basesyntax.service.file.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServicesTest {
    private static final File EMPTY_FILE = new File("tmp.test1");
    private static final File FILE_WITH_DATA = new File("tmp.test2");
    private static final File FILE_FOR_DATA = new File("tmp.test3");
    private static final File NULL_FILE = null;
    private static final File INVALID_NAME_FILE = new File("*?test2");
    private static final String EMPTY_DATA_EXPECTED = "";
    private static final String VALID_DATA_EXPECTED = "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";

    @BeforeClass
    public static void beforeClass() throws Exception {
        Files.write(EMPTY_FILE.toPath(), "".getBytes());
        Files.write(FILE_WITH_DATA.toPath(), ("type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50").getBytes(StandardCharsets.UTF_8));
    }

    @AfterClass
    public static void clearResults() {
        try {
            Files.deleteIfExists(EMPTY_FILE.toPath());
            Files.deleteIfExists(FILE_WITH_DATA.toPath());
            Files.deleteIfExists(FILE_FOR_DATA.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    public void getFromFile_emptyData_ok() {
        String actual = FileReaderService.getFromFile(EMPTY_FILE);
        assertEquals(EMPTY_DATA_EXPECTED, actual);
    }

    @Test
    public void getFromFile_validData_ok() {
        String actual = FileReaderService.getFromFile(FILE_WITH_DATA);
        assertEquals(VALID_DATA_EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getFromFile_nullFil_notOk() {
        FileReaderService.getFromFile(NULL_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void getFromFile_invalidFileName_notOk() {
        FileReaderService.getFromFile(INVALID_NAME_FILE);
    }

    @Test
    public void writeToFile_validFileName_ok() {
        FIleWriterService.writeToFile(FILE_FOR_DATA, VALID_DATA_EXPECTED);
        String actual = readFromFile();
        assertEquals(VALID_DATA_EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFileName_notOk() {
        FIleWriterService.writeToFile(INVALID_NAME_FILE, VALID_DATA_EXPECTED);
    }

    private String readFromFile() {
        try {
            return Files.readString(FILE_FOR_DATA.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file "
                    + FILE_FOR_DATA.getPath(), e);
        }
    }
}
