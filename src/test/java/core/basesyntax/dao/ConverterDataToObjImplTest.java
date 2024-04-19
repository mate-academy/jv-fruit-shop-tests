package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConverterDataToObjImplTest {
    private final ConverterDataToObjImpl converterDataToObj = new ConverterDataToObjImpl();
    private final List<String> fileData = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private final List<String> incorrectFileData = List.of("b,banana,20",
            "300,apple,100",
            "s,banana,gdsds");

    @Test
    void fruitTransactionInList_isOk() {
        assertSame(converterDataToObj.convertAll(fileData)
                .get(0).getClass(), FruitTransaction.class);

    }

    @Test
    void incorrectDataPattern_notOk() {
        assertThrows(RuntimeException.class, () ->
                converterDataToObj.convertAll(incorrectFileData));
    }
}
