package core.basesyntax.date;

import static org.junit.Assert.assertEquals;

import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

class DateConverterImplTest {
    private DateConverter converter;

    @Before
    public void setUp() {
        converter = new DateConverterImpl();
    }

    @Test
    public void testConvertToTransaction_Ok() {
        List<String> rawData = new ArrayList<>();

        rawData.add("type,fruit,quantity");
        rawData.add("supply,banana,10");
        rawData.add("purchase,apple,3");

        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);

        assertEquals(2, resultList.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, resultList.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, resultList.get(1).getOperation());
        assertEquals("banana", resultList.get(0).getFruit());
        assertEquals("apple", resultList.get(1).getFruit());
        assertEquals(10, resultList.get(0).getAmount());
        assertEquals(3, resultList.get(1).getAmount());
    }

    @Test
    public void testConvertToTransaction_emptyInput_NotOk() {
        List<String> rawData = new ArrayList<>();
        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);

        assertEquals(0, resultList.size());
    }

    @Test
    public void testConvertToTransaction_nullInput_NotOk() {
        List<FruitTransaction> resultList = converter.convertToTransaction(null);

        assertEquals(0, resultList.size());
    }

    @Test
    public void testConvertToTransaction_invalidHeader_NotOk() {
        List<String> rawData = new ArrayList<>();

        rawData.add("fruit,apple");
        rawData.add("supply,banana,2");

        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);
        assertEquals(1, resultList.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, resultList.get(1).getOperation());
        assertEquals("banana", resultList.get(0).getFruit());
        assertEquals(2, resultList.get(1).getAmount());
    }

    @Test
    public void testConvertToTransaction_invalidOperation_NotOk() {
        List<String> rawData = new ArrayList<>();
        rawData.add("type,fruit,quantity");
        rawData.add("invalid,banana,10");

        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);
        assertEquals(0, resultList.size());
    }

    @Test
    public void testConvertToTransaction_emptyFruit_NotOk() {
        List<String> rawData = new ArrayList<>();
        rawData.add("type,fruit,quantity");
        rawData.add("supply,,12");

        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);
        assertEquals(0, resultList.size());
    }

    @Test
    public void testConvertToTransaction_negativeAmount_NotOk() {
        List<String> rawData = new ArrayList<>();
        rawData.add("type,fruit,quantity");
        rawData.add("supply,banana,-12");

        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);
        assertEquals(0, resultList.size());
    }

    @Test
    public void testConvertToTransaction_notNumberInAmount_NotOk() {
        List<String> rawData = new ArrayList<>();
        rawData.add("type,fruit,quantity");
        rawData.add("supply,banana,sda");

        List<FruitTransaction> resultList = converter.convertToTransaction(rawData);
        assertEquals(0, resultList.size());
    }
}
