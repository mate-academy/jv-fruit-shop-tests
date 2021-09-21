package core.basesyntax.services.readfromfile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReadingFromFileImplTest {
    @Test
    public void readingFromFile_FileIsNotExist_NotOk() {
        String filePath = "src/test/resources/file0.csv";
        boolean haveException = false;
        ReadingFromFile readingFromFile = new ReadingFromFileImpl();
        try {
            List<TransactionDto> transactionDtoList = readingFromFile.readingFromFile(filePath);
        } catch (ReadingException e) {
            haveException = true;
        }
        assertTrue(haveException);
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
        assertEquals(expected.get(0).getOperationType(), actual.get(0).getOperationType());
        assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
        assertEquals(expected.get(0).getAmount(), actual.get(0).getAmount());
        assertEquals(expected.get(1).getOperationType(), actual.get(1).getOperationType());
        assertEquals(expected.get(1).getFruit(), actual.get(1).getFruit());
        assertEquals(expected.get(1).getAmount(), actual.get(1).getAmount());
        assertEquals(expected.get(2).getOperationType(), actual.get(2).getOperationType());
        assertEquals(expected.get(2).getFruit(), actual.get(2).getFruit());
        assertEquals(expected.get(2).getAmount(), actual.get(2).getAmount());
        assertEquals(expected.get(3).getOperationType(), actual.get(3).getOperationType());
        assertEquals(expected.get(3).getFruit(), actual.get(3).getFruit());
        assertEquals(expected.get(3).getAmount(), actual.get(3).getAmount());
    }
}
