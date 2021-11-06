package com.atguigu.eduService.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> list = new ArrayList<>();
}
