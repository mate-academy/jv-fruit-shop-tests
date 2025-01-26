package core.basesyntax.convertor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConvertorTest {
    private static DataConvertor dataConvertor;

    @BeforeAll
    static void beforeAll() {
        dataConvertor = new DataConvertorImpl();
    }

    @Test
    void convertorOk() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "peach", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "grape", 3)
        );
        List<String> input = List.of("skip","b,peach,30", "r,grape,3");
        List<FruitTransaction> current = dataConvertor.convertToTransaction(input);

        assertEquals(expected, current);
        assertEquals(expected.get(0).toString(),current.get(0).toString());

    }

    @Test
    void convertorNotOk() {
        List<String> input = List.of("skip","incorrect data");
        assertThrows(IllegalArgumentException.class,() ->
                dataConvertor.convertToTransaction(input));
    }
}
