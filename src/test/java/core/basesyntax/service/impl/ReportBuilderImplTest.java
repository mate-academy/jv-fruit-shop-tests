package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReportBuilder;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturningOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportBuilderImplTest {
    private static final String EXPECTED_REPORT = """
            fruit,quantity
            banana,109
            apple,109""";
    private final List<String> expectedReaderResult = List.of(
            " type,fruit,quantity",
            "    b,banana,100",
            "    b,apple,100",
            "    s,banana,10",
            "    s,apple,10",
            "    r,banana,10",
            "    r,apple,10",
            "    p,banana,11",
            "    p,apple,11");
    private final OperationHandler balance = new BalanceOperationHandler();
    private final OperationHandler supply = new SupplyOperationHandler();
    private final OperationHandler purchase = new PurchaseOperationHandler();
    private final OperationHandler returning = new ReturningOperationHandler();
    private final List<OperationHandler> goodOperationList = List.of(
            balance, supply, purchase, returning);
    private final ReportBuilder reportBuilder = new ReportBuilderImpl();
    private final ParserService<String> parserService = new FruitTransactionMapper();
    private final List<FruitTransaction> expectedOutAfterParsing =
            parserService.parse(expectedReaderResult);

    @Test
    void reportBuilder_Ok_Equals() {
        Storage.dataBase.put("banana", 109);
        Storage.dataBase.put("apple", 109);
        String report = reportBuilder.build(Storage.dataBase);
        assertEquals(EXPECTED_REPORT, report);
    }
}
