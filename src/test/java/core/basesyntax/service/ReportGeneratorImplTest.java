package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private String report;
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private List<FruitTransaction> fruitTransactionList = new ArrayList<>();

    @BeforeEach
    void before() {
        fruitTransaction.setQuantity(0);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransactionList.add(fruitTransaction);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity" + System.lineSeparator());
        stringBuilder.append("banana,0");
        report = stringBuilder.toString();
    }

    @Test
    void getReport_Ok() {
        assertEquals(report, reportGenerator.getReport(fruitTransactionList));
    }
}
