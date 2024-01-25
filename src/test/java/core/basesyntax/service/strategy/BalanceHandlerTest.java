package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitDao;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {

    @Test
    void balanceProcessOperationIs_Ok() {
        try {
            FruitDao fruitDaoMock = mock(FruitDao.class);
            List<String> testCsvData = Arrays.asList(
                    "p,apple,5",
                    "s,banana,10",
                    "p,orange,3",
                    "r,apple,2",
                    "s,orange,10",
                    "s,apple,10",
                    "s,banana,10",
                    "p,banana,3",
                    "p,apple,3"
            );
            when(fruitDaoMock.getCsv()).thenReturn(testCsvData);
            BalanceHandler balanceHandler = new BalanceHandler(fruitDaoMock);
            List<String> result = balanceHandler.processOperation();
            assertEquals(3, result.size());
            assertEquals("b,orange,7", result.get(0));
            assertEquals("b,banana,17", result.get(1));
            assertEquals("b,apple,4", result.get(2));
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }
}
