package com.exam.vo;

import lombok.Data;

@Data
public class AnswerVO {
    private String question;
    private String subject;     //学科
    private String score;           //分数
    private String section;
    private String level;           //等级
    private String type;            //类型
}
