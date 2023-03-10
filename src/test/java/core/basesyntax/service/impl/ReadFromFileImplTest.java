package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReadFromFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileImplTest {
    private static final String ORDER_FILE_NAME = "src/test/resources/order.csv";
    private static final String EMPTY_ORDER_FILE_NAME = "src/test/resources/empty_order.csv";
    private static final String NOT_EXISTING_FILE_NAME = "src/test/resources/not_existing_file.csv";
    private static ReadFromFile readFromFile;

    @BeforeClass
    public static void beforeClass() {
        readFromFile = new ReadFromFileImpl();
    }

    @Test
    public void read_with_valid_file_name_Ok() {
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of(ORDER_FILE_NAME));
            actual = readFromFile.read(ORDER_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException("Can't read information from the file" + ORDER_FILE_NAME);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_with_not_existing_file_notOk() {
        readFromFile.read(NOT_EXISTING_FILE_NAME);
        fail("Should throw RuntimeException when we try to read not existing file");
    }

    @Test(expected = RuntimeException.class)
    public void read_from_empty_file_notOk() {
        List<String> actual = readFromFile.read(EMPTY_ORDER_FILE_NAME);
        fail("Should throw an exception when we read empty file");
    }

    @Test(expected = RuntimeException.class)
    public void read_with_null_input_parameter_notOk() {
        readFromFile.read(null);
        fail("Should throw an exception when input parameter is null");
    }
}
