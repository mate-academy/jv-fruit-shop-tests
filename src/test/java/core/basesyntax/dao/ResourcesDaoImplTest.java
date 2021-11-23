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
    private static String inputTestFilePath =
            "src/test/java/core/basesyntax/test_resources/input_test";
    private static String outputTestFilePath =
            "src/test/java/core/basesyntax/test_resources/output_test";
    private static String notExistingFilePath = "src/test/java/core/basesyntax/test_resources/";
    private static List<String> data = List.of("b,banana,100", "b,apple,100", "s,banana,100",
            "s,apple,100", "p,banana,100", "p,apple,100", "r,banana,100", "r,apple,100");

    @BeforeClass
    public static void beforeClass() {
        resourcesDao = new ResourcesDaoImpl();
    }

    @Test
    public void readFromNotExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> resourcesDao.readFromFile(notExistingFilePath));
    }

    @Test
    public void readFromFileWithNullPath_notOk() {
        assertThrows(RuntimeException.class, () -> resourcesDao.readFromFile(null));
    }

    @Test
    public void readFromFileWithIncorrectFirstLine_notOk() {
        try {
            Files.write(Path.of(inputTestFilePath), data);
            assertThrows(RuntimeException.class,
                    () -> resourcesDao.readFromFile(inputTestFilePath));
            List<String> newData = new ArrayList<>(data);
            newData.add(0, "type,fruit,quantity");
            Files.write(Path.of(inputTestFilePath), newData);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the file: " + inputTestFilePath);
        }
    }

    @Test
    public void readFromFile_Ok() {
        assertEquals(data, resourcesDao.readFromFile(inputTestFilePath));
    }

    @Test
    public void writeToFileWithWrongPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> resourcesDao.writeToFile(notExistingFilePath, data));
    }

    @Test
    public void writeToFileNullData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> resourcesDao.writeToFile(outputTestFilePath, null));
    }

    @Test
    public void writeToFile_Ok() {
        resourcesDao.writeToFile(outputTestFilePath, data);
        List<String> actual;
        try {
            actual = Files.lines(Path.of(outputTestFilePath))
                          .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from File: " + outputTestFilePath);
        }
        assertEquals(data, actual);
    }
}
