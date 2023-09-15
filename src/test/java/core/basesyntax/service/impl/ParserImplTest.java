package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserImplTest {
    ParserImpl parser = new ParserImpl();

    @Test
    void parseInput_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parseInput(null));
    }

    @Test
    void parseInput_emptyInput_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parseInput(new ArrayList<>()));
    }

    @Test
    void parseInput_wrongInput_notOk() {
        List<String> wrongInput = new ArrayList<>();
        wrongInput.add("b banana 19");
        assertThrows(RuntimeException.class, () -> parser.parseInput(wrongInput));
    }

    @Test
    void parseInput_wrongNumberOfCommas_notOk() {
        List<String> wrongInput = new ArrayList<>();
        wrongInput.add("b,banana 19");
        assertThrows(RuntimeException.class, () -> parser.parseInput(wrongInput));
    }

    @Test
    void parseInput_ok() {
        FruitTransaction transaction;
        List<String> input = new ArrayList<>();
        input.add("b,banana,20");
        List<FruitTransaction> fruitTransactions = parser.parseInput(input);
        transaction = fruitTransactions.get(0);
        assertEquals(transaction.getOperation(), Operation.BALANCE);
        assertEquals(transaction.getFruit(), "banana");
        assertEquals(transaction.getAmount(), 20);

        input.add("s,apple,10");
        fruitTransactions = parser.parseInput(input);
        transaction = fruitTransactions.get(1);
        assertEquals(transaction.getOperation(), Operation.SUPPLY);
        assertEquals(transaction.getFruit(), "apple");
        assertEquals(transaction.getAmount(), 10);
    }
}