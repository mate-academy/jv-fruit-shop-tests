package model;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void getCodeForOperation_ok() {
        final List<Operation> operationList = List.of(
                Operation.RETURN,
                Operation.SUPPLY,
                Operation.BALANCE,
                Operation.PURCHASE
                );

        Assertions.assertEquals("r", operationList.get(0).getCode());
        Assertions.assertEquals("s", operationList.get(1).getCode());
        Assertions.assertEquals("b", operationList.get(2).getCode());
        Assertions.assertEquals("p", operationList.get(3).getCode());
    }

    @Test
    void getByCodeOperation_ok() {
        final List<String> codesOfOperationsList
                = List.of("b", "r", "s", "p");

        Assertions.assertEquals(Operation.BALANCE,
                Operation.getByCode(codesOfOperationsList.get(0)));
        Assertions.assertEquals(Operation.RETURN,
                Operation.getByCode(codesOfOperationsList.get(1)));
        Assertions.assertEquals(Operation.SUPPLY,
                Operation.getByCode(codesOfOperationsList.get(2)));
        Assertions.assertEquals(Operation.PURCHASE,
                Operation.getByCode(codesOfOperationsList.get(3)));
    }

    @Test
    void getByCodeOperationForUnknownCode_notOk() {
        final String codeOfUnknownOperation = "c";

        Assertions.assertThrows(RuntimeException.class,
                () -> Operation.getByCode(codeOfUnknownOperation));
    }
}
