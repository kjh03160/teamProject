package com.hufsSchedule.hufsScheduleSystem.Controller;

import com.hufsSchedule.hufsScheduleSystem.Dto.TimetableDto;
import com.hufsSchedule.hufsScheduleSystem.Redis.RedisService;
import com.hufsSchedule.hufsScheduleSystem.Repository.CourseRepositorySupport;
import com.hufsSchedule.hufsScheduleSystem.Service.MakeTimeTableService;
import com.hufsSchedule.hufsScheduleSystem.Service.ResponseService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("web/v1/makeTimeTable")
@CrossOrigin(origins = "*")
public class MakeTimeTableController {
    private final ResponseService responseService;
    private final MakeTimeTableService makeTimeTableService;
    private final RedisService redisService;
    private final CourseRepositorySupport courseRepositorySupport;

    /*@GetMapping("/selectInstruction")
    public void makeTimeTable()throws Exception {
        redisService.InstallInstructions();
        return ;
    }*/

    @PostMapping("/try")
    public List<TimetableDto.Result> makeTimeTable(@RequestBody TimetableDto.Req req)throws Exception {
        List<TimetableDto.Result> results = makeTimeTableService.checkCondition(req);
        return results;
    }

    @GetMapping("/save")
    public List<TimetableDto.findInstructionCode> saveTimeTable()throws Exception {
        return courseRepositorySupport.findInstructionCodeByMajor();
    }
}
