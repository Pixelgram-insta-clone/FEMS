package com.cognizant.FEMS.proxy;
import com.cognizant.FEMS.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-proxy", url = "${tmem-user}")
public interface UserProxy {

    @GetMapping("users/{id}")
    User getUserById(
            @PathVariable int id
    );

}
