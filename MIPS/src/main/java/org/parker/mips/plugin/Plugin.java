/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parker.mips.plugin;

/**
 *
 * @author parke
 */
@SuppressWarnings("unused")
public interface Plugin {
    
    void onLoad();
    
    void onUnload();
    
    void onEnable();
    
    void onDisable();
}
