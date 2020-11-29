package org.comrades.springtime.module.impl;

import org.comrades.springtime.dao.DotRepository;
import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.comrades.springtime.module.servise.DotService;
import org.springframework.beans.factory.annotation.Autowired;

public class DotServiceImpl implements DotService {
    
    private DotRepository dotRepository;

    @Autowired
    public DotServiceImpl(DotRepository dotRepository) {this.dotRepository = dotRepository; }

    @Override
    public Dot createDot(Long dotId, Integer x, Integer y, Integer r, Boolean isHit) {
        Dot dot = new Dot();

        dot.setDotId(dotId);
        dot.setX(x);
        dot.setY(y);
        dot.setR(r);
        dot.setHit(isHit);

        saveDot(dot);

        return dot;
    }

    @Override
    public void saveDot(Dot dot) {
        dotRepository.save(dot);
    }
}
