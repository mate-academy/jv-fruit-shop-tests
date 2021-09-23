package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.TransactionDto;
import core.basesyntax.service.impl.TransactionDtoParseImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionDtoParseImplTest {
    private static TransactionDtoParseImpl transactionDtoParse;

    @BeforeAll
    public static void beforeAll() {
        transactionDtoParse = new TransactionDtoParseImpl();
    }

    @Test
    public void parseData_ok() {
        List<String> dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("b,apple,1");
        List<TransactionDto> parsedData = new ArrayList<>();
        parsedData.add(new TransactionDto("b",
                new Fruit("apple"), 1));
        List<TransactionDto> actual = transactionDtoParse.parseData(dataList);
        Assertions.assertEquals(parsedData, actual);
    }

    @Test
    public void parseData_notOk() {
        List<String> dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("b,apple,0");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionDtoParse.parseData(dataList));
        List<String> dataList2 = new ArrayList<>();
        dataList2.add("type,fruit,quantity");
        dataList2.add("");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionDtoParse.parseData(dataList2));
        List<String> dataList3 = new ArrayList<>();
        dataList3.add("type,fruit,quantity");
        dataList3.add(null);
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionDtoParse.parseData(dataList3));
    }
}
