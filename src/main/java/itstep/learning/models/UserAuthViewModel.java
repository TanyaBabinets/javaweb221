package itstep.learning.models;

import itstep.learning.dal.dto.User;
import itstep.learning.dal.dto.UserAccess;
import jakarta.security.enterprise.identitystore.openid.AccessToken;

public class UserAuthViewModel {

    private User user;
    private UserAccess userAccess;  
    private AccessToken accessToken;

    public UserAuthViewModel(User user, AccessToken accessToken, UserAccess userAccess) {
        this.user = user;
        this.accessToken = accessToken;
        this.userAccess = userAccess;
    }

  

    public UserAuthViewModel() {
    }
    
     public UserAccess getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(UserAccess userAccess) {
        this.userAccess = userAccess;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    
//    AccessToken token = datacontext.getAccessTokenDao().create(user);
//
//    restResponse.setStatus (
//
//    200 ).setData(user);
//    restService.sendResponse (resp, restResponse );

    
}
