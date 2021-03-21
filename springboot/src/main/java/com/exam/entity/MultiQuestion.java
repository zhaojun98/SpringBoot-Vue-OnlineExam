package com.exam.entity;

import lombok.Data;

// 选择题实体
@Data
public class MultiQuestion {
    private Integer questionId;

    private String subject;

    private String section;

    private String answerA;             //答案A

    private String answerB;             //答案B

    private String answerC;             //答案C

    private String answerD;             //答案D

    private String question;             //问题

    private String level;                  //等级

    private String rightAnswer;         //正确答案

    private String analysis;            //题目解析

    private Integer score;              //分数

}