package Application.Entities;

import Application.LoggerUtility;
import Application.Services.InstallationRequest;
import Application.Services.Order;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Profile {
    public int profileId;
    public String Name;
    public String phoneNumber;
    public String location;

    private List<Order> orderHistory;
    private List<InstallationRequest> installationRequests;
    private static Logger logger = Logger.getLogger(LoggerUtility.class.getName());

    public Profile() {

        this.orderHistory = new ArrayList<>();
        this.installationRequests = new ArrayList<>();
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


    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void setInstallationRequests(List<InstallationRequest> installationRequests) {
        this.installationRequests = installationRequests;
    }

    // Methods to manage order history
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    // Methods to manage installation requests
    public List<InstallationRequest> getInstallationRequests() {
        return installationRequests;
    }

    public void addInstallationRequest(InstallationRequest request) {
        installationRequests.add(request);
    }

    public boolean areChangesSaved() {
      // TODO: connect to DB
        return true;
    }

    public boolean viewOrderHistory() {
        //TODO: connect to DB
        for (Order order:this.orderHistory)
        {
            logger.info(order.toString());
        }
        return true;
    }

    public boolean viewInstallationRequests() {
        //TODO: connect to DB
        for (InstallationRequest requstes :this.installationRequests)
        {
            logger.info(requstes.toString());
        }
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
