package fruit.shop.tests;

import fruit.shop.service.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CsvFileTest {
    private static final String CSV_FOLDER_PATH = "src/test/java/Resources/";
    private static final ReaderServiceImpl READER_SERVICE = new ReaderServiceImpl();

    @Test
    public void file_empty_Ok() {
        String filePath = CSV_FOLDER_PATH + "NullBalance.csv";
        List<String> result = READER_SERVICE.readFromFile(filePath);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void file_absent_notOk() {
        String filePath = CSV_FOLDER_PATH + "null.csv";
        Assert.assertThrows(RuntimeException.class, () -> READER_SERVICE.readFromFile(filePath));
    }

    @Test
    public void file_correct_ok() {
        String filePath = CSV_FOLDER_PATH + "Balance.csv";
        List<String> result = READER_SERVICE.readFromFile(filePath);
        Assert.assertNotNull(result);
    }

}
