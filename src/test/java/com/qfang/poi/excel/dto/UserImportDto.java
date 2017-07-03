package com.qfang.poi.excel.dto;


import com.qfang.poi.excel.annotation.ColumnHead;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @since 1.0
 */
public class UserImportDto {

	@ColumnHead(title = "姓名")
	private String name;

	@ColumnHead(title = "身份证号", width = 25)
	private String idCard;

	@ColumnHead(title = "年龄")
	private String age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
}
