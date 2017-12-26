package com.elastic.mapper;

import java.util.HashMap;

import com.elastic.model.Test;
import com.elastic.util.MyMapper;

public interface TestMapper extends MyMapper<Test> {

	int updateByShard(HashMap<String, Object> parm);
}