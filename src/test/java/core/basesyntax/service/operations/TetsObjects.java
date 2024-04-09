package core.basesyntax.service.operations;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Fruit;

public class TetsObjects {
    static final Fruit fruit = new Fruit("apple");
    static final FruitTransactionDto VALID_BALANCE_DTO = new FruitTransactionDto(
            "b", "apple", 10);
    static final FruitTransactionDto INVALID_BALANCE_DTO = new FruitTransactionDto(
            "s", "apple", 10);
    static final FruitTransactionDto VALID_PURCHASE_DTO = new FruitTransactionDto(
            "p", "apple", 5);
    static final FruitTransactionDto INVALID_PURCHASE_DTO = new FruitTransactionDto(
            "x", "apple", 5);
    static final FruitTransactionDto INVALID_QUANTITY_PURCHASE_DTO = new FruitTransactionDto(
            "p", "apple", 15);
    static final FruitTransactionDto VALID_RETURN_DTO = new FruitTransactionDto(
            "r", "apple", 5);
    static final FruitTransactionDto INVALID_RETURN_DTO = new FruitTransactionDto(
            "z", "apple", 5);
    static final FruitTransactionDto VALID_SUPPLY_DTO = new FruitTransactionDto(
            "s", "apple", 20);
    static final FruitTransactionDto INVALID_SUPPLY_DTO = new FruitTransactionDto(
            "e", "apple", 1);
}
