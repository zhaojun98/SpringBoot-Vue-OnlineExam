package com.exam.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ExamManage {
    private Integer examCode;       //考试代码

    private String description;     //描述

    private String source;          //源

    private Integer paperId;

    private String examDate;           //考试时间

    private Integer totalTime;

    private String grade;           //年级

    private String term;            //学期

    private String major;

    private String institute;

    private Integer totalScore;         //总成绩

    private String type;                //类型

    private String tips;
}