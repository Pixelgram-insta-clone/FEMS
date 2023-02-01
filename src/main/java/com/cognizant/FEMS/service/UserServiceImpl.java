package com.cognizant.FEMS.service;


import com.cognizant.FEMS.model.User;
import com.cognizant.FEMS.proxy.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserProxy userProxy;

    @Override
    public User getUser(int id){
        return userProxy.getUserById(id);
    }
}
