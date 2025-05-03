package serviceimpl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Reader;

class ReaderImplTest {

    private static final String FILE_NAME = "src/test/java/res/testFile.txt";
    private static final String WRONG_FILE_NAME = "src/test/resources/badFileName.csv";
    private static final String FIRST_FILE_LINE = "type,fruit,quantity";
    private static final String BANANA_FILE_LINE = "b,banana,8";
    private static final String APPLE_FILE_LINE = "b,apple,45";
    private static final String PROBLEM_WITH_FILE = "Problem with file: ";
    private static final int ZERO_INDEX = 0;
    private static List<String> lines;
    private static Reader reportReader;

    @BeforeAll
    public static void setUpClass() {
        reportReader = new ReaderImpl();
        lines = new ArrayList<>();
    }

    @BeforeEach
    public void setUp() {
        lines.add(FIRST_FILE_LINE);
        lines.add(BANANA_FILE_LINE);
        lines.add(APPLE_FILE_LINE);

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (Exception e) {
            throw new RuntimeException(PROBLEM_WITH_FILE + FILE_NAME, e);
        }
    }

    @AfterEach
    public void tearDown() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        lines.clear();
    }

    @Test
    public void getListOfTransactions_validFile_ok() {
        List<String> expectedLines = new ArrayList<>(lines);
        expectedLines.remove(ZERO_INDEX);
        List<String> actualLines = reportReader.readDataFromFile(FILE_NAME);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    public void getListOfTransactions_invalidFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                        reportReader.readDataFromFile(WRONG_FILE_NAME),
                PROBLEM_WITH_FILE + WRONG_FILE_NAME);
    }
}
