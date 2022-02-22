package fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import fruitshop.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/output.txt";
    private static final String INCORRECT_PATH = "";
    private static List<String> expected;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private WriterService writerService = new WriterServiceImpl();
    private byte[] data;

    @BeforeClass
    public static void beforeClass() {
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
    }

    @Test
    public void writeToFile_correctDataAndPath_ok() {
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(CORRECT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_incorrectData_notOk() {
        data = null;
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Data is null");
        writerService.writeToFile(data, CORRECT_PATH);
    }

    @Test
    public void writeToFile_incorrectPath_notOk() {
        data = new byte[]{102, 114, 117, 105, 116, 44, 113, 117, 97, 110, 116, 105, 116, 121, 13,
                10, 98, 97, 110, 97, 110, 97, 44, 49, 53, 50, 13, 10, 97, 112, 112, 108, 101, 44,
                57, 48};
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't write data to file");
        writerService.writeToFile(data, INCORRECT_PATH);
    }
}
