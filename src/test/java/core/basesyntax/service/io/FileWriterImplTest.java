package core.basesyntax.service.io;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_TO_TEST_INPUT_FILE = "src/resourcesTest/testActualReport.csv";
    private static final String TEST_DATA = "TEST_DATA";

    @Test
    public void fileWriterValid_Ok() {
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(PATH_TO_TEST_INPUT_FILE, TEST_DATA);
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readeFromFile(PATH_TO_TEST_INPUT_FILE);
        List<String> excepted = new ArrayList<>();
        excepted.add(TEST_DATA);
        assertEquals(excepted,actual);
    }
}
