package core.basesyntax.converter;

import core.basesyntax.dao.Fruit;
import java.util.ArrayList;
import java.util.List;

public class StringToFruitConverterImpl implements Converter<String, Fruit> {

    @Override
    public Fruit convert(String s) {
        StringToFruitValidator validator = new StringToFruitValidator();
        String[] arr = s.split(",");
        if (!validator.validateInputData(arr)) {
            throw new IllegalArgumentException("Error while parsing string [" + s + "]");
        }
        final int indexOfName = 1;
        final int indexOfQuantity = 2;
        return new Fruit(arr[indexOfName], Integer.parseInt(arr[indexOfQuantity]));
    }

    @Override
    public List<Fruit> convertList(List<String> list) {
        List<Fruit> resultList = new ArrayList<>();
        for (String s : list) {
            resultList.add(convert(s));
        }
        return resultList;
    }
}
