package Application.Entities;

import Application.LoggerUtility;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Profile {
    public int profileId;
    public String Name;
    public String phoneNumber;
    public String location;

    private static Logger logger = Logger.getLogger(LoggerUtility.class.getName());

    public Profile() {

    }

    public Profile(int profileId, String name, String phoneNumber, String location) {
        this.profileId = profileId;
        this.Name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }


    public void setName(String name) {
        Name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }





    public boolean areChangesSaved() {
      // TODO: connect to DB
        return true;
    }

    public boolean viewOrderHistory() {
        //TODO: connect to DB
//        for (Order order:this.orderHistory)
//        {
//            logger.info(order.toString());
//        }
        return true;
    }

    public boolean viewInstallationRequests() {
        //TODO: connect to DB
//        for (InstallationRequest requstes :this.installationRequests)
//        {
//            logger.info(requstes.toString());
//        }
        return true;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }
}
