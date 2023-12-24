package application.entities;

import application.services.DatabaseService;

import static application.Main.logger;

public class Profile {

    private String name;
    private String phoneNumber;
    private String location;
    private int profileId;

    public Profile() {

    }

    public Profile(int profileId, String name, String phoneNumber, String location) {
        this.profileId = profileId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }





    public boolean areChangesSaved() {
        DatabaseService databaseService =new DatabaseService();
        try {
            databaseService.updateObject(this,"Profile","profileId");
        } catch (Exception e) {
            logger.severe("Error: in saving changes, try again please\n");
        }
        return true;
    }



    public int getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return profileId+"";
    }
}
