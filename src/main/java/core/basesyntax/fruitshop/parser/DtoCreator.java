package core.basesyntax.fruitshop.parser;

import core.basesyntax.fruitshop.model.RecordDto;

import java.util.List;

public interface DtoCreator {
    List<RecordDto> toDtoDataFormatter(List<String> rawRecords);
}
