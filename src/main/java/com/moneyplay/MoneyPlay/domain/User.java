package com.moneyplay.MoneyPlay.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    private School school;

    private ClassRoom classRoom;

    @Column(nullable = false)
    private int studentNumber;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String eMail;

    @Column(nullable = false)
    private boolean isTeacher;

    @Column(nullable = false)
    private String studentProfile;
}
