package io.rong.util;

public enum HostType {
	API("http://api.cn.ronghub.com"),		//公有云
	//API("http://103.36.132.10:81"), 		//私有云
	SMS("http://api.sms.ronghub.com");
	private String type;

	private HostType(String type) {
		this.type = type;
	}

	public static HostType getType(String state) {
		for (HostType deviceType : HostType.values()) {
			if (deviceType.type.equalsIgnoreCase(state)) {
				return deviceType;
			}
		}
		throw new RuntimeException(state + " is not a valid Host Type!");
	}

	public String getStrType() {
		return type;
	}
}