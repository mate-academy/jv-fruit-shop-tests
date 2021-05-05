package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.dao.FruitDaoImpl;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.interfaces.OperationHandler;
import org.junit.Test;

public class AddOperationHandlerTest {
    private final OperationHandler operationHandler = new AddOperationHandler(new FruitDaoImpl());

    @Test
    public void add_success() {
        FruitRecordDto recordDto = new FruitRecordDto(Operation.BALANCE, "orange", 25);
        int actual = operationHandler.apply(recordDto);
        int expected = 25;
        assertEquals(expected, actual);
    }
}
