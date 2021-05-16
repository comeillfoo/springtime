package org.comrades.springtime.mbeans;

import org.comrades.springtime.module.Dot;
import org.comrades.springtime.module.User;
import org.comrades.springtime.servise.DotService;
import org.comrades.springtime.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

@ManagedResource( objectName="PD:category=MBeans, name=areaDeterminantMBean", description="Managed Bean for determining the area of last user and its last query" )
@Component( "areaDeterminantMBean" )
public class AreaDeterminantMBean {
  private DotService dotService;
  private UserService userService;

  @Autowired
  public AreaDeterminantMBean( DotService dotService, UserService userService ) {
    this.dotService = dotService;
    this.userService = userService;
  }

  private double area( double radius ) {
    return radius * radius * 0.94634954084936207740375;
  }

  @ManagedOperation
  public double getTheResultingArea() {
    final User online = userService.getCurrentUser();
    if ( null == online ) return 0.0;

    final List<Dot> dots = dotService.getDotsByUser( online );
    if ( dots.isEmpty() )
      return 0.0;
    else return area( ( double ) dots.get( dots.size() - 1 ).getR() );
  }
}
