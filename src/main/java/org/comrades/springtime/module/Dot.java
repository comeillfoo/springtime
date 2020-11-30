package org.comrades.springtime.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "dots")
public class Dot extends DotBaseEntity {

    @Id
    @Column(name = "dot_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dotId;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
