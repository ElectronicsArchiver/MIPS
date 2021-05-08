package org.parker.mips.assembler.base.preprocessor;

import org.parker.mips.assembler.util.CompiledExpression;
import org.parker.mips.assembler.util.Line;

public class PreProcessedAssemblyStatement implements PreProcessedStatement{

    public final Line parentLine;
    public final String identifier;
    public final String expressionString;
    public final CompiledExpression[] args;


    public PreProcessedAssemblyStatement(Line parentLine, String identifier, String expressionString, CompiledExpression[] args){
        this.parentLine = parentLine;
        this.identifier = identifier;
        this.expressionString = expressionString;
        this.args = args;
    }

    @Override
    public String toString() {
        String temp = identifier + " ";
        for(int i = 0; i < args.length; i ++){
            temp += args[i].toString();
            if(i < args.length - 1){
                temp += ", ";
            }
        }
        return temp;
    }

    @Override
    public final Line getLine() {
        return parentLine;
    }
}