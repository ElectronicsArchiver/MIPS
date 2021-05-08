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
package org.parker.assembleride.architecture;


import org.parker.assembleride.core.MIPS;
import org.parker.assembleride.plugin.ArchitecturePluginLoader;
import org.parker.assembleride.plugin.base.PluginDescription;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchitecturePluginHandler {

    private static ComputerArchitecture ca;
    private static PluginDescription description;
    private final static Logger LOGGER = Logger.getLogger(ArchitecturePluginHandler.class.getName());

    public static synchronized void loadArchitecturePlugin() {
        if(ca != null){
            throw new IllegalStateException("Cannot load Architecture Plugin after startup");
        }
        ComputerArchitecture arcPlugin;
        try {
            arcPlugin = ArchitecturePluginLoader.loadPluginS(new File(MIPS.JAR_PATH), "/org/parker/mips/architecture/plugin.yml");
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Failed to load Architecture Plugin", e);
            return;
        }

        ComputerArchitecture ca = (ComputerArchitecture) arcPlugin;
        ca.onLoad();

        ca.createGUI().setVisible(true);
    }

    public static void setToLoadDescription(PluginDescription description) {
        if(description != null){
            ArchitecturePluginHandler.description = description;
        }else{
            throw new IllegalStateException("Cannot set Description after initial load");
        }
    }

    public static PluginDescription getDescription(){
        return description;
    }

    public static void requestSystemExit(){
        ca.requestSystemExit();
    }
    public static void requestSystemExit(ComputerArchitecture.SystemClosingEvent sce){
        ca.requestSystemExit(sce);
    }
}