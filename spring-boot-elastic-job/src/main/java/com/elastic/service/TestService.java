package com.elastic.service;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elastic.mapper.TestMapper;

/**
 * Created by daniel on 2016/12/22.
 */
@Component
public class TestService {
   
	@Autowired
	TestMapper testMapper;
	
	public int updateByShard(int shard){
		HashMap<String, Object> parm = new HashMap<String,Object>();
		Properties sysProperty=System.getProperties();
    	String host = sysProperty.getProperty("os.name")+"/"+sysProperty.getProperty("user.name");
		parm.put("shard", shard);
		parm.put("host", host);
		return testMapper.updateByShard(parm);
	}
}

