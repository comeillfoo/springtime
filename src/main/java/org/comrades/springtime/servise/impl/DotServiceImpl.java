package org.comrades.springtime.servise.impl;

import org.comrades.springtime.dao.DotRepository;
import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.DotService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DotServiceImpl implements DotService {
    
    private DotRepository dotRepository;

    @Autowired
    public DotServiceImpl(DotRepository dotRepository) {this.dotRepository = dotRepository; }

    @Override
    public List<Dot> findDotsByUser(User user) {
        return dotRepository.findDotsByUser(user);
    }

    @Override
    public Dot saveDot(Dot dot) {
        return dotRepository.save(dot);
    }
}
