package com.api.common;

import java.util.List;

import com.api.model.Result;
import com.api.model.ResultList;

public class CommonClass {
	public static enum Action {
		 UPDATE,
		 ADD
		}
	
	public static Result createResult(Object obj) {
		Result rs = new Result();
		rs.setData(obj) ;
		rs.setMessage("OK");
		return rs ;
		
	}
	public static ResultList createResultList(List<?> list) {
		ResultList rs = new ResultList();
		rs.setData(list) ;
		rs.setMessage("OK");
		return rs ;
		
	}
}
