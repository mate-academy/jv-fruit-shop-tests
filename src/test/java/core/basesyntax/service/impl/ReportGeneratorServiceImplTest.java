package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private final ReportGeneratorService reportGeneratorService
            = new ReportGeneratorServiceImpl();
    private final FruitDao dao = new FruitDaoImpl();
    private List<FruitTransaction> transactionsList;

    @Before
    public void setUp() {
        transactionsList = dao.get();
    }

    @Test
    public void generate_Ok() {
        reportGeneratorService.generate(transactionsList);
    }

    @Test(expected = RuntimeException.class)
    public void checkEmptyList_Ok() {
        List<FruitTransaction> testList = new ArrayList<>();
        reportGeneratorService.generate(testList);
    }
}