package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReportServiceImplTest {
    private ReportService reportService;
    private List<FruitTransaction> fruitTransactionList;
    private FruitTransaction bananaFruitTransaction;
    private FruitTransaction appleFruitTransaction;
    private static final String actualReport = "fruit,quantity\nbanana,50\napple,25\n";

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        fruitTransactionList = new ArrayList<>();
        bananaFruitTransaction = new FruitTransaction();
        bananaFruitTransaction.setFruit("banana");
        bananaFruitTransaction.setQuantity(50);
        appleFruitTransaction = new FruitTransaction();
        appleFruitTransaction.setFruit("apple");
        appleFruitTransaction.setQuantity(25);
    }

    @Test
    void createReport_ok() {
        fruitTransactionList.add(bananaFruitTransaction);
        fruitTransactionList.add(appleFruitTransaction);
        String extendReport = reportService.createReport(fruitTransactionList);
        assertEquals(actualReport, extendReport);
    }
}
