package com.yiban.yblaas.service;

import com.yiban.yblaas.domain.College;
import com.yiban.yblaas.domain.Eclass;
import com.yiban.yblaas.domain.Student;
import com.yiban.yblaas.domain.Yblaas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: yblaas
 * @description: public的Service
 * @author: xiaozhu
 * @create: 2020-03-17 23:15
 **/
public interface PublicService {
    String userLogin(HttpServletRequest request, HttpServletResponse response);

    String getYibanUrl();

    String userRegister(String type);

    List<College> getCollegeList();

    List<Eclass> getEclassList(Integer collegeId);

    String sendMessage(String phone);

    String sendQq(String userQq);

    String sendEmail(String userEmail);

    Yblaas getYblaas();
}
