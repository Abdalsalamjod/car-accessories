package Application.Entities;

import Application.Services.InstallationRequest;
import Application.Services.Order;

import java.util.List;
import java.util.ArrayList;

public class Profile {
    public int profileId;
    public String Name;
    public String phoneNumber;
    public String location;

    private List<Order> orderHistory;
    private List<InstallationRequest> installationRequests;

    public Profile() {

        this.orderHistory = new ArrayList<>();
        this.installationRequests = new ArrayList<>();
    }

    // Methods to manage user profile
    public void updateUserProfile(String newEmail, String newPassword) {

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
}
