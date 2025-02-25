///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package itstep.learning.dal.dao;
//
// public static UserAccess fromResultSet( ResultSet rs ) throws SQLException {
//        UserAccess ua = new UserAccess();
//        ua.setUserAccessId( UUID.fromString( rs.getString( "user_access_id" ) ) );
//        ua.setUserId( UUID.fromString( rs.getString( "user_id" ) ) );
//        ua.setLogin( rs.getString( "login" ) );
//        ua.setSalt( rs.getString( "salt" ) );
//        ua.setDk( rs.getString( "dk" ) );
//        ua.setRoleId(rs.getString( "role_id" ) );
//        java.sql.Timestamp timestamp = rs.getTimestamp( "ua_delete_dt" ) ;
//        ua.setDeleteMoment( 
//                timestamp == null ? null : new Date( timestamp.getTime() ) ) ;
//        return ua;
//    }