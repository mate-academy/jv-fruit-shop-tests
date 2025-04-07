package core.basesyntax;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    public void setup() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_ok() {
        List<FruitTransaction> transactions =
                dataConverter.convertToTransaction(prepareTestInput());

        Assert.assertEquals(2, transactions.size());
        Assert.assertEquals("apple", transactions.get(0).getFruit());
        Assert.assertEquals(5, transactions.get(0).getAmount());
        Assert.assertEquals("banana", transactions.get(1).getFruit());
        Assert.assertEquals(100, transactions.get(1).getAmount());

    }

    @Test
    public void convertToTransaction_invalidLine_notOk() {
        List<String> inputData = prepareInvalidLineTest();

        IllegalArgumentException exception =
                Assert.assertThrows(IllegalArgumentException.class,() -> {
                    dataConverter.convertToTransaction(inputData);
                });

        Assert.assertTrue(exception.getMessage()
                .contains("Invalid line format:expected 3 parts but found "));
        Assert.assertTrue(exception.getMessage()
                .contains("Line:"));
    }

    @Test
    public void convertToTransaction_invalidQuantity_notOk() {
        List<String> inputData = prepareInvalidQuantityFormat();

        IllegalArgumentException exception =
                Assert.assertThrows(IllegalArgumentException.class,() -> {
                    dataConverter.convertToTransaction(inputData);
                });

        Assert.assertTrue(exception.getMessage().contains("Invalid quantity format:"));
        Assert.assertTrue(exception.getMessage().contains("expected an integer"));
    }

    @Test
    public void convertToTransaction_removeHeader_ok() {
        List<String> result = dataConverter.removeHeader(prepareRemoveHeader());

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("b,apple,5", result.get(0));
        Assert.assertEquals("s,banana,100", result.get(1));
    }

    private List<String> prepareTestInput() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,apple,5");
        inputData.add("s,banana,100");
        return inputData;
    }

    private List<String> prepareInvalidLineTest() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,b,apple,5");
        inputData.add("s,banana,100");
        return inputData;
    }

    private List<String> prepareInvalidQuantityFormat() {
        List<String> inputData = new ArrayList<>();
        inputData.add("b,apple,abc");
        inputData.add("s,banana,100");
        return inputData;
    }

    private List<String> prepareRemoveHeader() {
        List<String> inputData = new ArrayList<>();
        inputData.add("header");
        inputData.add("b,apple,5");
        inputData.add("s,banana,100");
        return inputData;
    }
}
