package core.basesyntax.service.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileWriterWithResultsImplTest {
    private static final String VALID_PATH =
            "src/main/java/core/basesyntax/recources/validForWriter.csv";
    private static final String INVALID_PATH = "*-invalidForWriter";
    private static final String PATH_FOR_EMPTY =
            "src/main/java/core/basesyntax/recource/emptyForWriter.csv";
    private List<String> testList;
    private final FileWriterWithResultsImpl writer = new FileWriterWithResultsImpl();
    private final FileReader reader = new FileReaderImpl();

    @Test
    public void writeResultToFile_validInput_ok() {
        testList = new ArrayList<>();
        testList.add("first test line for writer");
        testList.add("second line for writer");
        List<String> expected = new ArrayList<>(testList);
        expected.add(0, "fruit,quantity");
        writer.writeResultToFile(VALID_PATH, testList);
        List<String> actual = reader.readFile(VALID_PATH);
        assertEquals("Method should return file with valid data for valid input",
                expected, actual);
    }

    @Test
    public void writerResultToFile_invalidPath_notOk() {
        assertThrows("Method should RuntimeExeption for invalid path",
                RuntimeException.class, () -> writer.writeResultToFile(INVALID_PATH, testList));
    }

    @Test
    public void writerResultToFile_emptyInput_notOk() {
        testList = new ArrayList<>();
        assertThrows("Method should RuntimeExeption for invalid input",
                RuntimeException.class, () -> writer.writeResultToFile(PATH_FOR_EMPTY, testList));
    }
}
