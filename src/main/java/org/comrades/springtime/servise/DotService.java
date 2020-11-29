package org.comrades.springtime.servise;

import org.comrades.springtime.module.Dot;

public interface DotService {

    Dot createDot(Long dotId, Integer x, Integer y, Integer r, Boolean isHit);
    void saveDot(Dot dot);
}
