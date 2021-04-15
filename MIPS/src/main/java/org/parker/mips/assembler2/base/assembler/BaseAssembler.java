package org.parker.mips.assembler2.base.assembler;

import org.parker.mips.MIPS;
import org.parker.mips.assembler.AssemblerLevel;
import org.parker.mips.assembler.InstructionToString;
import org.parker.mips.assembler2.base.*;
import org.parker.mips.assembler2.base.preprocessor.*;
import org.parker.mips.assembler2.directives.assembler.AssemblerDirectives;
import org.parker.mips.assembler2.exception.AssemblerError;
import org.parker.mips.assembler2.exception.LableNotDeclaredError;
import org.parker.mips.assembler2.mips.MipsAssembler;
import org.parker.mips.assembler2.util.*;
import org.parker.mips.assembler2.util.linking.AssemblyUnit;
import org.parker.mips.assembler2.util.Label;
import org.parker.mips.emulator.mips.Memory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public abstract class BaseAssembler<P extends BasePreProcessor> implements Assembler{


    private static final Logger ASSEMBLER_LOGGER = Logger.getLogger(BaseAssembler.class.getName() + "\\.Assembler");
    private static final Logger LINKER_LOGGER = Logger.getLogger(BaseAssembler.class.getName() + "\\.Linker");

    protected P preProcessor;
    protected ArrayList<AssemblyUnit> assemblyUnits = new ArrayList<>();
    protected HashMap<String, Label> globalLabelMap = new HashMap<>();

    public static void main(String... args){
        MIPS.main(args);
        MipsAssembler a = new MipsAssembler();
        org.parker.mips.assembler.Assembler.assemble(new File("C:\\Users\\parke\\OneDrive\\Documents\\MIPS\\Examples\\Games\\snake2.asm"));
        InstructionToString.decompile();
        a.assemble(new File[]{new File("C:\\Users\\parke\\OneDrive\\Documents\\MIPS\\Examples\\Games\\snake2.asm")});
        InstructionToString.decompile();
    }

    public long getCurrentAddress(){
        return currentAddress;
    }

    protected File currentFile;
    protected HashMap<String, Label> fileLabelMap = new HashMap<>();
    protected ArrayList<Data> fileDataList = new ArrayList<>();
    protected AssemblyUnit currentAssemblyUnit;
    protected long startingAddress = 0;
    protected long currentAddress = 0;
    protected long size = 0;

    private void assemble(File input){

        if(input == null) {
            ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "Provided file is null skipping");
            return;
        }
        ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_MESSAGE, "Started assembly of: " + input.getAbsolutePath());

        currentFile = input;
        fileLabelMap = new HashMap<>();
        fileDataList = new ArrayList<>();
        currentAssemblyUnit = new AssemblyUnit(fileDataList, fileLabelMap);

        List<PreProcessedStatement> lines = preProcessor.preprocess(input);

        startingAddress = 0;
        currentAddress = 0;
        size = 0;

        for(int i = 0; i < lines.size(); i ++){

            PreProcessedStatement line = lines.get(i);
            Data data = null;

                if (line instanceof PreProcessedAssemblyStatement) {

                    try {
                        String mnemonic = ((PreProcessedAssemblyStatement) line).identifier;
                        CompiledExpression[] args = ((PreProcessedAssemblyStatement) line).args;

                        DataStatement instruction = getInstruction(mnemonic);
                        instruction.setArgExpressions(args, ((PreProcessedAssemblyStatement) line).parentLine);
                        data = instruction;
                    }catch (Exception e){
                        ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed to evaluate Assembly Statement", ((PreProcessedAssemblyStatement) line).parentLine, e));
                        continue;
                    }

                } else if (line instanceof PreProcessedAssemblyDirective) {

                    try {
                        String directive = ((PreProcessedAssemblyDirective) line).identifier;
                        CompiledExpression[] args = ((PreProcessedAssemblyDirective) line).args;

                        AssemblerDirectives.getHandler(directive).parse(args, this);
                    }catch (Exception e){
                        ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed to evaluate AssemblyDirective", ((PreProcessedAssemblyDirective) line).parentLine, -1, e));
                        continue;
                    }

                } else if (line instanceof PreProcessedLabel) {
                    try {

                        Label label = new Label(currentAddress, ((PreProcessedLabel) line).label, ((PreProcessedLabel) line).parentLine);
                        fileLabelMap.put(((PreProcessedLabel) line).label, label);

                    }catch (Exception e){
                        ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed to evaluate Label", ((PreProcessedLabel) line).parentLine, -1, e));
                        continue;
                    }
                }

            if(data != null){
                currentAddress += data.getSize();
                size += data.getSize();
                fileDataList.add(data);
            }

        }
        currentAssemblyUnit.setSize(size);

        assemblyUnits.add(currentAssemblyUnit);
        globalLabelMap.putAll(fileLabelMap);
    }

    public void addDataToCurrent(Data data){
        currentAddress += data.getSize();
        size += data.getSize();
        fileDataList.add(data);
    }

    public PagedMemory linkGlobal(){

        PagedMemory pMemory = new PagedMemory();

        assemblyUnits.sort((o1, o2) -> {
            if(o1.getStartingAddress() < o2.getStartingAddress()){
                return -1;
            }else if(o1.getStartingAddress() > o2.getStartingAddress()){
                return 1;
            }else{
                return 0;
            }
        });

        if(assemblyUnits.get(0).getStartingAddress() < 0){
            assemblyUnits.get(0).setStartingAddress(0);
        }
        for(int i = 1; i < assemblyUnits.size(); i ++){
            if(assemblyUnits.get(i - 1).getStartingAddress() < 0){
                assemblyUnits.get(i).setStartingAddress(assemblyUnits.get(i).getEndingAddress());
            }else{
                if(assemblyUnits.get(i - 1).getEndingAddress() >= assemblyUnits.get(i).getStartingAddress()){
                    LINKER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "Size miss mach");
                }
            }
        }

        for(AssemblyUnit au: assemblyUnits){
            long address = au.getStartingAddress();
            long size = 0;

                for(Data d:au.data){
                    if(d instanceof LinkableData){
                        try {
                            ((LinkableData) d).link(this, address);
                        }catch (Exception e){
                            LINKER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, ": Failed to link data", e);
                            try {
                                address += d.getSize();
                                size += d.getSize();
                            }catch (Exception ex){
                                LINKER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "", ex);
                            }
                            continue;
                        }
                    }
                    try {
                        byte[] bytes = d.toBinary();
                        pMemory.add((int) address, bytes);
                        address += d.getSize();
                        size += d.getSize();
                    }catch(AssemblerError e){
                        try{
                            address += d.getSize();
                            size += d.getSize();
                        }catch (Exception ex){
                            ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "",  ex);
                        }
                        ASSEMBLER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, "",  e);
                    }
                }
            if(size != au.getSize()){
                LINKER_LOGGER.log(AssemblerLevel.ASSEMBLER_ERROR, ": Size missmatch size of Assembler Unit does not match the linked size? expected: " + au.getSize() + " got: " + size);
            }
        }
        Memory.setMemory(pMemory.getPage(0));
        return pMemory;
    }


    @Override
    public void assemble(File[] files) {

        this.preProcessor = createPreProcessor();

        for(File f: files){
            assemble(f);
        }

        linkGlobal();
    }

    protected abstract P createPreProcessor();
    protected abstract DataStatement getInstruction(String mnemonic);

    public Label getLabel(String token) {
        if(globalLabelMap.containsKey(token)) {
            return globalLabelMap.get(token);
        }else{
            throw new LableNotDeclaredError("Label: " + token + " is not defined or not in the current scope");
        }
    }

    public void setCurrentAssemblyUnitAlignment(int i){
        currentAssemblyUnit.setAlignment(i);
    }

    public static long align(long address, long alignment){
        return address + getAlignmentOffset(address, alignment);
    }

    public static long getAlignmentOffset(long address, long alignment){
        if(bitCount(alignment) != 1){

        }else{
            long mask = alignment - 1;
            long offset = ((~(mask & address)) + 1) & mask;
            return offset;
        }
        return 0;
    }

    public static int bitCount(long number){
        int count = 0;
        for(int i = 0; i < 64; i ++){
            count += ((number >> i) & 0b1) > 0?1:0;
        }
        return count;
    }
}