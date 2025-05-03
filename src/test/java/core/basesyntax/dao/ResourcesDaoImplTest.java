package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourcesDaoImplTest {
    private static ResourcesDao resourcesDao;
    private static final String INPUT_TEST_FILE_PATH =
            "src/test/java/core/basesyntax/test_resources/input_test";
    private static final String OUTUT_TEST_FILE_PATH =
            "src/test/java/core/basesyntax/test_resources/output_test";
    private static final String NOT_EXISTING_FILE_PATH =
            "src/test/java/core/basesyntax/test_resources/";
    private static List<String> data = List.of("b,banana,100", "b,apple,100", "s,banana,100",
            "s,apple,100", "p,banana,100", "p,apple,100", "r,banana,100", "r,apple,100");

    @BeforeClass
    public static void beforeClass() {
        resourcesDao = new ResourcesDaoImpl();
    }

    @Test
    public void readFromNotExistingFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                resourcesDao.readFromFile(NOT_EXISTING_FILE_PATH));
    }

    @Test
    public void readFromFileWithNullPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                resourcesDao.readFromFile(null));
    }

    @Test
    public void readFromFileWithIncorrectFirstLine_notOk() {
        try {
            Files.write(Path.of(INPUT_TEST_FILE_PATH), data);
            assertThrows(RuntimeException.class,
                    () -> resourcesDao.readFromFile(INPUT_TEST_FILE_PATH));
            List<String> newData = new ArrayList<>(data);
            newData.add(0, "type,fruit,quantity");
            Files.write(Path.of(INPUT_TEST_FILE_PATH), newData);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the file: " + INPUT_TEST_FILE_PATH);
        }
    }

    @Test
    public void readFromFile_Ok() {
        assertEquals(data, resourcesDao.readFromFile(INPUT_TEST_FILE_PATH));
    }

    @Test
    public void writeToFileWithWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> resourcesDao.writeToFile(NOT_EXISTING_FILE_PATH, data));
    }

    @Test
    public void writeToFileNullData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> resourcesDao.writeToFile(OUTUT_TEST_FILE_PATH, null));
    }

    @Test
    public void writeToFile_Ok() {
        resourcesDao.writeToFile(OUTUT_TEST_FILE_PATH, data);
        List<String> actual;
        try {
            actual = Files.lines(Path.of(OUTUT_TEST_FILE_PATH))
                          .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from File: " + OUTUT_TEST_FILE_PATH);
        }
        assertEquals(data, actual);
    }
}
