package com.rays.ctl;


import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.UserDTO;
import com.rays.form.UserRegistrationForm;
import com.rays.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "Auth")
public class LoginCtl extends BaseCtl {

    @Autowired
    public UserService service;

    @PostMapping("signUp")
    public ORSResponse signUp(@RequestBody @Valid UserRegistrationForm form, BindingResult bindingResult) {
        ORSResponse res = validate(bindingResult);

        if (!res.isSuccess()) {
            return res;
        }

        UserDTO dto = new UserDTO();
        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setLoginId(form.getLoginId());
        dto.setPassword(form.getPassword());
        dto.setDob(form.getDob());
        dto.setRoleId(2L);

        long pk = service.add(dto);
        res.addData(pk);
        res.addMessage("User Registration Successfully");
        return res;

    }

}
