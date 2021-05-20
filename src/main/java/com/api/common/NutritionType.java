package com.api.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NutritionType {

	// Khởi tạo các phần tử từ cấu tử.
	// Các phần tử này luôn là final, static
	Type1(1, "30-30-40", "High Fat"), 
	Type2(2, "40-40-20", "Medium"),
	Type3(3, "50-30-20", "High Protein")
	;

	private int code;
	private String type;
	private String text;
}
