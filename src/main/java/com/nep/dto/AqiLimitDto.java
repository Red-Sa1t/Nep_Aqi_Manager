package com.nep.dto;

public class AqiLimitDto {
	private int intlevel;
	private String level;
	private String explain;
	private String color;

	public AqiLimitDto(int intlevel, String level, String explain, String color) {
		super();
		this.intlevel = intlevel;
		this.level = level;
		this.explain = explain;
		this.color = color;
	}
	public AqiLimitDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIntlevel() {
		return intlevel;
	}
	public void setIntlevel(int intlevel) {
		this.intlevel = intlevel;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

}
