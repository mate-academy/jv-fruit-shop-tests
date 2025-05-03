package core.basesyntax.strategy.impl;

import core.basesyntax.services.ParametrsValidatorService;
import core.basesyntax.strategy.OperationHandler;
import java.util.function.IntUnaryOperator;

public class OperationHandlerReturn implements OperationHandler {
    @Override
    public IntUnaryOperator changeSaldo(int saldo) {
        return IntUnaryOperator.identity().andThen(quantity -> {
                    ParametrsValidatorService.isParametrPositive(saldo, quantity);
                    return saldo + quantity;
                }
        );
    }
}
