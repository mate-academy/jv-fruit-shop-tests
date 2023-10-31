package serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Writer;

class WriterImplTest {
    private static final String FILE_NAME = "src/test/java/res/testFile.txt";
    private static final String WRONG_FILE_NAME = "src/test/resources/badFileName.csv";
    private static final String FIRST_FILE_LINE = "type,fruit,quantity";
    private static final String BANANA_FILE_LINE = "b,banana,8";
    private static final String APPLE_FILE_LINE = "b,apple,45";
    private static final String PROBLEM_WITH_FILE = "Problem with file: ";
    private static final String NULL_PROBLEM = "Can not write NULL to : ";
    private static List<String> lines;
    private static Writer reportWrite;

    @BeforeAll
    public static void setUpClass() {
        lines = new ArrayList<>();
        reportWrite = new WriterImpl();
    }

    @BeforeEach
    public void setUp() {
        lines.add(FIRST_FILE_LINE);
        lines.add(BANANA_FILE_LINE);
        lines.add(APPLE_FILE_LINE);
    }

    @AfterEach
    public void tearDown() {
        lines.clear();
        try {
            new File(FILE_NAME).delete();
            new File(WRONG_FILE_NAME).delete();
        } catch (Exception e) {
            throw new RuntimeException(PROBLEM_WITH_FILE, e);
        }
    }

    @Test
    void writeToFile_nullLinesOrFileName_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> reportWrite
                .writeToFile(null, "test.txt"));
        assertThrows(IllegalArgumentException.class, () -> reportWrite
                .writeToFile(new ArrayList<>(), null));
    }

    @Test
    void writeToFile_nonExistentDirectory_throwRuntimeException() {
        List<String> lines = new ArrayList<>();
        lines.add("apple,10");
        String nonExistentDirectory = "non_existent_directory/test.txt";
        assertThrows(RuntimeException.class, () -> reportWrite
                .writeToFile(lines, nonExistentDirectory));
    }

    @Test
    public void writeToFile_validValues_ok() {
        reportWrite.writeToFile(lines, FILE_NAME);
        assertEquals(lines,readLinesFromFile(FILE_NAME));
    }

    @Test
    public void writeToFile_largeNumberOfLines_ok() {
        List<String> largeListOfLines = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeListOfLines.add("Line " + i);
        }
        String largeFile = "large_file.txt";
        reportWrite.writeToFile(largeListOfLines, largeFile);
        List<String> fileContents = readLinesFromFile(largeFile);
        assertEquals(1000, fileContents.size());
    }

    @Test
    public void writeToFile_emptyList_ok() {
        String emptyFile = "empty_file.txt";
        List<String> emptyList = Collections.emptyList();
        reportWrite.writeToFile(emptyList, emptyFile);
        assertTrue(new File(emptyFile).exists());
    }

    @Test
    public void writeToFile_nullValue_notOk() {
        assertThrows(RuntimeException.class, () ->
                        reportWrite.writeToFile(null, FILE_NAME),
                NULL_PROBLEM + FILE_NAME);
    }

    @Test
    public void writeToFile_nullFileName_notOk() {
        String nullFileName = null;
        assertThrows(RuntimeException.class, () ->
                        reportWrite.writeToFile(lines, nullFileName),
                PROBLEM_WITH_FILE + nullFileName);
    }

    public List<String> readLinesFromFile(String filePath) {
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(filePath));
        } catch (Exception e) {
            throw new RuntimeException(PROBLEM_WITH_FILE + filePath, e);
        }
        return dataFromFile;
    }
}
