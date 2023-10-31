package fruitshop.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.model.FruitTransaction;
import fruitshop.model.Operation;
import fruitshop.service.DataParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    @Test
    void parseStringToDataObject_validProcessStringToObject_ok() {
        DataParser dataParser = new DataParserImpl();
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,banana,13");
        stringList.add("r,apple,10");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10)
        );
        List<FruitTransaction> actual = dataParser.parseStringToDataObject(stringList);
        assertEquals(expected, actual);
    }

    @Test
    void parseStringToDataObject_sizesEquals_ok() {
        DataParser dataParser = new DataParserImpl();
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,banana,13");
        stringList.add("r,apple,10");
        int expected = stringList.size() - 1;
        int actual = dataParser.parseStringToDataObject(stringList).size();
        assertEquals(expected, actual);
    }

    @Test
    void parseStringToDataObject_emptyListAsParameter_ok() {
        DataParser dataParser = new DataParserImpl();
        List<String> stringList = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = dataParser.parseStringToDataObject(stringList);
        assertEquals(expected, actual);
    }
}
