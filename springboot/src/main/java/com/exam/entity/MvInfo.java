package com.exam.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class MvInfo {
	private String id;
	/** 教师关联id */
	private String teacherId;
	/** 创建时间 */
	private String createTime;
	/** 视频地址 */
	private String mvPath;
	/** 视频标题 */
	private String subject;
	/** 视频归类（父子ID） */
	private String pid;
	/** 剧集 */
	private String drama;
	/**  封面*/
	private String imgPath;
	//名字
	@TableField(exist = false)
	private String teacherName;
	//学院
	@TableField(exist = false)
	private String institute;
	///职称
	@TableField(exist = false)
	private String type;

	@TableField(exist = false)
	private String studentName;

	@TableField(exist = false)
	private String content;

	@TableField(exist = false)
	private String time;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	public String getTeacherId() {
		return this.teacherId;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setMvPath(String mvPath) {
		this.mvPath = mvPath;
	}
	
	public String getMvPath() {
		return this.mvPath;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getPid() {
		return this.pid;
	}
	
	public void setDrama(String drama) {
		this.drama = drama;
	}
	
	public String getDrama() {
		return this.drama;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        MvInfo that = (MvInfo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
    

	
}