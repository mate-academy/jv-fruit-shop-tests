package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.service.WriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String FILE_NAME = "src/test/resources/forDaoTests.csv";
    private static final String DEFAULT_DATA_FOR_WRITE = "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";
    private static final List<String> DEFAULT_DATA_FROM_FILE = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeFileNameNull_NotOk() {
        writerService.writeToFile(null, "");
    }

    @Test(expected = RuntimeException.class)
    public void writerService_writeFileWithNullReport_NotOk() {
        writerService.writeToFile(FILE_NAME, null);
    }

    @Test
    public void writerService_equalsDataFromFile() {
        writerService.writeToFile(FILE_NAME, DEFAULT_DATA_FOR_WRITE);
        List<String> actual = readFromFile();
        assertEquals(DEFAULT_DATA_FROM_FILE, actual);
    }

    private List<String> readFromFile() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + FILE_NAME, e);
        }
        return lines;
    }
}
