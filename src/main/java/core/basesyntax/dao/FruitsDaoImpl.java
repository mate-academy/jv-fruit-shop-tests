package core.basesyntax.dao;

import core.basesyntax.database.FruitDto;
import core.basesyntax.database.FruitsStorage;
import java.util.Objects;

public class FruitsDaoImpl implements FruitsDao {
    @Override
    public FruitDto get(String fruitName) {
        for (FruitDto fruitDto : FruitsStorage.fruitsStorage) {
            if (Objects.equals(fruitName, fruitDto.getName())) {
                return fruitDto;
            }
        }
        return null;
    }

    @Override
    public void put(FruitDto fruitDto) {
        if (fruitDto == null) {
            return;
        }
        FruitDto fruitDtoFromDB = get(fruitDto.getName());
        if (fruitDtoFromDB != null) {
            fruitDtoFromDB.setAmount(fruitDtoFromDB.getAmount() + fruitDto.getAmount());
        } else {
            FruitsStorage.fruitsStorage.add(fruitDto);
        }
    }
}
