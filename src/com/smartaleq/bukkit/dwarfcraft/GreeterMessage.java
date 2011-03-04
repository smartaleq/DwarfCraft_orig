package com.smartaleq.bukkit.dwarfcraft;

public class GreeterMessage {
	String leftClick;
	String rightClick;
	
	GreeterMessage(String newLeftClick, String newRightClick) {
		this.leftClick = newLeftClick;
		this.rightClick = newRightClick;
	}
	
	public String getLeftClickMessage() { return leftClick; }
	public String getRightClickMessage() { return rightClick; }
}