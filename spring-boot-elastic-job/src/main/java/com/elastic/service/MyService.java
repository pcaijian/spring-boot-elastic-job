package com.elastic.service;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by daniel on 2016/12/22.
 */
@Service
public class MyService {
    public void printContext(ShardingContext context) {
        System.out.println(ToStringBuilder.reflectionToString(context));
    }
}

