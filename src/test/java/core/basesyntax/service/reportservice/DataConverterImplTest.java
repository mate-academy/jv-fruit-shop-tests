package core.basesyntax.service.reportservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;
    private static final List<String> VALID_REPORT_TO_CONVERT = List.of(
            "type,fruit,quantity",
            "b,banana,20", "b,apple,100", "s,banana,100",
            "r,apple,10", "p,apple,20", "p,banana,5"
    );

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_emptyReport_notOk() {
        List<String> emptyReport = new ArrayList<>();
        String expectedMessage = "Input file does not contain data: " + emptyReport;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(emptyReport));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convertToTransaction_onlyHeaderReport_notOk() {
        List<String> onlyHeaderReport = List.of("type,fruit,quantity");
        String expectedMessage = "Input file does not contain data: " + onlyHeaderReport;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(onlyHeaderReport));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convertToTransaction_invalidReport_notOk() {
        List<String> invalidReport = List.of("type,fruit,quantity", "50,mango,s",
                "banana,40,p", "p,apple,10");
        String expectedMessage = "Input report contains wrong row(s)";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidReport));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convertToTransaction_validReport_ok() {
        FruitTransaction balanceBanana = new FruitTransaction();
        balanceBanana.setOperation(FruitTransaction.Operation.BALANCE);
        balanceBanana.setFruit("banana");
        balanceBanana.setQuantity(20);
        FruitTransaction balanceApple = new FruitTransaction();
        balanceApple.setOperation(FruitTransaction.Operation.BALANCE);
        balanceApple.setFruit("apple");
        balanceApple.setQuantity(100);
        FruitTransaction supplyBanana = new FruitTransaction();
        supplyBanana.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyBanana.setFruit("banana");
        supplyBanana.setQuantity(100);
        FruitTransaction returnApple = new FruitTransaction();
        returnApple.setOperation(FruitTransaction.Operation.RETURN);
        returnApple.setFruit("apple");
        returnApple.setQuantity(10);
        FruitTransaction purchaseApple = new FruitTransaction();
        purchaseApple.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseApple.setFruit("apple");
        purchaseApple.setQuantity(20);
        FruitTransaction purchaseBanana = new FruitTransaction();
        purchaseBanana.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseBanana.setFruit("banana");
        purchaseBanana.setQuantity(5);
        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(balanceBanana);
        expectedTransactions.add(balanceApple);
        expectedTransactions.add(supplyBanana);
        expectedTransactions.add(returnApple);
        expectedTransactions.add(purchaseApple);
        expectedTransactions.add(purchaseBanana);
        assertEquals(expectedTransactions,
                dataConverter.convertToTransaction(VALID_REPORT_TO_CONVERT));
    }

}
