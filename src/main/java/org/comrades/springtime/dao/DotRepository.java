package org.comrades.springtime.dao;

import org.comrades.springtime.module.Dot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DotRepository extends JpaRepository<Dot, Long> {
    Dot findByDotId(Long dotId);
}
