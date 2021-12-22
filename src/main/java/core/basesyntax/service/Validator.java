package core.basesyntax.service;

public interface Validator {
    boolean checkOperation(int quantity);

    boolean checkInputData(String[] data);
}
