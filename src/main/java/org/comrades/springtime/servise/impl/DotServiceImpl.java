package org.comrades.springtime.servise.impl;

import org.comrades.springtime.dao.DotRepository;
import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.DotBaseEntity;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.DotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DotServiceImpl implements DotService {
    
    private DotRepository dotRepository;

    @Autowired
    public DotServiceImpl(DotRepository dotRepository) {this.dotRepository = dotRepository; }

    @Override
    public List<Dot> getDotsByUser(User user) {
        return dotRepository.findDotsByUser(user);
    }

    @Override
    public boolean checkDots(Dot dot) {
        Integer x = dot.getX();
        Double y = dot.getY();
        Integer r = dot.getR();

        if (x >= 0 && y >= 0 && (-x + r >= y)) {
            dot.setIsHit(true);
            return true;
        }
        if (x <= 0 && y <= 0 && x >= r && y >= r/2) {
            dot.setIsHit(true);
            return true;
        }else if (x >= 0 && y <= 0 && (Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r/2, 2)) {
            dot.setIsHit(true);
            return true;
        }else {
            dot.setIsHit(false);
            return false;
        }
    }

    @Override
    public Dot validate(DotBaseEntity dot) throws NumberFormatException {
        Integer x = dot.getX();
        Double y = dot.getY();
        Integer r = dot.getR();

        if (x == null || y == null || r == null) {
            throw new NullPointerException("Parameters shouldn't be null");
        }

        if (y < -5 || y > 5) {
            throw new NumberFormatException("y should be {-5 ; 5}");
        }

        return null;
    }

    @Override
    public boolean clearByUser(User user) {
        return dotRepository.clearByUser(user);
    }


    @Override
    public Dot saveDot(Dot dot) {
        return dotRepository.save(dot);
    }
}
