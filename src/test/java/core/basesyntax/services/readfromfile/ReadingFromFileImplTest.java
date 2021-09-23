package core.basesyntax.services.readfromfile;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReadingFromFileImplTest {
    @Test(expected = ReadingException.class)
    public void readingFromFile_FileIsNotExist_NotOk() {
        String filePath = "src/test/resources/file0.csv";
        ReadingFromFile readingFromFile = new ReadingFromFileImpl();
        readingFromFile.readingFromFile(filePath);
    }

    @Test
    public void readingFromFile_FileIsOk_Ok() {
        String filePath = "src/test/resources/file.csv";
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto("b", "banana", 20));
        expected.add(new TransactionDto("s", "banana", 100));
        expected.add(new TransactionDto("p", "banana", 70));
        expected.add(new TransactionDto("r", "banana", 50));
        ReadingFromFile readingFromFile = new ReadingFromFileImpl();
        List<TransactionDto> actual = readingFromFile.readingFromFile(filePath);
        assertEquals(expected, actual);
    }
}
