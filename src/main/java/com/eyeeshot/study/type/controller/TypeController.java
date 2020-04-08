package com.eyeeshot.study.type.controller;

import com.eyeeshot.study.common.ResponseModel;
import com.eyeeshot.study.type.domain.request.CreateTypeRequestDto;
import com.eyeeshot.study.type.domain.request.ListTypeRequestDto;
import com.eyeeshot.study.type.service.TypeService;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "type")
public class TypeController {

    final private TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @RequestMapping(value = "", method = {RequestMethod.GET})
    public ResponseEntity<String> listType(ListTypeRequestDto listTypeRequestDto) {
        ResponseModel responseModel = new ResponseModel();
        Object result = null;

        Map<String, Object> listTypeResponse = typeService.listTypes(listTypeRequestDto);

        result = listTypeResponse;

        responseModel.setData(result);

        return responseModel.toResponse();
    }

    @RequestMapping(value = "/{name}", method = {RequestMethod.GET})
    public ResponseEntity<String> type(@PathVariable("name") @NotBlank String name) {
        ResponseModel responseModel = new ResponseModel();
        Object result = null;

        Map<String, Object> listTypeResponse = typeService.type(name);

        if (listTypeResponse != null) {
            result = listTypeResponse;
        }

        responseModel.setData(result);

        return responseModel.toResponse();
    }

    @RequestMapping(value = "", method = {RequestMethod.POST})
    public ResponseEntity<String> createType(@RequestBody CreateTypeRequestDto createTypeRequestDto) {
        ResponseModel responseModel = new ResponseModel();
        Object result = null;

        Map<String, Object> createTypeResponse = typeService.createType(createTypeRequestDto);

        responseModel.setData(createTypeResponse);

        return responseModel.toResponse();
    }

    @PatchMapping(value = "/type/{name}")
    @ResponseStatus(value= HttpStatus.OK)
    public ResponseEntity<String> deprecateType(@PathVariable("name") @NotBlank String name, @RequestBody DeprecateTypeRequestDto deprecateTypeRequestDto) {
        ResponseModel responseModel = new ResponseModel();

        Map<String, Object> deprecateType = typeService.deprecateType(name,deprecateTypeRequestDto);

        responseModel.setData(deprecateType);

        return responseModel.toResponse();
    }

}
