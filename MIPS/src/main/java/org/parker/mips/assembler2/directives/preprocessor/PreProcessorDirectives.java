package org.parker.mips.assembler2.directives.preprocessor;

import org.parker.mips.assembler2.base.preprocessor.BasePreProcessor;
import org.parker.mips.assembler2.base.preprocessor.IntermediateDirective;
import org.parker.mips.assembler2.base.preprocessor.IntermediateStatement;
import org.parker.mips.assembler2.exception.DirectivesError;
import org.parker.mips.assembler2.util.ExpressionCompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreProcessorDirectives {

    private static final HashMap<String, PreProcessorDirective> handlerMap = new HashMap<>();

    private static final PreProcessorDirective INCLUDE = (s, is, index, ec, preProcessor) -> {

    };

    private static final PreProcessorDirective IF = (s, is, index, ec, preProcessor) -> {

        index ++;
        int ifLevel = 0;
        int stage = 0;
        boolean statementCondition = (Boolean)ec.compileExpression(s.expressionString, s.parentLine, s.expressionStartingIndex).evaluate();
        boolean completedStatement = false;

        while(true){
            if(is.size() <= index){
                throw new DirectivesError("reached end of file before .endif", s.parentLine);
            }

            IntermediateStatement statement = is.get(index);

            if(statement instanceof IntermediateDirective){
                String identifier = ((IntermediateDirective) statement).identifier;

                if(identifier.equals("if")){
                    ifLevel++;
                }else if(identifier.equals("endif")){
                    if(ifLevel == 0){
                        is.remove(index);
                        break;
                    }
                    ifLevel--;
                }

                if(ifLevel == 0) {
                    if (identifier.equals("elseif")) {

                        if (stage <= 1) {
                            if(statementCondition){
                                completedStatement = true;
                                //continue;
                            }else {
                                    statementCondition = (Boolean) ec.compileExpression(
                                            ((IntermediateDirective) statement).expressionString,
                                            ((IntermediateDirective) statement).parentLine,
                                            ((IntermediateDirective) statement).expressionStartingIndex).evaluate();
                            }
                        } else {
                            throw new DirectivesError("elseif directive cannot follow else directive", s.parentLine);
                        }

                        stage = 1;
                        is.remove(index);
                        continue;
                    } else if (identifier.equals("else")) {

                        if (stage < 2) {
                            if(statementCondition){
                                completedStatement = true;
                                //continue;
                            }else {
                                    statementCondition = true;
                            }
                        } else {
                            throw new DirectivesError("else directive cannot follow else directive", s.parentLine);
                        }

                        stage = 2;
                        is.remove(index);
                        continue;
                    }
                }
            }

            if((!statementCondition) || completedStatement) {
                is.remove(index);
            }else{
                index ++;
            }
        }
    };

    public static final Pattern defineArgPattern = Pattern.compile("\\s*([a-zA-Z_$][a-zA-Z_$0-9]*)((\\s+(\\S.*))){0,1}");

    private static final PreProcessorDirective DEFINE = (s, is, index, ec, preProcessor) -> {
        Matcher m = defineArgPattern.matcher(s.expressionString);
        if(m.find()){
            String defineName = m.group(1);
            String mnemonicReplacement = m.group(4);

            if(mnemonicReplacement == null) {
                preProcessor.define(defineName);
            }else{
                preProcessor.addDirectMnemonicReplacement(defineName, mnemonicReplacement);
            }
        }
    };

    private static final PreProcessorDirective MACRO = (s, is, index, ec, preProcessor) -> {
        index ++;
        List<IntermediateStatement> myStatements = new ArrayList<>();
        while(true){
            if(is.size() <= index){
                throw new DirectivesError("reached end of file before .endm", s.parentLine);
            }

            IntermediateStatement statement = is.get(index);
            is.remove(index);

            if(statement instanceof IntermediateDirective){
                String identifier = ((IntermediateDirective) statement).identifier;

                if(identifier.equals("endm")){
                    break;
                }else{
                    myStatements.add(statement);
                }
            }

            index ++;
        }

        for(int i = 0; i < myStatements.size(); i ++){
            System.out.println(myStatements.get(i).toString());
        }
    };

    static{

        //define symbols
        handlerMap.put("define", DEFINE);
        handlerMap.put("asg", null);
        handlerMap.put("unasg", null);
        handlerMap.put("undefine", null);

        //macros
        handlerMap.put("macro", MACRO);
        handlerMap.put("endm", new ConservedDirectiveSpot("Found endm directive without endm"));

        //file reference
        handlerMap.put("include", INCLUDE);

        //conditional statements
        handlerMap.put("if", IF);
        handlerMap.put("else", new ConservedDirectiveSpot("Found else directive without if"));
        handlerMap.put("elseif", new ConservedDirectiveSpot("Found elseif directive without if"));
        handlerMap.put("endif", new ConservedDirectiveSpot("Found endif directive without if"));

        //loop
        handlerMap.put("loop", null);
        handlerMap.put("break", new ConservedDirectiveSpot("Found break directive without loop"));
        handlerMap.put("endloop", new ConservedDirectiveSpot("Found endloop directive without loop"));
    }

    public static PreProcessorDirective getHandler(String directiveName){
        if(handlerMap.containsKey(directiveName)) {

            return handlerMap.get(directiveName);
        }else{
            throw new RuntimeException("Does not contain: " + directiveName);
        }
    }

    public static boolean hasDirective(String directiveName){
        return handlerMap.containsKey(directiveName);
    }

    private static class ConservedDirectiveSpot implements PreProcessorDirective {

        private final String message;

        public ConservedDirectiveSpot(String message){
            this.message = message;
        }

        @Override
        public void parse(IntermediateDirective s, List<IntermediateStatement> is, int index, ExpressionCompiler ec, BasePreProcessor preProcessor) {
            throw new DirectivesError(message, s.parentLine);
        }
    }
}
