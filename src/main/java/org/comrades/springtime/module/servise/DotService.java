package org.comrades.springtime.module.servise;

import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.springframework.data.repository.CrudRepository;

public interface DotService {

    Dot createDot(Long dotId, Integer x, Integer y, Integer r, Boolean isHit);
    void saveDot(Dot dot);
}
