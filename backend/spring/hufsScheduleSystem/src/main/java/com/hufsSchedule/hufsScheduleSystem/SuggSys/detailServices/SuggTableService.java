package com.hufsSchedule.hufsScheduleSystem.SuggSys.detailServices;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.hufsSchedule.hufsScheduleSystem.Entity.Instruction;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.Objs.CreditRatio;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.Objs.WeightInstruction;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.SuggSysFunc;
import org.springframework.data.relational.core.sql.In;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hufsSchedule.hufsScheduleSystem.SuggSys.SuggSysFunc.*;

public class SuggTableService {

    public static Table<String, String, WeightInstruction> initTimeTable(List<Instruction> userSelectInstructions, List<String> userSelectFreetime) {
        Table<String, String, WeightInstruction> timeTable = getEmptyTimeTable();
        List<WeightInstruction> weigted = SuggInstructionService.addWeightToInstructions(userSelectInstructions, new Float(1));

        for (String freetime : userSelectFreetime) { // 공강시간 --> empty instruction
            String day = cvtKorDayToEng(freetime.substring(0,1));
            String time = freetime.substring(1);
            timeTable.put(time, day, SuggSysFunc.getEmptyInstruction());
        }
        for (WeightInstruction instruction : weigted) { // 사용자가 설정한 강의 추가
            inputInstructionToTable(timeTable, instruction);
        }

        return timeTable;
    }

    public static Table<String, String, WeightInstruction> getEmptyTimeTable() {
        List<String> columns = Lists.newArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        List<String> rows = Lists.newArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
        Table<String, String, WeightInstruction> timeTable = ArrayTable.create(rows, columns);

        return timeTable;
    }

    public static void inputInstructionToTable(Table<String, String, WeightInstruction> timeTable, WeightInstruction instruction)  {
        String classTime = instruction.getInstruction().getClassTime();
        List<String> times = extClassTimes(classTime);

        for (String time : times) {
            System.out.println(classTime);
            System.out.println(instruction.getInstruction().getSubject());
            timeTable.put(time.substring(1,2) , cvtKorDayToEng(time.substring(0,1)), instruction);
        }

    }

    public static Boolean inputInstructionToTable(Table<String, String, WeightInstruction> timeTable, WeightInstruction instruction, CreditRatio ratio)  {
        String field = ratio.getFieldToMajor().get(instruction.getInstruction().getDept());
        String classTime = instruction.getInstruction().getClassTime();
        if (classTime == null || classTime.length() == 0) { // classtime 없으면 return
            return false;
        }
        if (classTime.substring(0,1).equals("토")) { return false; }
        if (field != null) {
            if (ratio.getRatio().get(field) == null) {
                return false; // 없다 = 채울게 애초에 없다.
            }
            else if (ratio.getRatio().get(field) <= 0) {
                if (field.equals("secondMajor")) {
                    System.out.println("when input --....");
                    System.out.println(field + instruction.getInstruction().getSubject());
                    System.out.println(ratio.getRatio());
                }


                return false; // ratio에서 이미 채웠으면 pass
            }

        }
        List<String> columns = Lists.newArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        List<String> rows = Lists.newArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
        for (String row : rows) {
            for (String column : columns) {
                WeightInstruction cell = timeTable.get(row, column);
                if (cell != null && !isInstructionEmpty(cell)) {
                    String instNumber = cell.getInstruction().getInstructionNumber().substring(0, 6);
                    if (instNumber.equals(instruction.getInstruction().getInstructionNumber().substring(0, 6))) {
                        return false; // 같은 학수번호가 존재한다면 return
                    }
                }
            }
        }



//        System.out.println("classtime -----------------------");
//        System.out.println(classTime);
//        System.out.println(instruction.getInstruction().getInstructionNumber());
//        System.out.println(instruction.getInstruction().getSubject());
//        System.out.println("--------------------------------");

        List<String> times = extClassTimes(classTime);

        for (String time : times) {
            if (timeTable.get(time.substring(1,2), cvtKorDayToEng(time.substring(0,1))) != null) {// 해당 시간에 null이 아닌 뭔가가 있으면 pass
                return false;
            }
        }

        for (String time : times) {
            timeTable.put(time.substring(1,2), cvtKorDayToEng(time.substring(0,1)), instruction);
        }

        return true;
    }
    public static List<Table<String, String, WeightInstruction>> getTopNTableResult(List<Table<String, String, WeightInstruction>> tables, Integer counts) {
        //remove duplication
        List<Table<String, String, WeightInstruction>> unq = new ArrayList<>();
        for (Table<String, String, WeightInstruction> table : tables) {
            if (!unq.contains(table)) {
                unq.add(table);
            }
        }
        System.out.println("duplicated size : " + tables.size());
        System.out.println("unique size : " + unq.size());

        if (unq.size() < counts) {
            return unq;
        }

        List<Float> scores = new ArrayList<>();
        List<String> columns = Lists.newArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        List<String> rows = Lists.newArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
        for (Table<String, String, WeightInstruction> table : unq) {
            Float score = new Float(0.0);
            for ( String row : rows) {
                for (String column : columns) {
                    WeightInstruction cell = table.get(row, column);
                    if (cell != null && ! isInstructionEmpty(cell)) {
                        score += cell.getWeight();
                    }
                }
            }
            scores.add(score);
        }
        List<Integer> indices = new ArrayList<>();
        List<Table<String, String, WeightInstruction>> topNs = new ArrayList<>();

        for (Integer i = 0; i < counts; i++) {
            Float value = Collections.max(scores);
            int idx = scores.indexOf(value);
            scores.remove(idx);
            topNs.add(unq.get(idx));
            unq.remove(idx);
            System.out.println("suggTaleServbice, getTopN, score/idx" + value.toString() +" ~"+ idx);
        }

        return topNs;
    }
}
