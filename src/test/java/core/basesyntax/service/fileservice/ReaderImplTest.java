package core.basesyntax.service.fileservice;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.IIOException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private static final String NAME_OF_EMPTY_FILE = "testEmptyFile.scv";
    private static final String NOT_EXISTENT_FILE = "nonExistentFile.scv";
    private static final String OK_FILE_NAME = "testFile.scv";
    private static final String WRONG_FORMAT = "testEmptyFile.txt";
    private static final String EMPTY_INPUT_DATA = "";
    private List<String> expectedOutputData;
    private Reader reader;

    @Before
    public void setUp() throws Exception {
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
    public void readFromIncorrectlyFilename_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromInput(EMPTY_INPUT_DATA));
    }

    @Test
    public void readFromNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFromInput(null));
    }

    @Test
    public void readFromEmptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromInput(NAME_OF_EMPTY_FILE));
    }

    @Test
    public void readFromWrongFileFormat_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromInput(WRONG_FORMAT));
    }

    @Test
    public void readFromFileNonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromInput(NOT_EXISTENT_FILE));
    }

    @Test
    public void readFromFile_Ok() {
        assertEquals(expectedOutputData, reader.readFromInput(OK_FILE_NAME));
    }

    @AfterClass
    public static void afterClass() {
        File testEmptyFile = new File(NAME_OF_EMPTY_FILE);
        testEmptyFile.delete();
        File testFile = new File(OK_FILE_NAME);
        testFile.delete();
    }
}
