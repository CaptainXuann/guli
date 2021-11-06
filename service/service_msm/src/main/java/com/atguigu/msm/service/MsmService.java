package com.atguigu.msm.service;

import java.util.HashMap;

/**
 * @author
 */
public interface MsmService {
    boolean send(HashMap<String, Object> map, String phone);
}
