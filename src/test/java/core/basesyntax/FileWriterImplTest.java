package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static List<String> expectedList;
    private static final String VALID_REPORT = "fruit,quantity \n"
            + "banana,25\r\n"
            + "apple,25\r\n";
    private static final String PATH_OUTPUT = "src/test/outputTest.csv";
    private static final String WRONG_PATH_OUTPUT = "****";

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        expectedList = new ArrayList<>();
        expectedList.add("fruit,quantity ");
        expectedList.add("banana,25");
        expectedList.add("apple,25");
    }

    @Test
    public void writeDate_Ok() throws IOException {
        fileWriter.writeData(PATH_OUTPUT, VALID_REPORT);
        List<String> actual = Files.readAllLines(Path.of(PATH_OUTPUT));
        assertEquals(expectedList, actual);
    }

    @Test
    public void writeData_WrongPath_NotOk() throws IOException {
        Assertions.assertThrows(InvalidPathException.class,
                () -> fileWriter.writeData(WRONG_PATH_OUTPUT, VALID_REPORT));
    }
}
