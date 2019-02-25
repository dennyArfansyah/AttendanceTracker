package denny.com.attendancetracker.utils;

import java.util.List;

import denny.com.attendancetracker.models.Approval;
import denny.com.attendancetracker.models.Division;
import denny.com.attendancetracker.models.Employee;
import denny.com.attendancetracker.models.LeaveType;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class DatabaseHelper {

    // region Division
    public static void insertUpdateDivision(List<Division> divisionList){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for(int i=0; i<divisionList.size(); i++){
            Division currentDivision = realm.where(Division.class).equalTo("id",
                    divisionList.get(i).getId()).findFirst();
            if(currentDivision == null){
                realm.copyToRealm(divisionList.get(i));
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    public static RealmResults<Division> getDivisions(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Division> divisions = realm.where(Division.class)
                .findAll()
                .sort("id", Sort.ASCENDING);
        return divisions;
    }
    // endregion

    // region User
    public static void insertEmployee(Employee employee){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(employee);
        realm.commitTransaction();
        realm.close();
    }

    public static Employee getEmployee(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Employee.class).findFirst();
    }

    public static void updateEmpolyee(Employee employee){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Employee updatedEmployee = realm.where(Employee.class).findFirst();
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setDob(employee.getDob());
        updatedEmployee.setDivision(employee.getDivision());
        realm.commitTransaction();
        realm.close();
    }

    public static void refreshToken(String token){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Employee updatedEmployee = realm.where(Employee.class).findFirst();
        updatedEmployee.setToken(token);
        realm.commitTransaction();
        realm.close();
    }

    public static void changePassword(String password){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Employee updatedEmployee = realm.where(Employee.class).findFirst();
        updatedEmployee.setPassword(password);
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteEmployee(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Employee employee = realm.where(Employee.class).findFirst();
        employee.deleteFromRealm();
        realm.commitTransaction();
        realm.close();

    }
    // endregion

    // region LeaveType
    public static void insertUpdateLeaveType(List<LeaveType> leaveTypeList){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for(int i=0; i<leaveTypeList.size(); i++){
            LeaveType currentLeaveType = realm.where(LeaveType.class).equalTo("id",
                    leaveTypeList.get(i).getId()).findFirst();
            if(currentLeaveType == null){
                realm.copyToRealm(leaveTypeList.get(i));
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    public static RealmResults<LeaveType> getLeaveTypes(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<LeaveType> leaveTypes = realm.where(LeaveType.class)
                .findAll()
                .sort("id", Sort.ASCENDING);
        return leaveTypes;
    }
    // endregion

    // region Approval
    public static void insertUpdateApproval(List<Approval> approvalList){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for(int i=0; i<approvalList.size(); i++){
            Approval currentApproval = realm.where(Approval.class).equalTo("_id",
                    approvalList.get(i).get_id()).findFirst();
            if(currentApproval == null){
                realm.copyToRealm(approvalList.get(i));
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    public static RealmResults<Approval> getApprovals(int status){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Approval> approvals = realm.where(Approval.class).equalTo("status", status)
                .findAll()
                .sort("_id", Sort.ASCENDING);
        return approvals;
    }
    // endregion

}
