package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.service.ReaderService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_NAME = "src/test/resources/forDaoTests.csv";
    private static final List<String> DEFAULT_DATA_FROM_FILE = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final String DEFAULT_DATA = "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Before
    public void setUp() {
        writeDefaultDataToFile();
    }
    
    @Test
    public void readerService_equalsReadDataFromFile_Ok() {
        List<String> actual = readerService.readFromFile(FILE_NAME);
        assertEquals(DEFAULT_DATA_FROM_FILE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_wrongFileName_NotOk() {
        readerService.readFromFile("qwe");
    }

    @Test(expected = RuntimeException.class)
    public void readerService_nullFileName_NotOk() {
        readerService.readFromFile(null);
    }

    private void writeDefaultDataToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bufferedWriter.write(DEFAULT_DATA);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file " + FILE_NAME, e);
        }
    }
}
