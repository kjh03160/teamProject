package com.hufsSchedule.hufsScheduleSystem.GrdCond.LibArtsCond;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class LibArts2015 implements LibArts{
    enum LibArts2015Enum {
        Y12101("미네르바인문(1)읽기와쓰기", "Minerva Humanities Reading and Writing"),
        Y12102("미네르바인문(2)읽기와토의.토론", "Minerva Humanities Reading and Discussion & Debate");

        final private String korName, engName;

        LibArts2015Enum(String korName, String engName) {
            this.korName = korName;
            this.engName = engName;
        }

        public String getKorName() {
            return korName;
        }
        public String getEngName() {
            return engName;
        }
        public String getCourseID() { return name(); }
    }

    private String studentYear, firstMajorName, secondMajorName;

    public LibArts2015(String studetYear, String firstMajorName, String secondMajorName) {
        this.studentYear = studetYear;
        this.firstMajorName = firstMajorName;
        this.secondMajorName = secondMajorName;
    }

    @Override
    public ArrayList<String> getLibArtsCourseList() {
        ArrayList<String> baseCourseList = new ArrayList<String>();
        Arrays.asList(LibArts2015Enum.values()).forEach(e -> baseCourseList.add(e.getCourseID()));

        ArrayList<String> retCourseList = modifyCourseListByStudentYear(
                modifyCourseListBySecondMajor(
                        modifyCourseListByFirstMajor(baseCourseList, this.firstMajorName),
                        this.secondMajorName),
                this.studentYear
        );
        return retCourseList;
    }

    @Override
    public ArrayList<String> modifyCourseListByStudentYear(ArrayList<String> courseList, String studentYear) {
        return null;
    }

    @Override
    public ArrayList<String> modifyCourseListByFirstMajor(ArrayList<String> courseList, String firstMajorName) {
        return null;
    }

    @Override
    public ArrayList<String> modifyCourseListBySecondMajor(ArrayList<String> courseList, String secondMajorName) {
        return null;
    }


}