package core.basesyntax.service.fileoperations;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class FileServiceImplTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.csv";
    private static final String FILES_TO_DELETE = "src/main/resources/*.*";
    private static final String RECOVER_INPUT_FILE_NAME = "src/test/resources/input.csv";
    private static final String RECOVER_OUTPUT_FILE_NAME = "src/test/resources/output.csv";
    private static final String INPUT_EMPTY_DATA_FILE_NAME
            = "src/test/resources/input_empty_data.csv";
    private static final String INPUT_NORMAL_DATA_FILE_NAME
            = "src/test/resources/input_normal_data.csv";
    private static final String LSEP = System.lineSeparator();
    private static final String WRITE_TEST_STRING =
            "fruit,quantity" + LSEP
                    + "apple,90" + LSEP
                    + "orange,465" + LSEP
                    + "mango,71" + LSEP
                    + "plum,101" + LSEP
                    + "pear,191" + LSEP
                    + "grapefruit,9" + LSEP;
    private static FileService fileService = new FileServiceImpl();

    @Before
    public void setUp() {
        try {
            Files.copy(Path.of(RECOVER_INPUT_FILE_NAME),
                    Path.of(INPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't provide files recovering.");
        }
    }

    @Test
    public void readData_normalData_Ok() {
        inputFileSetup(INPUT_NORMAL_DATA_FILE_NAME);
        List<String> readList = fileService.readData();
        int expected = 12;
        assertEquals(expected, readList.size());
    }

    @Test
    public void readData_emptyData_Ok() {
        inputFileSetup(INPUT_EMPTY_DATA_FILE_NAME);
        List<String> readList = fileService.readData();
        int expected = 1;
        assertEquals(expected, readList.size());
    }

    @Test(expected = RuntimeException.class)
    public void readData_exceptionIfNoFiles_Ok() {
        try {
            Files.delete(Path.of(FILES_TO_DELETE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete files " + FILES_TO_DELETE, e);
        }
        List<String> readList = fileService.readData();
    }

    @Test
    public void writeData_Ok() {
        fileService.writeData(WRITE_TEST_STRING);
        int expected = 7;
        assertEquals(expected, readOutput().size());
    }

    @After
    public void tearDown() throws Exception {
        try {
            Files.deleteIfExists(Path.of(FILES_TO_DELETE));
            Files.copy(Path.of(RECOVER_INPUT_FILE_NAME),
                    Path.of(INPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Path.of(RECOVER_OUTPUT_FILE_NAME),
                    Path.of(OUTPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't provide files recovering.");
        }
    }

    private void inputFileSetup(String fileName) {
        try {
            Files.copy(Path.of(fileName),
                    Path.of(INPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't copy file '"
                    + fileName + "'", e);
        }
    }

    private List<String> readOutput() {
        List<String> outputList;
        try {
            outputList = Files.readAllLines(Path.of(OUTPUT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        return outputList;
    }
}