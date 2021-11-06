package com.atguigu.eduService.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author
 */
@Data
public class CourseQuery {
    private String title;
    private String status;
    private Integer lessonNum;
    private Date begin;
    private Date end;
}
