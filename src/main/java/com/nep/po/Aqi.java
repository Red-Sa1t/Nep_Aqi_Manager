package com.nep.po;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Aqi implements Serializable {
    private static final long serialVersionUID = 1L;

	//todo: 没有把level，explain和impact合并进入aqi构造函数

	private String level;
	private String explain;
	private String impact;

	@JsonProperty("aqi_id")
	private int aqiId;

	@JsonProperty("chinese_explain")
	private String chineseExplain;

	@JsonProperty("aqi_explain")
	private String aqiExplain;

	private String color;

	@JsonProperty("health_impact")
	private String healthImpact;

	@JsonProperty("take_steps")
	private String takeSteps;

	@JsonProperty("so2_min")
	private int so2Min;

	@JsonProperty("so2_max")
	private int so2Max;

	@JsonProperty("co_min")
	private int coMin;

	@JsonProperty("co_max")
	private int coMax;

	@JsonProperty("spm_min")
	private int spmMin;

	@JsonProperty("spm_max")
	private int spmMax;

	private String remarks;

	public Aqi() {
		super();
	}
	public Aqi(int aqiId, String chineseExplain, String aqiExplain, String color, String healthImpact, String takeSteps,
			   int so2Min, int so2Max, int coMin, int coMax, int spmMin, int spmMax, String remarks) {
		super();
		this.aqiId = aqiId;
		this.chineseExplain = chineseExplain;
		this.aqiExplain = aqiExplain;
		this.color = color;
		this.healthImpact = healthImpact;
		this.takeSteps = takeSteps;
		this.so2Min = so2Min;
		this.so2Max = so2Max;
		this.coMin = coMin;
		this.coMax = coMax;
		this.spmMin = spmMin;
		this.spmMax = spmMax;
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "Api [aqiId=" + aqiId + ", chineseExplain=" + chineseExplain + ", aqiExplain=" + aqiExplain + ", color="
				+ color + ", healthImpact=" + healthImpact + ", takeSteps=" + takeSteps + ", so2Min=" + so2Min
				+ ", so2Max=" + so2Max + ", coMin=" + coMin + ", coMax=" + coMax + ", spmMin=" + spmMin + ", spmMax="
				+ spmMax + ", remarks=" + remarks + "]";
	}
	public void setAqiId(int aqiId) {
		this.aqiId = aqiId;
	}
	public int getAqiId() {
		return this.aqiId;
	}
	public void setChineseExplain(String chineseExplain) {
		this.chineseExplain = chineseExplain;
	}
	public String getChineseExplain() {
		return this.chineseExplain;
	}
	public void setAqiExplain(String aqiExplain) {
		this.aqiExplain = aqiExplain;
	}
	public String getAqiExplain() {
		return this.aqiExplain;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return this.color;
	}
	public void setHealthImpact(String healthImpact) {
		this.healthImpact = healthImpact;
	}
	public String getHealthImpact() {
		return this.healthImpact;
	}
	public void setTakeSteps(String takeSteps) {
		this.takeSteps = takeSteps;
	}
	public String getTakeSteps() {
		return this.takeSteps;
	}
	public void setSo2Min(int so2Min) {
		this.so2Min = so2Min;
	}
	public int getSo2Min() {
		return this.so2Min;
	}
	public void setSo2Max(int so2Max) {
		this.so2Max = so2Max;
	}
	public int getSo2Max() {
		return this.so2Max;
	}
	public void setCoMin(int coMin) {
		this.coMin = coMin;
	}
	public int getCoMin() {
		return this.coMin;
	}
	public void setCoMax(int coMax) {
		this.coMax = coMax;
	}
	public int getCoMax() {
		return this.coMax;
	}
	public void setSpmMin(int spmMin) {
		this.spmMin = spmMin;
	}
	public int getSpmMin() {
		return this.spmMin;
	}
	public void setSpmMax(int spmMax) {
		this.spmMax = spmMax;
	}
	public int getSpmMax() {
		return this.spmMax;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return this.remarks;
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

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}
}
