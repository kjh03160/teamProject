package com.hufsSchedule.hufsScheduleSystem.SuggSys.Objs;

import com.hufsSchedule.hufsScheduleSystem.Entity.Instruction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeightInstruction implements Comparable<WeightInstruction>{
    private Float weight;
    private Instruction instruction;
    public WeightInstruction(Float weight, Instruction instruction) {
        this.weight = weight;
        this.instruction = instruction;
    }


    @Override
    public int compareTo(WeightInstruction o) {
        if (this.weight < o.getWeight()) {
            return -1;
        } else if (this.weight > o.getWeight()) {
            return 1;
        }
        return 0;
    }
}
