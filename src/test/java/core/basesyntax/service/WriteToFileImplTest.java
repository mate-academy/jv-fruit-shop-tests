package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriteToFileImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileImplTest {
    private static WriteToFile writer;
    private static final String VALID_REPORT = "banana,50"
            + System.lineSeparator() + "apple,40";
    private static final String FILE_PATH_WRITE = "src/test/java/core"
            + "/basesyntax/resourses/report.csv";
    private static final String NULL_VALUE = null;
    private static List<String> expectedList;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriteToFileImpl();
        expectedList = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidPath_notOk() {
        writer.write(VALID_REPORT, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeInvalidData_notOk() {
        writer.write(NULL_VALUE, FILE_PATH_WRITE);
    }

    @Test
    public void writeValidParameters_ok() throws IOException {
        expectedList.add("banana,50");
        expectedList.add("apple,40");
        writer.write(VALID_REPORT, FILE_PATH_WRITE);
        List<String> actualList = Files.readAllLines(Path.of(
                "src/test/java/core/basesyntax/resourses/report.csv"));
        assertEquals(expectedList, actualList);
    }
}
