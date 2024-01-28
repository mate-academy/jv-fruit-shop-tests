package core.basesyntax.service.separateservice.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.separateservice.ParsingTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParsingTransactionServiceImplTest {
    private final ParsingTransactionService parsingTransactionService
            = new ParsingTransactionServiceImpl();
    private List<FruitTransaction> listFromString;

    @BeforeEach
    void setUp() {
        listFromString = new ArrayList<>();
    }

    @Test
    void parsingStringWithNotCorrectEnumCode_ok() {
        String emptyString = "w, orange, 22";
        Assert.assertThrows(RuntimeException.class,
                () -> parsingTransactionService.parsingTransaction(listFromString, emptyString));
    }

    @Test
    void parsingStringWithCorrectInputValue_ok() {
        String value = "s,banana,100";
        parsingTransactionService.parsingTransaction(listFromString, value);
        assertFalse(listFromString.isEmpty());
    }
}
