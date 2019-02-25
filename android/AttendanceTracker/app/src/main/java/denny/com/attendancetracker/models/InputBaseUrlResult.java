package denny.com.attendancetracker.models;

import java.util.List;

public class InputBaseUrlResult extends BaseResult{

    private Data data;

    public InputBaseUrlResult(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setDataList(Data data) {
        this.data = data;
    }

    public class Data {
        private List<Division> divisions;
        private List<Service> services;
        private List<LeaveType> leave_types;

        public Data(List<Division> divisions, List<Service> services) {
            this.divisions = divisions;
            this.services = services;
        }

        public List<Division> getDivisions() {
            return divisions;
        }

        public void setDivisions(List<Division> divisions) {
            this.divisions = divisions;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public List<LeaveType> getLeave_types() {
            return leave_types;
        }

        public void setLeave_types(List<LeaveType> leave_types) {
            this.leave_types = leave_types;
        }
    }
}
