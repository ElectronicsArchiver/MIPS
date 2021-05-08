/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.retargetableassembler.util.linking;

import org.parker.retargetableassembler.util.Line;
import org.parker.retargetableassembler.exception.LabelNotDeclaredError;

public class GlobalLabel extends Label{

    private final AssemblyUnit parentAssemblyUnit;

    public GlobalLabel(AssemblyUnit au, String mnemonic, Line line) {
        super(mnemonic, line);
        this.parentAssemblyUnit = au;
    }

    @Override
    public long getAddress() {
        if(!(parentAssemblyUnit.getAsuLabelMap().containsKey(this.mnemonic))){
            throw new LabelNotDeclaredError(this);
        }
        return parentAssemblyUnit.getAsuLabelMap().get(this.mnemonic).getAddress();
    }
}