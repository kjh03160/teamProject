package com.hufsSchedule.hufsScheduleSystem.GrdCond.MajorCond;

import com.hufsSchedule.hufsScheduleSystem.GrdCond.CourseEnums;

import java.util.ArrayList;
import java.util.Arrays;

public class Business implements IfcMajors {
//    enum BusinessEnum {
//        D012033("경영학원론", "Principle of MajorCond.Business Management"),
//        D01205("회계원리", "Principles of Accounting"),
//        D03103("경영통계학", "MajorCond.Business Statistics"),
//        D03210("운영관리", "Operations Management"),
//        D01405("마케팅관리", "Marketing Management"),
//        D01314("조직행동", "Organizational Behavior"),
//        D01311("재무관리", "Financial Management"),
//        D03205("재무회계(1)", "Intermediate Accounting (1)"),
//        D01305("국제경영론", "International MajorCond.Business Managemnet"),
//        P042052("경영정보학개론", "Introduction to Management Information System"),
//        A12345("경영학과목", "BusinessTestCourse");
//
//        final private String korName, engName;
//
//        BusinessEnum(String korName, String engName) {
//            this.korName = korName;
//            this.engName = engName;
//        }
//
//        public String getCourseID() {
//            return name();
//        }
//
//        public String getCourseKorName() {
//            return korName;
//        }
//
//        public String getCourseEngName() {
//            return engName;
//        }
//
//    }

    private String studentYear;
    private Boolean bSecondMajor;

    public Business(String studentYear, Boolean bSecondMajor) {
        this.studentYear = studentYear;
        this.bSecondMajor = bSecondMajor;
    }

    @Override
    public ArrayList<String> getMajorCourseList() {
        ArrayList<String> baseCourseList = new ArrayList<String>();
        Arrays.asList(CourseEnums.BusinessEnum.values()).forEach(e -> baseCourseList.add(e.name()));



        ArrayList<String> retCourseList = modifyCourseListByStudentYear(
                modifyCourseListBybSecondMajor(baseCourseList, this.bSecondMajor), this.studentYear
        );
        return retCourseList;
    }

    @Override
    public ArrayList<String> modifyCourseListByStudentYear(ArrayList<String> courseList, String studentYear) {
        ArrayList<String> copiedCourseList =(ArrayList<String>) courseList.clone();

        if (studentYear.equals("2015")) {
            copiedCourseList.add("MODIFIED_BY_STUDENT_YEAR");
        }

        return copiedCourseList;

    }

    @Override
    public ArrayList<String> modifyCourseListBybSecondMajor(ArrayList<String> courseList, Boolean bSecondMajor) {
        ArrayList<String> copiedCourseList =(ArrayList<String>) courseList.clone();

        if (bSecondMajor == true) {
            copiedCourseList.add("MODIFIED_BY_BOOL_SECOND_MAJOR");
        }
        return copiedCourseList;
    }

}
