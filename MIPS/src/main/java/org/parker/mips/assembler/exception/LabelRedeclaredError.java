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
package org.parker.mips.assembler.exception;

import org.parker.mips.assembler.util.linking.Label;

public class LabelRedeclaredError extends AssemblerError{

    private final Label label;

    public LabelRedeclaredError(Label label) {
        super("Label: " + label.mnemonic + " has already been declared in this assembly unit", label.line);
        this.label = label;
    }
}