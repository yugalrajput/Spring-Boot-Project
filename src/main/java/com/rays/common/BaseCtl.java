package com.rays.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCtl {
    public ORSResponse validate(BindingResult bindingResult) {
        ORSResponse response = new ORSResponse(true);

        if (bindingResult.hasErrors()) {
            response.setSuccess(false);
            Map<String, String> errors = new HashMap<String, String>();
            List<FieldError> list = bindingResult.getFieldErrors();
            list.forEach(x -> {

                errors.put(x.getField(), x.getDefaultMessage());
            });
            response.addInputError(errors);

        }
        return response;
    }


}

