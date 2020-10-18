/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parker.mips.Processor.InternalSystemCallPlugins;

import org.parker.mips.PluginHandler.SystemCallPluginHandler.SystemCall;
import org.parker.mips.PluginHandler.SystemCallPluginHandler.SystemCallData;
import org.parker.mips.PluginHandler.SystemCallPluginHandler.SystemCallPlugin;
import org.parker.mips.PluginHandler.SystemCallPluginHandler.SystemCallPluginFrame;

/**
 *
 * @author parke
 */
public class DefaultSystemCalls extends SystemCallPlugin {

    public DefaultSystemCalls() {

        super(6, "Default_System_Calls");

        SystemCallData[] scd = this.getSystemCallDataFromClass(this.getClass());

        this.systemCalls[0] = new SystemCall(scd[0], "SYSTEM_HALT_PROGRAM") {
            @Override
            public void handleSystemCall() {
                stopProcessor();
                logRunTimeSystemCallMessage("Halted Processor");
            }
        };
        this.systemCalls[1] = new SystemCall(scd[1], "SYSTEM_RANDOM_NUM") {
            @Override
            public void handleSystemCall() {
                setRegister(2, (int) (Math.random() * (getRegister(5) + 1 - getRegister(4))) + getRegister(4));
            }
        };
        this.systemCalls[2] = new SystemCall(scd[2], "SYSTEM_SLEEP_MILLS") {
            @Override
            public void handleSystemCall() {
                try {
                    Thread.sleep(getRegister(4));
                } catch (Exception e) {

                }
            }
        };
        this.systemCalls[3] = new SystemCall(scd[3], "SYSTEM_SLEEP_DELTA_MILLS") {

            long lastTimeCheck = 0;

            @Override
            public void handleSystemCall() {
                try {
                    Thread.sleep(getRegister(4) - (System.currentTimeMillis() - lastTimeCheck));

                } catch (Exception e) {

                }

                lastTimeCheck = System.currentTimeMillis();

            }
        };
        this.systemCalls[4] = new SystemCall(scd[4], "SYSTEM_BREAK_POINT") {
            @Override
            public void handleSystemCall() {
                throwBreakPoint();
            }
        };
        this.systemCalls[5] = new SystemCall(scd[5], "SYSTEM_GET_MILLIS") {
            @Override
            public void handleSystemCall() {
                setRegister(2, (int) System.currentTimeMillis());
            }
        };

    }

    @Override
    public void init() {

    }

    @Override
    public SystemCallPluginFrame getPluginFrame() {
        return null;
    }

}
