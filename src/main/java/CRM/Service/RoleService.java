package CRM.Service;

import java.util.List;

import CRM.Repository.RoleRepository;
import CRM.Repository.UserRepository;
import ProjectCRM.Model.Role;

public class RoleService {
	private RoleRepository roleRepository = new RoleRepository();
	
	public List<Role>getAllRoles(){
		return roleRepository.getAllRoles();
	}
	
	public String addRole(Role role) {
		if (role == null || role.getRoleName() == null || role.getRoleName().trim().isEmpty()) {
	        return "error:Tên quyền không được trống!";
	    }
	    try {
	        roleRepository.addRole(role);
	        return "success:Thêm quyền thành công!";
	    } catch (Exception e) {
	        return "error:Không thể thêm quyền " + e.getMessage();
	    }
	}
	
	public boolean deleteRole(int id) {
		int count =  roleRepository.deleteId(id);
		
		return count > 0;
	}
	
	public boolean updateRole(Role role) {
		return roleRepository.updateRole(role);
	}
}
