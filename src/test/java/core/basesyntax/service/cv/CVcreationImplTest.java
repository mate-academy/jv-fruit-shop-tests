package core.basesyntax.service.cv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitDao;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CVcreationImplTest {
    private CVcreation cvCreation;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        fruitDao = mock(FruitDao.class);
        cvCreation = new CVcreationImpl(fruitDao);
    }

    @Test
    public void createCv_WithNonEmptyStorage_ReturnsCorrectCsv() {
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 120);
        expectedStorage.put("apple", 100);
        expectedStorage.put("orange", 8);

        when(fruitDao.getStorage()).thenReturn(expectedStorage);

        String actual = cvCreation.createCV();

        String[] lines = actual.split(System.lineSeparator());

        assertEquals(4, lines.length);
        assertTrue(actual.contains("fruit,quantity"));
        assertTrue(actual.contains("banana,120"));
        assertTrue(actual.contains("apple,100"));
        assertTrue(actual.contains("orange,8"));
    }

    @Test
    public void createCv_WithEmptyStorage_ReturnsHeaderOnly() {
        Map<String, Integer> emptyStorage = new HashMap<>();

        when(fruitDao.getStorage()).thenReturn(emptyStorage);

        String actual = cvCreation.createCV();

        String[] lines = actual.split(System.lineSeparator());

        assertEquals(1, lines.length);
        assertTrue(actual.contains("fruit,quantity"));
    }
}

