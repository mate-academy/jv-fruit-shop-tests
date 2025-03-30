package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitBalanceDao;
import core.basesyntax.model.FruitBalance;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportGeneratorImplTest {
    @Mock
    private FruitBalanceDao fruitBalanceDao;
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ReportGeneratorImpl(fruitBalanceDao);
    }

    @Test
    void getReport_emptyList_ok() {
        when(fruitBalanceDao.get()).thenReturn(List.of());
        String expected = "";
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_validFruitBalanceList_ok() {
        FruitBalance bananaBalance = new FruitBalance("banana", 100);
        FruitBalance appleBalance = new FruitBalance("apple", 50);
        FruitBalance kiwiBalance = new FruitBalance("kiwi", 40);
        when(fruitBalanceDao.get()).thenReturn(List.of(bananaBalance, appleBalance, kiwiBalance));
        String expected = String.join(System.lineSeparator(),
                "banana,100", "apple,50", "kiwi,40");
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }
}
