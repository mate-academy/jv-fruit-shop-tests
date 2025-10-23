package core.basesyntax.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import java.util.Objects;

public class FruitDto {
    @CsvCustomBindByName(column = "type", converter = OperationConverter.class)
    private FruitTransaction.Operation operation;
    @CsvBindByName
    private String fruit;
    @CsvBindByName
    private int quantity;

    public FruitDto() {
    }

    public FruitDto(FruitTransaction.Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public FruitTransaction.Operation getOperation() {
        return operation;
    }

    public void setOperation(FruitTransaction.Operation operation) {
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitDto)) {
            return false;
        }
        FruitDto fruitDto = (FruitDto) o;
        return quantity == fruitDto.quantity
                && operation == fruitDto.operation
                && Objects.equals(fruit, fruitDto.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
