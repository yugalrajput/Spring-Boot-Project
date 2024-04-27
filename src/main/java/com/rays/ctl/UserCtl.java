package com.rays.ctl;

import com.rays.common.*;
import com.rays.dto.AttachmentDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.AttachmentService;
import com.rays.service.RoleService;
import com.rays.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "User")
public class UserCtl extends BaseCtl {

    @Autowired
    public UserService userService;

    @Autowired
    public RoleService roleService;


    @Autowired
    public AttachmentService attachmentService;

    @GetMapping("preload")
    public ORSResponse preload() {
        ORSResponse res = new ORSResponse();

        RoleDTO dto = new RoleDTO();
        List<DropDownList> roleList = roleService.search(dto, 0, 0);

        res.addResult("roleList", roleList);
        return res;
    }

    @PostMapping("save")
    public ORSResponse save(@RequestBody @Valid UserForm form, BindingResult bindingResult) {

        ORSResponse res = validate(bindingResult);

        if (!res.isSuccess()) {
            return res;
        }

        UserDTO dto = (UserDTO) form.getDto();
        if (dto.getId() != null && dto.getId() > 0) {
            dto.setRoleId(form.getRoleId());
            userService.update(dto);
            res.addData(dto.getId());
            res.addMessage("Data Updated Successfully..!!");
        } else {
            dto.setRoleId(form.getRoleId());
            long pk = userService.add(dto);
            res.addData(pk);
            res.addMessage("Data added Successfully..!!");
        }
        return res;
    }

    @GetMapping("get/{id}")
    public ORSResponse get(@PathVariable long id) {
        ORSResponse res = new ORSResponse();
        UserDTO dto = userService.findById(id);
        res.addData(dto);
        return res;
    }

    @GetMapping("delete/{ids}")
    public ORSResponse delete(@PathVariable long[] ids) {
        ORSResponse res = new ORSResponse();
        for (long id : ids) {
            userService.delete(id);
        }
        res.addMessage("data deleted successfully");
        return res;
    }

    @PostMapping("search/{pageNo}")
    public ORSResponse search(@RequestBody UserForm form, @PathVariable int pageNo) {

        ORSResponse res = new ORSResponse();

        UserDTO dto = new UserDTO();
        dto.setFirstName(form.getFirstName());

        List list = userService.search(dto, pageNo, 5);

        if (list.size() == 0) {
            res.addMessage("Result not found...!!!");
        } else {
            res.addData(list);
        }
        return res;
    }


    @PostMapping("/profilePic/{userId}")
    public ORSResponse uploadPic(@PathVariable Long userId, @RequestParam("file") MultipartFile file,
                                 HttpServletRequest req) {

        AttachmentDTO attachmentDto = new AttachmentDTO(file);

        attachmentDto.setDescription("profile pic");

        attachmentDto.setUserId(userId);

        UserDTO userDto = userService.findById(userId);

        if (userDto.getImageId() != null && userDto.getImageId() > 0) {

            attachmentDto.setId(userDto.getImageId());

        }

        Long imageId = attachmentService.save(attachmentDto);

        if (userDto.getImageId() == null) {

            userDto.setImageId(imageId);

            userService.update(userDto);
        }

        ORSResponse res = new ORSResponse();

        res.addResult("imageId", imageId);

        return res;
    }

    @GetMapping("/profilePic/{userId}")
    public @ResponseBody void downloadPic(@PathVariable Long userId, HttpServletResponse response) {

        try {

            UserDTO userDto = userService.findById(userId);

            AttachmentDTO attachmentDTO = null;

            if (userDto != null) {
                attachmentDTO = attachmentService.findById(userDto.getImageId());
            }

            if (attachmentDTO != null) {
                response.setContentType(attachmentDTO.getType());
                OutputStream out = response.getOutputStream();
                out.write(attachmentDTO.getDoc());
                out.close();
            } else {
                response.getWriter().write("ERROR: File not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
