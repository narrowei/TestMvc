package com.etc.config;

import java.util.HashMap;

public class ActionMappings {
	private HashMap<String,ActionMapping> mappings = new HashMap<String,ActionMapping>();
	
	public void addActionMapping(ActionMapping mapping){
		mappings.put(mapping.getPath(), mapping);
	}
	
	public ActionMapping findActionMapping(String path){
		return mappings.get(path);
	}
	public String toString(){
		return mappings.toString();
	}
}
