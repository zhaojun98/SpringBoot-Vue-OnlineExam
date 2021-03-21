package com.exam.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ExamManage {
    private Integer examCode;       //科目编号

    private String description;     //试卷标题

    private String source;          //科目

    private Integer paperId;            //编号

    private String examDate;           //考试时间

    private Integer totalTime;          //答题总时间

    private String grade;           //年级

    private String term;            //学期

    private String major;           //专业

    private String institute;            //学院

    private Integer totalScore;         //试卷总分

    private String type;                //考试类型

    private String tips;            //备注
}