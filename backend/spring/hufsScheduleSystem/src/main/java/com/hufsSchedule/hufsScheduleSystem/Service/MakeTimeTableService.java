package com.hufsSchedule.hufsScheduleSystem.Service;

import com.google.common.collect.Table;
import com.hufsSchedule.hufsScheduleSystem.Advice.Exception.UserNotFoundException;
import com.hufsSchedule.hufsScheduleSystem.Dto.ConditionDto;
import com.hufsSchedule.hufsScheduleSystem.Dto.TimetableDto;
import com.hufsSchedule.hufsScheduleSystem.Entity.Credit;
import com.hufsSchedule.hufsScheduleSystem.Entity.Instruction;
import com.hufsSchedule.hufsScheduleSystem.Entity.Timetable;
import com.hufsSchedule.hufsScheduleSystem.Entity.User;
import com.hufsSchedule.hufsScheduleSystem.GrdCond.GrdCompareService;
import com.hufsSchedule.hufsScheduleSystem.GrdCond.GrdCondObj;
import com.hufsSchedule.hufsScheduleSystem.GrdCond.GrdCondService;
import com.hufsSchedule.hufsScheduleSystem.Repository.InstructionRepository;
import com.hufsSchedule.hufsScheduleSystem.Repository.TimeTableRepository;
import com.hufsSchedule.hufsScheduleSystem.Repository.TimeTableRepositorySupport;
import com.hufsSchedule.hufsScheduleSystem.Repository.UserRepository;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.Objs.SuggSysObj;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.Objs.UserSelectsObj;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.Objs.WeightInstruction;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.SuggSysFunc;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.SuggSysService;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.detailServices.SuggTableService;
import com.hufsSchedule.hufsScheduleSystem.SuggSys.detailServices.UserSelectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MakeTimeTableService {
    private final ConditionCheckService conditionCheckService;
    private final InstructionRepository instructionRepository;
    private final UserRepository userRepository;
    private final TimeTableRepositorySupport timeTableRepositorySupport;
    private final TimeTableRepository timeTableRepository;
    private GrdCondService grdCondService;
    private GrdCompareService grdCompareService;
    private TimetableDto.Res res;
    private SuggSysService suggSysService;
    private UserSelectsService userSelectsService;

    public List<TimetableDto.Result> checkCondition(TimetableDto.Req req){
        ConditionDto.courseInstructionRes condition = conditionCheckService.checkConditionForTimeTable(req.getUserId());
        ArrayList<Instruction> instructions = instructionRepository.findAllByRqYear(20L); //20년도 강의목록입니다.


        User userInfo = userRepository.findById(req.getUserId()).orElseThrow(UserNotFoundException::new);
        List<Instruction> userTakenCourses = condition.getInstructions();
        Credit userCredit = condition.getCredit();

        ArrayList<Instruction> tester = req.getMyCourse();

        for (Instruction instruction : tester) {
            System.out.println(" -- maketimetable test --");
            System.out.println(instruction.getInstructionId());
            System.out.println(instruction.getInstructionNumber());
            System.out.println(instruction.getProfessor());
            System.out.println(instruction.getDept());
            System.out.println(instruction.getArea());
            System.out.println(instruction.getCredit());
            System.out.println(instruction.getSubject());
            System.out.println(instruction.getRqSemester());
            System.out.println(instruction.getRqYear());
            System.out.println(instruction.getTime());
            System.out.println(instruction.getClassTime());
            System.out.println(instruction.getNote());
            System.out.println(instruction.getUrl());
            System.out.println(" ------------------------");
        }

//
        UserSelectsObj userSelectsObj = UserSelectsService.initUserSelects(req.getMyCourse(), req.getMyCredit(), req.getMyFreetime());

        SuggSysObj suggSysObj = suggSysService.initSuggSys(userInfo, userSelectsObj, userTakenCourses, userCredit, instructions);
        GrdCondObj GrdObj = GrdCondService.makeGrdCondByUserInfo(userInfo);
        GrdCondObj remainObj = GrdCompareService.compareGrdAndUser(userInfo, condition.getInstructions(), userCredit, GrdObj);

        List<Table<String, String, WeightInstruction>> tables = SuggSysService.addInstructionsToTable(suggSysObj, remainObj.getGrdCourse());
        System.out.println("-- table results --");
        System.out.println(tables.size());
        System.out.println(tables.indexOf(0));
        System.out.println("-------------------");

        List<TimetableDto.Result> results = new ArrayList<>();
        for (Table<String, String, WeightInstruction> table : tables) {
            results.add(SuggSysService.cvtTableToResult(table));
        }



        return results;
        // Taken GrdCondObj, remain GrdCondObj, UserInfo
        //GrdCondObj GrdCond = grdCondService.makeGrdCondByUserInfo(req);
        //GrdCondObj remains = grdCompareService.compareGrdAndUser(req, condition, GrdCond);
        //res = new TimetableDto.Res(remains);

        // TimetableDto.Req req 안에 user 데이터 들어있음


        // 학생의 학점 정보(Credit 객체) + 수강했던 강의 이름(List<String>) 을 불러오는 메소드
        // 자세 정보를 알고 싶다면, Dto/CondtionDto 의 courseIdRes를 체크.
        // 만든 결과물은 TimetableDto 내 res에 넣어야 함.

    }
    public TimetableDto.MyTimeTable checkTimeTable(TimetableDto.ReqTimeTable req){
        List<Instruction> instructionList = timeTableRepositorySupport.findInstructionByUser(req.getUserId());
        TimetableDto.MyTimeTable result = TimetableDto.MyTimeTable.builder().myCourse(instructionList).build();
        return result;
    }

    public boolean saveTimeTable(TimetableDto.SaveTimeTable req){
        User user = userRepository.findById(req.getUserId()).orElseThrow(UserNotFoundException::new);
        ArrayList<Timetable> dto = new ArrayList<>();
        for (Instruction instruction: req.getMyCourse()){
            Timetable timetable = new Timetable();
            timetable.setUser(user);
            timetable.setInstruction(instruction);
            dto.add(timetable);
        }
        timeTableRepository.saveAll(dto);
        return true;
    }
}
