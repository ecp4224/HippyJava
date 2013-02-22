package com.ep.hippyjava.model;

import static com.ep.hippyjava.utils.Constants.GSON;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.ep.hippyjava.utils.WebUtils;

public class HipchatUser {
    private int user_id;
    private String name;
    private String mention_name;
    private String email;
    private String title;
    private String photo_url;
    private String status;
    private String status_message;
    private int is_group_admin;
    private int is_deleted;
    private BufferedImage cache;
    private static HashMap<String, HipchatUser> user_cache = new HashMap<String, HipchatUser>(); 
    
    /**
     * Return the HipchatUser object that has the name of <b>nick</b>
     * @param nick
     *            The nick to get the users for
     * @param APIKey
     *             The API Key to use when using Hipchat's HTTP API
     * @return
     *        The HipchatUser object with the given nick.
     */
    public static HipchatUser createInstance(String nick, String APIKey) {
        if (!user_cache.containsKey(nick)) {
            HipchatUser[] users = getHipchatUsers(APIKey);
            for (HipchatUser user : users) {
                if (!user_cache.containsKey(user.name))
                    user_cache.put(user.name, user);
            }
            if (!user_cache.containsKey(nick))
                return null;
            else
                return user_cache.get(nick);
        }
        else
            user_cache.get(nick);
        return null;
    }
    
    /**
     * Return the HipchatUser object that has the user id of <b>ID</b>
     * @param ID
     *            The user id to get the users for
     * @param APIKey
     *             The API Key to use when using Hipchat's HTTP API
     * @return
     *        The HipchatUser object with the given user id.
     */
    public static HipchatUser createInstance(int ID, String APIKey) {
        HipchatUser[] users = getHipchatUsers(APIKey);
        for (HipchatUser user : users) {
            if (user.user_id == ID)
                return user;
        }
        return null;
    }
    
    /**
     * Get an array of HipchatUser objects that the APIKey passed in the parameter has
     * access to.
     * @param APIKey
     *              The API Key to use
     * @return
     *        An array of HipchatUsers
     */
    public static HipchatUser[] getHipchatUsers(String APIKey) {
        return getHipchatUserHolder(APIKey).users;
    }
    
    private static HipchatUserHolder getHipchatUserHolder(String APIKey) {
        try {
            String JSON = WebUtils.getTextAsString("https://api.hipchat.com/v1/users/list?format=json&auth_token=" + APIKey);
            HipchatUserHolder data = GSON.fromJson(JSON, HipchatUserHolder.class);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            HipchatUserHolder u = new HipchatUserHolder();
            u.users = new HipchatUser[0];
            return u;
        }
    }
    
    private HipchatUser() { }
    
    public int getUserID() {
        return user_id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getMentionName() {
        return mention_name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getPhotoUrl() {
        return photo_url;
    }
    
    public BufferedImage getProfilePhoto() {
        if (cache == null) {
            try {
                cache = ImageIO.read(new URL(getPhotoUrl()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getStatusMessage()  {
        return status_message;
    }
    
    public boolean isGroupAdmin() {
        return is_group_admin == 1;
    }
    
    public boolean isDeletedAccount() {
        return is_deleted == 1;
    }
    
    private static class HipchatUserHolder {
        public HipchatUser[] users;
        public HipchatUserHolder() { }
    }
}
