package core.basesyntax.service.io;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_TO_TEST_INPUT_FILE = "src/resourcesTest/testActualReport.csv";
    private static final String PATH_TO_NOT_EXIST_TEST_INPUT_FILE = "";
    private static final String TEST_DATA = "TEST_DATA";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void fileWriter_Ok() {
        fileWriter.writeToFile(PATH_TO_TEST_INPUT_FILE, TEST_DATA);
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readeFromFile(PATH_TO_TEST_INPUT_FILE);
        List<String> excepted = new ArrayList<>();
        excepted.add(TEST_DATA);
        assertEquals(excepted,actual);
    }

    @Test
    public void fileWriter_NotValidPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(PATH_TO_NOT_EXIST_TEST_INPUT_FILE, TEST_DATA));
    }

}
