package software.ulpgc.aoc.day01.io;

import software.ulpgc.aoc.day01.model.Instruction;

import java.util.List;


public interface InstructionLoader {
    List<Instruction> loadAll();
}
