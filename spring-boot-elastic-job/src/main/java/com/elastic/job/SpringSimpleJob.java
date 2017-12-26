package com.elastic.job;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.elastic.service.MyService;
import com.elastic.service.TestService;

/**
 * Created by daniel on 2016/12/22.
 */
public class SpringSimpleJob implements SimpleJob {

    @Autowired
    MyService myService;

    @Autowired
    TestService testService;

    @Override
    public void execute(ShardingContext context) {
    	Properties sysProperty=System.getProperties();
    	String host = sysProperty.getProperty("os.name")+"/"+sysProperty.getProperty("user.name");
    	myService.printContext(context);
        switch (context.getShardingItem()) {
            case 0:
            	System.out.println("======================>"+host+"执行分片1");
            	testService.updateByShard(0);
                break;
            case 1:
            	System.out.println("======================>"+host+"执行分片2");
            	testService.updateByShard(1);
                break;
            case 2:
            	System.out.println("======================>"+host+"执行分片3");
            	testService.updateByShard(2);
                break;
            // case n: ...
        }
    }
}