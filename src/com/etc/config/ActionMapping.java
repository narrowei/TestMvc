package com.etc.config;

import java.util.HashMap;

public class ActionMapping {
	private String path;
	private String type;
	private HashMap<String,ForwardBean> forwards= new HashMap<String,ForwardBean>();
	
	public void addForwardBean(ForwardBean forward){
		forwards.put(forward.getName(), forward);
	}
	public ForwardBean findForward(String name){
		return forwards.get(name);
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
