package basesyntax.service.data;

import basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserImplTest {
    private static DataParserImpl dataParserImpl;

    @BeforeClass
    public static void beforeAll() {
        dataParserImpl = new DataParserImpl();
    }

    @Test
    public void formatData_correctData_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,10");
        data.add("b,apple,15");
        List<FruitRecordDto> expected = new ArrayList<>();
        List<FruitRecordDto> actual = new ArrayList<>();
        try {
            expected.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "banana", 10));
            expected.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "apple", 15));
            actual = dataParserImpl.formatData(data);
        } catch (RuntimeException e) {
            Assert.assertEquals("Data was not parsed correctly!", expected, actual);
        }
        data.add("p,apple,12");
        data.add("r,banana,14");
        try {
            expected.add(new FruitRecordDto(FruitRecordDto.Type.PURCHASE, "apple", 12));
            expected.add(new FruitRecordDto(FruitRecordDto.Type.RETURN, "banana", 14));
            actual = dataParserImpl.formatData(data);
        } catch (RuntimeException e) {
            Assert.assertEquals("Data was not parsed correctly!", expected, actual);
        }
    }

    @Test
    public void formatData_incorrectData_notOk() {
        List<String> data = new ArrayList<>();
        data.add("b,apple,-15");
        try {
            dataParserImpl.formatData(data);
        } catch (RuntimeException e) {
            throw new RuntimeException("Data is not valid: " + e);
        }
        List<String> otherData = new ArrayList<>();
        otherData.add("b,apple");
        try {
            dataParserImpl.formatData(otherData);
        } catch (RuntimeException e) {
            throw new RuntimeException("Data is not valid: " + e);
        }
    }
}
