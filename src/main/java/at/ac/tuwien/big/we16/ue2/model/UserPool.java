package at.ac.tuwien.big.we16.ue2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserPool {
    private Map<String, User> users = new ConcurrentHashMap<String, User>();
    
    public void registerUser(User user) {
        if(!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        }
    }
    
    public List<User> getUsers() {
        return new ArrayList<User>(users.values());
    }
    
    public User getUser(String username, String password) {
        User user;
        if ((user = users.get(username)) != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    public User getUser(String username) {
        User user;
        if ((user = users.get(username)) != null) {
            return user;            
        }
        return null;
    }
    
}
