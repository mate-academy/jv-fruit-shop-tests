package core.basesyntax.service.impl;

import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.Transformation;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FruitDtoTransformation implements Transformation<String, FruitOperationDto> {

    @Override
    public List<FruitOperationDto> transform(List<String> dataFromFile) {
        Function<String, FruitOperationDto> mapper = new FruitOperationDtoMapper();
        return dataFromFile.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
