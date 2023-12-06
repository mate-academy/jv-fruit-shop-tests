package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.file.actions.ParserFile;
import core.basesyntax.service.file.actions.impl.ParserFileImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParserFileTest {
    private static final List<String> CORRECT_TRANSACTIONS_STRING = List
            .of("b,banana,20", "s,banana,100", "p,banana,5");
    private static final List<String> WRONG_TRANSACTION = List
            .of("b,banana,20", "L,banana,100", "p,banana,5");
    private static final FruitTransaction BALANCE_TXN = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction SUPPLY_TXN = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static final FruitTransaction PURCHASE_TXN = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE, "banana", 5);
    private static final List<FruitTransaction> CORRECT_TRANSACTIONS = List
            .of(BALANCE_TXN, SUPPLY_TXN, PURCHASE_TXN);
    private ParserFile parser = new ParserFileImpl();

    @Test
    public void parserFruitShop_correctData_ok() {
        Assert.assertEquals(CORRECT_TRANSACTIONS,
                parser.parseFruitShop(CORRECT_TRANSACTIONS_STRING));
    }

    @Test
    public void parserFruitShop_wrongData_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> parser.parseFruitShop(WRONG_TRANSACTION));
    }
}
