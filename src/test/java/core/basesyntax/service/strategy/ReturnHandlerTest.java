package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitDao;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    @Test
    void returnProcessOperationIs_Ok() {
        FruitDao fruitDaoMock = mock(FruitDao.class);
        List<String> testCsvData = Arrays.asList(
                "p,apple,5",
                "s,banana,10",
                "p,orange,3",
                "r,apple,2",
                "s,orange,10",
                "s,apple,10",
                "r,banana,2",
                "p,banana,3",
                "p,apple,3"
        );
        when(fruitDaoMock.getCsv()).thenReturn(testCsvData);
        ReturnHandler returnHandler = new ReturnHandler(fruitDaoMock);
        List<String> result = returnHandler.processOperation();
        assertEquals(2, result.size());
        assertEquals("r,banana,2", result.get(0));
        assertEquals("r,apple,2", result.get(1));
    }
}
