package CRM.Service;

import java.util.List;

import CRM.Repository.StatusReposiroty;
import ProjectCRM.Model.StatusTask;

public class StatusService {
	public List<StatusTask> getAllStatus(){
		return StatusReposiroty.getAllStatus();
	}
}
