package core.basesyntax.files;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String REPORT_FILE_PATH = "src/test/resources/report.csv";
    private static final String INVALID_FILE_PATH = "///";
    private static final String INVALID_FILE = "src/test/resources/invalidReport.csv";
    private static final String DIRECTORY_PATH = "src/test/resources";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static final int EXPECTED_SIZE = 3;
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void write_ToExistingFile_Ok() {
        fileWriter.write(REPORT_FILE_PATH, REPORT);
        assertEquals(EXPECTED_SIZE, fileReader.read(REPORT_FILE_PATH).size());
    }

    @Test
    public void write_ToDefunctFile_Ok() {
        fileWriter.write(INVALID_FILE, REPORT);
        assertEquals(EXPECTED_SIZE, fileReader.read(REPORT_FILE_PATH).size());
    }

    @Test(expected = RuntimeException.class)
    public void write_ToDirectory_NotOk() {
        fileWriter.write(DIRECTORY_PATH, REPORT);
    }

    @Test(expected = RuntimeException.class)
    public void write_ToInvalidFilePath_NotOk() {
        fileWriter.write(INVALID_FILE_PATH, REPORT);
    }
}
