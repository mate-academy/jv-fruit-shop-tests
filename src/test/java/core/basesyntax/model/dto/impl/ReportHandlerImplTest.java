package core.basesyntax.model.dto.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.impl.AddHandlerImpl;
import core.basesyntax.service.impl.OperationType;
import core.basesyntax.storage.DataBase;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportHandlerImplTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String COLUMN_SEPARATOR = ",";
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static Map<String, Integer> map;
    private static ReportHandlerImpl reportHandler;
    private static FruitRecordDto fruitRecordDto;

    @BeforeClass
    public static void beforeClass() {
        map = DataBase.getDataBase();
        reportHandler = new ReportHandlerImpl(map);
    }

    @AfterClass
    public static void afterClass() {
        DataBase.getDataBase().clear();
    }

    @Test
    public void makeReportTest_Ok() {
        fruitRecordDto
                = new FruitRecordDto(OperationType.BALANCE, "banana", 25);
        addHandler.applyFruitToStorage(fruitRecordDto);
        String actual = reportHandler.makeReport();
        String expected = FIRST_LINE
                + System.lineSeparator()
                + fruitRecordDto.getName()
                + COLUMN_SEPARATOR
                + fruitRecordDto.getAmount()
                + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }
}
