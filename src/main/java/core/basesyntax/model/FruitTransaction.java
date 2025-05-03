package core.basesyntax.model;

public record FruitTransaction(
        String name,
        Operation operation,
        Integer quantity
) {
}
