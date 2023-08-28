package com.moneyplay.MoneyPlay.domain;

import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD
=======
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Deposit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DepositId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
<<<<<<< HEAD
=======
    @OnDelete(action = OnDeleteAction.CASCADE)
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9
    private User user;

    private Long DepositAmount;

    private Long InterestAmount;

    private LocalDate StartDate;

    private LocalDate EndDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_type_id")
    private DepositType depositType;

<<<<<<< HEAD

=======
    private Long week;
>>>>>>> 1b245fd90b881b754493d7ab9a5926f2c32bc4a9


}
