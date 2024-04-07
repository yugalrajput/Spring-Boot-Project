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

@RestController
@RequestMapping(value = "User")
public class UserCtl {

    @Autowired
    public UserService service;

    @PostMapping("save")
    public ORSResponse save(@RequestBody UserForm form) {

        ORSResponse res = new ORSResponse();

        UserDTO dto = new UserDTO();
        dto.setId(form.getId());
        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setLoginId(form.getLoginId());
        dto.setPassword(form.getPassword());
        dto.setDob(form.getDob());

        if (dto.getId() != null && dto.getId() > 0) {
            service.update(dto);
            res.addData(dto.getId());
            res.addMessage("Data Updated Successfully..!!");
        } else {
            long pk = service.add(dto);
            res.addData(pk);
            res.addMessage("Data added Successfully..!!");
        }
        return res;
    }

    @GetMapping("get/{id}")
    public ORSResponse get(@PathVariable long id) {
        ORSResponse res = new ORSResponse();
        UserDTO dto = service.findById(id);
        res.addData(dto);
        return res;
    }

    @GetMapping("delete/{ids}")
    public ORSResponse delete(@PathVariable long[] ids) {
        ORSResponse res = new ORSResponse();
        for (long id : ids) {
            service.delete(id);
        }
        res.addMessage("data deleted successfully");
        return res;
    }

    @PostMapping("search/{pageNo}")
    public ORSResponse search(@RequestBody UserForm form, @PathVariable int pageNo) {

        ORSResponse res = new ORSResponse();

        UserDTO dto = new UserDTO();
        dto.setFirstName(form.getFirstName());

        List list = service.search(dto, pageNo, 5);

        if (list.size() == 0) {
            res.addMessage("Result not found...!!!");
        } else {
            res.addData(list);
        }
        return res;
    }


}
