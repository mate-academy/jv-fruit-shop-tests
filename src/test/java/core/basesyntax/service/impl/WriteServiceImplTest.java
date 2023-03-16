package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/output.csv";
    private static final String WRONG_FILE_PATH = "src/testM/resources/Input.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private WriteServiceImpl writeService;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notOk() {
        writeService.writeToFile(WRONG_FILE_PATH, REPORT);
    }

    @Test
    public void writeToFile_ok() {
        writeService.writeToFile(CORRECT_FILE_PATH,REPORT);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90"
        );
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(CORRECT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + CORRECT_FILE_PATH, e);
        }
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_NullArguments_notOk() {
        writeService.writeToFile(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeFirstArgumentNull_notOk() {
        writeService.writeToFile(null, CORRECT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void write_SecondArgumentNull_notOk() {
        writeService.writeToFile(REPORT, null);
    }
}
