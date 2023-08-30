package ProjectCRM.Model;

public class StatusTask {
	private int statusID;
	private String statusName;
	private String statusType;
	
	public StatusTask() {

	}

	public StatusTask(int statusID, String statusName, String statusType) {
		this.statusID = statusID;
		this.statusName = statusName;
		this.statusType = statusType;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
}
