//
//package itstep.learning.dal.dao;
//
//
//public class AccessTokenDao {
//    
//    public AccessToken create(UserAccess userAccess){
//    if(userAccess==null) return null;
//    AccessToken token=new AccessToken();
//    token.setAccessTokenId(UUID.randomUUID());
//    token.setUserAccessId(userAccess.getUserAccessId());
//    Date date= new Date();
//    token.setIssuedAt(date);
//    token.setexpiresAt(new Date(date.getTime()+100));
//    
//    String sql="INSERT INTO access_tokens()access_token_id, user_access_id, "
//            +"issued_at, expired_at") VALUES(?,?,?,?)";"
//            
//     try(PreparedStatement prep=dbService.getConnection().prepareStatement(sql));
//     prep.setString(1, token.getAccessTokenId().toString());
//     prep.setString(2, token.getUserAccessId().toString());
//     prep.setTimestamp(3, new Timestamp(token.getIssedAt().getTime()));
//     prep.setTimestamp(4, new Timestamp(token.getExpiredAt().getTime()));
//     prep.executeUpdate();
//     dbService.getConnection().commit();
//    
//    }
//     catch(SQLException ex{
//     logger.log(Level.WARNING,)
//     })
//}
//
