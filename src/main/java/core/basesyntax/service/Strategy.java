package core.basesyntax.service;

public interface Strategy {
    OperationHandler getActivity(String storeActivityType);
}
