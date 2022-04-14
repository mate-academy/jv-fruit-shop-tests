package core.basesyntax.services.data;

import core.basesyntax.model.TransactionDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserCsvTest {
    private static ParserCsv dataParser;

    @BeforeClass
    public static void beforeAll() {
        dataParser = new ParserCsv();
    }

    @Test
    public void formatData_correctData_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,10");
        data.add("b,apple,15");
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto(TransactionDto.Type.BALANCE, "banana", 10));
        expected.add(new TransactionDto(TransactionDto.Type.BALANCE, "apple", 15));
        List<TransactionDto> actual = dataParser.formatData(data);
        Assert.assertEquals("Data parsed incorrectly!", expected.size(), actual.size());
        data.add("p,apple,12");
        data.add("r,banana,14");
        expected.add(new TransactionDto(TransactionDto.Type.PURCHASE, "apple", 12));
        expected.add(new TransactionDto(TransactionDto.Type.RETURN, "banana", 14));
        actual = dataParser.formatData(data);
        Assert.assertEquals("Data parsed incorrectly!", expected.size(), actual.size());
    }

    @Test
    public void formatData_incorrectData_notOk() {
        List<String> data = new ArrayList<>();
        data.add("b,apple,-15");
        Assert.assertThrows("Wrong data was parsed, should throw exception",
                RuntimeException.class, () -> dataParser.formatData(data));
        List<String> otherData = new ArrayList<>();
        otherData.add("b,apple");
        Assert.assertThrows("Wrong data was parsed, should throw exception",
                RuntimeException.class, () -> dataParser.formatData(otherData));
    }
}
