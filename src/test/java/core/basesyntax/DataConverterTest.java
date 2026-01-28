package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConvert;
import core.basesyntax.serviceimpl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private DataConvert dataConvert;
    private final List<String> transactionList = List.of(
            "type,fruit,quantity", "b,banana,20",
            "s,banana,100", "p,banana,13", "r,apple,10");

    @BeforeEach
    void setUp() {
        dataConvert = new DataConverterImpl();
    }

    @Test
    void convertFile_ok() {
        List<FruitTransaction> convert = dataConvert.convert(transactionList);
        Assertions.assertFalse(convert.isEmpty());
        Assertions.assertEquals(convert.size(), transactionList.size() - 1);
    }

    @Test
    void convertEmptyList_NotOk() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> dataConvert.convert(List.of()));
        Assertions.assertTrue(exception.getMessage().contains("can't be empty"));
    }

    @Test
    void convertNullList_NotOk() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> dataConvert.convert(null));

        Assertions.assertTrue(exception.getMessage().contains("can't be null"));
    }

    @Test
    void convertLineLengthsFour_NotOk() {
        List<String> transactionListLengthFourElement = List.of(
                "type,fruit,quantity", "b,banana,20,45",
                "s,banana,100", "p,banana,13", "r,apple,10");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> dataConvert.convert(transactionListLengthFourElement));
        Assertions.assertTrue(exception.getMessage().contains("Invalid format"));
    }

    @Test
    void setOperationNullValue_NotOk() {
        List<String> transactionListIncorrectType = List.of(
                "type,fruit,quantity", "b,banana,20",
                "yt,banana,100", "p,banana,13", "s,apple,10");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> dataConvert.convert(transactionListIncorrectType));
        Assertions.assertTrue(exception.getMessage().contains("Invalid operation"));
    }
}
