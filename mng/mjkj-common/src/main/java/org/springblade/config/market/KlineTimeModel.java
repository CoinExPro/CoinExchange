package org.springblade.config.market;

import lombok.Data;

/**
 * K线
 */
@Data
public class KlineTimeModel {
	private Long min1;//1分钟
	private Long min5;//5分钟
	private Long min15;//15分钟
	private Long min30;//30分钟
	private Long min60;//60分钟
	private Long hour4;//4小时
	private Long day1;//1天
	private Long week1;//1周
	private Long mon1;//1月
}
