package com.nep.util;


import com.nep.dto.AqiLimitDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	/**
	 * 当前系统日期
	 * @return
	 */
	public static String currentDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 二氧化硫浓度限值对应级别
	 * @param value
	 * @return
	 */
	public static AqiLimitDto so2Limit(double value){
		AqiLimitDto dto = null;
		if(0<=value && value<=50){
			dto = new AqiLimitDto(1,"一级","优","#02E300");
		}else if(51<=value && value<=150){
			dto = new AqiLimitDto(2,"二级","良","#FFFF00");
		}else if(151<=value && value<=475){
			dto = new AqiLimitDto(3,"三级","轻度污染","#FF7E00");
		}else if(476<=value && value<=800){
			dto = new AqiLimitDto(4,"四级","中度污染","#FE0000");
		}else if(801<=value && value<=1600){
			dto = new AqiLimitDto(5,"五级","重度污染","#98004B");
		}else if(1600<=value){
			dto = new AqiLimitDto(6,"六级","严重污染","#7E0123");
		}
		return dto;
	}
	/**
	 * 一氧化碳浓度限值对应级别
	 * @param value
	 * @return
	 */
	public static AqiLimitDto coLimit(double value){
		AqiLimitDto dto = null;
		if(0<=value && value<=5){
			dto = new AqiLimitDto(1,"一级","优","#02E300");
		}else if(6<=value && value<=10){
			dto = new AqiLimitDto(2,"二级","良","#FFFF00");
		}else if(11<=value && value<=35){
			dto = new AqiLimitDto(3,"三级","轻度污染","#FF7E00");
		}else if(36<=value && value<=60){
			dto = new AqiLimitDto(4,"四级","中度污染","#FE0000");
		}else if(61<=value && value<=90){
			dto = new AqiLimitDto(5,"五级","重度污染","#98004B");
		}else if(91<=value){
			dto = new AqiLimitDto(6,"六级","严重污染","#7E0123");
		}
		return dto;
	}
	/**
	 * PM2.5浓度限值对应级别
	 * @param value
	 * @return
	 */
	public static AqiLimitDto pmLimit(double value){
		AqiLimitDto dto = null;
		if(0<=value && value<=35){
			dto = new AqiLimitDto(1,"一级","优","#02E300");
		}else if(36<=value && value<=75){
			dto = new AqiLimitDto(2,"二级","良","#FFFF00");
		}else if(76<=value && value<=115){
			dto = new AqiLimitDto(3,"三级","轻度污染","#FF7E00");
		}else if(116<=value && value<=150){
			dto = new AqiLimitDto(4,"四级","中度污染","#FE0000");
		}else if(151<=value && value<=250){
			dto = new AqiLimitDto(5,"五级","重度污染","#98004B");
		}else if(251<=value){
			dto = new AqiLimitDto(6,"六级","严重污染","#7E0123");
		}
		return dto;
	}
	/**
	 * 最终实测AQI等级
	 * @param so2level
	 * @param colevel
	 * @param pmlevel
	 * @return
	 */
	public static AqiLimitDto confirmLevel(int so2level,int colevel,int pmlevel){
		AqiLimitDto dto = null;
		int max = so2level > colevel ? so2level : colevel;
		max = pmlevel > max ? pmlevel : max;
		switch (max) {
			case 1:
				dto = new AqiLimitDto(1,"一级","优","#02E300");
				break;
			case 2:
				dto = new AqiLimitDto(2,"二级","良","#FFFF00");
				break;
			case 3:
				dto = new AqiLimitDto(3,"三级","轻度污染","#FF7E00");
				break;
			case 4:
				dto = new AqiLimitDto(4,"四级","中度污染","#FE0000");
				break;
			case 5:
				dto = new AqiLimitDto(5,"五级","重度污染","#98004B");
				break;
			case 6:
				dto = new AqiLimitDto(6,"六级","严重污染","#7E0123");
				break;
			default:
				break;
		}
		return dto;
	}
}