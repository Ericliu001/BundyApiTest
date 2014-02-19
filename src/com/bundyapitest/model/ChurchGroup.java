package com.bundyapitest.model;

public class ChurchGroup {
	String groupName;
	String groupType;
	String groupTime;
	String groupDescription;
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getGroupTime() {
		return groupTime;
	}
	public void setGroupTime(String groupTime) {
		this.groupTime = groupTime;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return groupName + " " + groupType + " " + groupTime + " \n" + groupDescription ;
	}
}
