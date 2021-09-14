package core.basesyntax.service.data;

import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvOperationParserTest {
    private static CsvOperationParser csvOperationParser;

    @BeforeClass
    public static void beforeAll() {
        csvOperationParser = new CsvOperationParser();
    }

    @Test
    public void formatData_correctData_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,10");
        data.add("b,apple,15");
        List<Operation> expected = new ArrayList<>();
        expected.add(new Operation(Operation.Type.BALANCE, "banana", 10));
        expected.add(new Operation(Operation.Type.BALANCE, "apple", 15));
        List<Operation> actual = csvOperationParser.formatData(data);
        Assert.assertEquals("Data was not parsed correctly!", expected, actual);
        data.add("p,apple,12");
        data.add("r,banana,14");
        expected.add(new Operation(Operation.Type.PURCHASE, "apple", 12));
        expected.add(new Operation(Operation.Type.RETURN, "banana", 14));
        actual = csvOperationParser.formatData(data);
        Assert.assertEquals("Data was not parsed correctly!", expected, actual);
    }

    @Test
    public void formatData_incorrectData_notOk() {
        List<String> data = new ArrayList<>();
        data.add("b,apple,-15");
        Assert.assertThrows("Wrong data was parsed, should throw exception",
                RuntimeException.class, () -> csvOperationParser.formatData(data));
        List<String> otherData = new ArrayList<>();
        otherData.add("b,apple");
        Assert.assertThrows("Wrong data was parsed, should throw exception",
                RuntimeException.class, () -> csvOperationParser.formatData(otherData));
    }
}
