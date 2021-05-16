package org.comrades.springtime.mbeans;


import javafx.util.Pair;
import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.DotService;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import java.util.List;
import java.util.stream.Collectors;

@ManagedResource(
    objectName="PD:category=MBeans, name=dotsCounterMBean",
    description="Managed Bean for counting dots that out of area and dots at all"
)
@Component( "dotsCounterMBean" )
public class DotsCounterMBean implements NotificationPublisherAware {
  private DotService dotService;
  private UserService userService;
  private NotificationPublisher notificationPublisher;

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

    // get the not hit dots
    List<Dot> notHitDots = dots.parallelStream().filter( ( dot )->( !dot.getHit() ) ).collect( Collectors.toList());
    final Long notHit = ( long ) notHitDots.size();

    // send notifications about every dot
    notHitDots.forEach( ( dot )->{
      notificationPublisher.sendNotification(
          new Notification(
              "count",
              this,
              System.currentTimeMillis(),
              String.format( "точка ( %f %f ) за пределами области[ R = %d ]", dot.getX(), dot.getY(), dot.getR() ) )
      );
    } );

    return new Pair<>( total, notHit );
  }

  @Override
  public void setNotificationPublisher( NotificationPublisher notificationPublisher ) {
    this.notificationPublisher = notificationPublisher;
  }
}