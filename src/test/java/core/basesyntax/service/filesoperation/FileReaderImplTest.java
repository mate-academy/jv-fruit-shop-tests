package core.basesyntax.service.filesoperation;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String NORMAL_FILE_PATH = "src/test/resources/input_file";
    private static final String INVALID_FILE_PATH = "src/test/resources/input_file1";
    private FileReader fileReader;

    public static void writeDataToFile() {
        StringBuilder data = new StringBuilder();
        data.append("type,fruit,quantity")
                .append(System.lineSeparator())
                .append("b,banana,20")
                .append(System.lineSeparator())
                .append("b,apple,100");
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(NORMAL_FILE_PATH))) {
            bufferedWriter.write(data.toString());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file "
                    + NORMAL_FILE_PATH, e);
        }
    }

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readNormalFilePath_Ok() {
        writeDataToFile();
        List<String> dailyTransactionList = fileReader.read(NORMAL_FILE_PATH);
        assertEquals(3,dailyTransactionList.size());
        assertEquals("type,fruit,quantity",dailyTransactionList.get(0));
        assertEquals("b,banana,20",dailyTransactionList.get(1));
        assertEquals("b,apple,100",dailyTransactionList.get(2));
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidFilePath_NotOk() {
        fileReader.read(INVALID_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidFilePathNull_NotOk() {
        fileReader.read(null);
    }
}
