package core.basesyntax.service.fileservice;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.IIOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String NAME_OF_EMPTY_FILE = "testEmptyFile.csv";
    private static final String NOT_EXISTENT_FILE = "nonExistentFile.csv";
    private static final String OK_FILE_NAME = "testFile.csv";
    private static final String WRONG_FORMAT = "testEmptyFile.txt";
    private static final String EMPTY_INPUT_DATA = "";
    private static List<String> expectedOutputData;
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        expectedOutputData = new ArrayList<>();
        expectedOutputData.add("type,fruit,quantity");
        expectedOutputData.add("b,banana,20");
        expectedOutputData.add("b,apple,100");
        expectedOutputData.add("s,banana,100");
        expectedOutputData.add("p,banana,13");
        reader = new ReaderImpl();
        try {
            Files.write(new File(NAME_OF_EMPTY_FILE).toPath(), EMPTY_INPUT_DATA.getBytes());
        } catch (IIOException e) {
            throw new RuntimeException("Can`t write to file" + NAME_OF_EMPTY_FILE);
        }
        String okFileContent = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n";
        try {
            Files.write(new File(OK_FILE_NAME).toPath(), okFileContent.getBytes());
        } catch (IIOException e) {
            throw new RuntimeException("Can`t write to file" + OK_FILE_NAME);
        }

    }

    @Test
    public void read_incorrectlyFilename_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(EMPTY_INPUT_DATA));
    }

    @Test
    public void read_null_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read(null));
    }

    @Test
    public void read_emptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(NAME_OF_EMPTY_FILE));
    }

    @Test
    public void read_wrongFileFormat_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(WRONG_FORMAT));
    }

    @Test
    public void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.read(NOT_EXISTENT_FILE));
    }

    @Test
    public void read_file_Ok() {
        assertEquals(expectedOutputData, reader.read(OK_FILE_NAME));
    }

    @AfterClass
    public static void afterClass() {
        File testEmptyFile = new File(NAME_OF_EMPTY_FILE);
        testEmptyFile.delete();
        File testFile = new File(OK_FILE_NAME);
        testFile.delete();
    }
}
