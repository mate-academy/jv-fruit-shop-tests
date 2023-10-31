package core.basesyntax.serviceimpl;

import core.basesyntax.service.ConvertService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertServiceImplTest {
    private ConvertService convertService;

    @BeforeEach
    void setUp() {
        convertService = new ConvertServiceImpl();
    }

    @Test
    void convert_Ok() {
        List<String> inputList = Arrays.asList("b,Apple,10", "p,Banana,5");
        List<FruitTransaction> result = convertService.convert(inputList);
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "Apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Banana", 5)
        );
        Assertions.assertEquals(expected, result);
    }

    @Test
    void convert_NotOk() {
        List<String> inputList = Arrays.asList("b,Apple", "p,Banana,5");
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> convertService.convert(inputList));

    }
}
