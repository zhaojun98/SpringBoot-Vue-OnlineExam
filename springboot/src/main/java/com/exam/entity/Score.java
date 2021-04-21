package com.exam.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Score {
    private Integer examCode;           //考试编号

    private Integer studentId;          //学生编号

    private String subject;         //科目

    private Integer ptScore;

    private Integer etScore;            //考试成绩

    private Integer score;

    private Integer scoreId;                //成绩编号

    private String answerDate;          //答题日期
}