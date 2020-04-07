package com.eyeeshot.study.type.controller;

import com.eyeeshot.study.common.ResponseModel;
import com.eyeeshot.study.type.domain.request.CreateTypeRequestDto;
import com.eyeeshot.study.type.domain.request.ListTypeRequestDto;
import com.eyeeshot.study.type.service.TypeService;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "type")
public class TypeController {

    final private TypeService typeService;
    private Object result = null;
    ResponseModel responseModel = new ResponseModel();

    @Autowired
    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @RequestMapping(value = "", method = {RequestMethod.GET})
    public ResponseEntity<String> listType(ListTypeRequestDto listTypeRequestDto) {
        Map<String, Object> listTypeResponse = typeService.listTypes(listTypeRequestDto);

        result = listTypeResponse;

        responseModel.setData(result);

        return responseModel.toResponse();
    }

    @RequestMapping(value = "/{name}", method = {RequestMethod.GET})
    public ResponseEntity<String> type(@PathVariable("name") @NotBlank String name) {
        Map<String, Object> listTypeResponse = typeService.type(name);

        if (listTypeResponse != null) {
            result = listTypeResponse;
        }

        responseModel.setData(result);

        return responseModel.toResponse();
    }

    @RequestMapping(value = "", method = {RequestMethod.POST})
    public ResponseEntity<String> createType(@RequestBody CreateTypeRequestDto createTypeRequestDto) {
        System.out.printf(String.valueOf(createTypeRequestDto));

        Map<String, Object> createTypeResponse = typeService.createType(createTypeRequestDto);

        result = createTypeResponse;

        responseModel.setData(result);

        return responseModel.toResponse();
    }

}
