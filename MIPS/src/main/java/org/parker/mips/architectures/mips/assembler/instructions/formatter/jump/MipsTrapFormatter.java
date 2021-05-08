package org.parker.mips.architectures.mips.assembler.instructions.formatter.jump;

import org.parker.mips.assembler.base.assembler.BaseAssembler;
import org.parker.mips.assembler.instruction.StandardInstruction;
import org.parker.mips.architectures.mips.assembler.instructions.formatter.MipsJumpFormatter;
import org.parker.mips.assembler.operand.OpImmediate;

public enum MipsTrapFormatter implements MipsJumpFormatter {

    trap(0b011010);

    private final int opCode;

    MipsTrapFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardInstruction instruction, BaseAssembler assembler){
        int im = 0;

        if(instruction.argsLength() == 1){
                if(!(instruction.getArg(0) instanceof OpImmediate)){
                    instruction.throwParameterTypeError(0, OpImmediate.class);
                }
            im = ((Number) instruction.getArg(0).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(1);
        }
        //Operand Order o,i
        MipsJumpFormatter.super.encode(data, new int[]{opCode, im}, assembler);
    }
}
