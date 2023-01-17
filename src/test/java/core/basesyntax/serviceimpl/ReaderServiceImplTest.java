package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static String filePath;
    private static ReaderServiceImpl readerService;

    private final List<String> defaultDataFromFile = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private final String defaultData =
            "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator()
                    + "s,banana,100" + System.lineSeparator()
                    + "p,banana,13" + System.lineSeparator()
                    + "r,apple,10" + System.lineSeparator()
                    + "p,apple,20" + System.lineSeparator()
                    + "p,banana,5" + System.lineSeparator()
                    + "s,banana,50";

    @BeforeClass
    public static void beforeClass() {
        filePath = "src\\test\\result.csv";
        readerService = new ReaderServiceImpl();
    }

    @Before
    public void setUp() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(defaultData);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file " + filePath, e);
        }
    }

    @Test
    public void readFromFile_equalsReadDataFromFile_ok() {
        List<String> actual = readerService.readFromFile(filePath);
        assertEquals(defaultDataFromFile, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongFileName_notOk() {
        readerService.readFromFile("randomFile");
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullFileName_notOk() {
        readerService.readFromFile(null);
    }
}
