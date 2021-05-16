package org.comrades.springtime.mbeans;


import javafx.util.Pair;
import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.DotService;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

@ManagedResource(
    objectName="PD:category=MBeans, name=dotsCounterMBean",
    description="Managed Bean for counting dots that out of area and dots at all"
)
@Component( "dotsCounterMBean" )
public class DotsCounterMBean {
  private DotService dotService;
  private UserService userService;

  @Autowired
  public DotsCounterMBean( DotService dotService, UserService userService ) {
    this.dotService = dotService;
    this.userService = userService;
  }

  @ManagedOperation
  public Pair<Long, Long> countDots() {
    final User online = userService.getCurrentUser();
    if ( null == online )
      return new Pair<>( 0L, 0L );

    final List<Dot> dots = dotService.getDotsByUser( online );
    if ( null == dots )
      return new Pair<>( 0L, 0L );

    final Long total = ( long ) dots.size();
    // TODO: write notifications
    final Long notHit = dots.parallelStream().filter( ( dot )->( !dot.getHit() ) ).count();

    return new Pair<>( total, notHit );
  }
}