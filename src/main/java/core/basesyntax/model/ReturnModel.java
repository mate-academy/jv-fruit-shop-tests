package core.basesyntax.model;

public interface ReturnModel {
    FruitModel getModel(String[] line);

    boolean elementIsEmptyOrNull(String element);
}
