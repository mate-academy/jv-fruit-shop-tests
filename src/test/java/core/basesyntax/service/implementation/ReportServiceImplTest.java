package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.OperationType;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final ReportService reportService = new ReportServiceImpl();
    private static final FruitRecordDto fruitRecordDto =
            new FruitRecordDto(OperationType.PURCHASE,"apple", 25);

    @Before
    public void cleanMapDB() {
        Storage.getFruits().clear();
    }

    @Test
    public void testApply_withDecreaseOperation_isOk() {
        Storage.getFruits().put("apple", 50);
        Storage.getFruits().put("banana", 12);
        String newQuantity = reportService.createReport(Storage.getFruits());
        assertEquals("fruit,quantity\nbanana,12\napple,50", newQuantity);
    }
}
