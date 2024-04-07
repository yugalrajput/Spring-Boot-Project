package com.rays.ctl;

import com.rays.common.ORSResponse;
import com.rays.common.SpringResponse;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCtl {
    @RestController
    @RequestMapping(value = "User")
    public class UserCtl {

        @GetMapping
        public SpringResponse display() {
            SpringResponse response = new SpringResponse();


            response.setData("data inserted");
            response.setMessage("success");

            Map errors = new HashMap();
            errors.put("firstname", "firstname is required");
            errors.put("lastname", "lastname is required");
            errors.put("loginId", "loginId is required");
            errors.put("password", "password is required");

            Map map = new HashMap();
            map.put("inputerror", errors);

            response.setResult(map);

            return response;
        }


        @GetMapping(value = "/testORSResponse")
        public ORSResponse testORSResponse() {
            ORSResponse response = new ORSResponse();

            Map errors = new HashMap();
            errors.put("firstname", "firstname is required");
            errors.put("lastname", "lastname is required");
            errors.put("loginId", "loginId is required");
            errors.put("password", "password is required");

            response.addInputError(errors);
            response.addMessage("data inserted");

            UserDTO dto = new UserDTO();
            dto.setId(1L);
            dto.setFirstName("yugal");
            dto.setLastName("rajput");
            dto.setLoginId("yugal@gmail.com");
            dto.setPassword("123456");

            response.addData(dto);
            response.addResult("result", "success");

            return response;
        }


    }

}
