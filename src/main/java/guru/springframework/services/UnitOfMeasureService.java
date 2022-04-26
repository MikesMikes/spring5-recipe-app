package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUomsAsCommand();
}
