package core.basesyntax.service;

import core.basesyntax.model.dto.FruitDto;
import java.util.List;

public interface DataConverterService {
    List<FruitDto> convertDto(List<String> dataFromFile);
}
