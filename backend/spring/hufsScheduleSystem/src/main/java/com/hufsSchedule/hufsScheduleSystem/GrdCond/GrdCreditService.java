package com.hufsSchedule.hufsScheduleSystem.GrdCond;

import com.hufsSchedule.hufsScheduleSystem.GrdCond.CreditCond.CreditCondObj;
import com.hufsSchedule.hufsScheduleSystem.GrdCond.CreditCond.IfcCreditCond;

public class GrdCreditService {
    private String studentYear;
    private CreditCondObj creditCondObj;
    private Boolean bIntensiveMajor, bSecondMajor, bMinor;

    public GrdCreditService(String studentYear, Boolean bIntensiveMajor, Boolean bSecondMajor, Boolean bMinor) {
        this.studentYear = studentYear;
        this.bIntensiveMajor = bIntensiveMajor;
        this.bSecondMajor = bSecondMajor;
        this.bMinor = bMinor;

        this.creditCondObj = makeGrdCreditByInfo(studentYear, this.bIntensiveMajor, this.bSecondMajor, this.bMinor);
    }

    public CreditCondObj makeGrdCreditByInfo(String studentYear, Boolean bIntensiveMajor, Boolean bSecondMajor, Boolean bMinor) {
        IfcCreditCond creditCond;
        String className = "com.hufsSchedule.hufsScheduleSystem.GrdCond.CreditCond.CreditCond" + studentYear;
        try {
            creditCond = (IfcCreditCond) Class.forName(className)
                    .getDeclaredConstructor(Boolean.class, Boolean.class, Boolean.class)
                    .newInstance(bIntensiveMajor, bSecondMajor, bMinor);
        } catch (Exception e) {
            creditCond = null;
            System.out.println(className);
            System.out.println("error in makeGrdCreditByInfo occured");
        }

        return creditCond.getCreditCondList();
    }

    public CreditCondObj getCreditCondObj() { return creditCondObj; }

    //get user info and boolean infos from it
}

