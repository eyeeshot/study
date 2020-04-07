package com.eyeeshot.study.type.service;

import com.eyeeshot.study.context.AwsIotContext;
import com.eyeeshot.study.type.domain.request.CreateTypeRequestDto;
import com.eyeeshot.study.type.domain.request.ListTypeRequestDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.iot.model.CreateThingTypeRequest;
import software.amazon.awssdk.services.iot.model.CreateThingTypeResponse;
import software.amazon.awssdk.services.iot.model.DescribeThingTypeRequest;
import software.amazon.awssdk.services.iot.model.DescribeThingTypeResponse;
import software.amazon.awssdk.services.iot.model.ListThingTypesRequest;
import software.amazon.awssdk.services.iot.model.ListThingTypesResponse;
import software.amazon.awssdk.services.iot.model.Tag;

@Component
public class TypeService {

    private final AwsIotContext awsIotContext;

    private final Map<String, Object> result = new HashMap<String, Object>();

    @Autowired
    public TypeService (AwsIotContext awsIotContext) {
        this.awsIotContext = awsIotContext;
    }

    public Map<String, Object> listTypes(ListTypeRequestDto listTypeRequestDto) {
        ListThingTypesRequest listThingTypesRequest = ListThingTypesRequest.builder()
            .thingTypeName(listTypeRequestDto.getName())
            .maxResults(listTypeRequestDto.getMaxResults())
            .nextToken(listTypeRequestDto.getNextToken())
            .build();

        ListThingTypesResponse listThingTypesResponse = awsIotContext.getIotClient().listThingTypes(listThingTypesRequest);

        result.put("type", listThingTypesResponse.thingTypes());

        return result;
    }

    public Map<String, Object> type(String typeName) {

        DescribeThingTypeRequest describeThingRequest = DescribeThingTypeRequest.builder().thingTypeName(typeName).build();
        DescribeThingTypeResponse describeThingTypeResponse = awsIotContext.getIotClient().describeThingType(describeThingRequest);


        result.put("id", describeThingTypeResponse.thingTypeId());
        result.put("name", describeThingTypeResponse.thingTypeName());
        result.put("arn", describeThingTypeResponse.thingTypeArn());
        result.put("properties", describeThingTypeResponse.thingTypeProperties());
        result.put("metadata", describeThingTypeResponse.responseMetadata());

        return result;
    }

    public Map<String, Object> createType(CreateTypeRequestDto createTypeRequestDto) {
        CreateThingTypeRequest createThingTypeRequest = CreateThingTypeRequest.builder()
            .thingTypeName(createTypeRequestDto.getName())
//            .thingTypeProperties((Consumer<Builder>) createTypeRequestDto.getTypeProperties())
            .tags((Consumer<Tag.Builder>) createTypeRequestDto.getTags())
            .build();

        CreateThingTypeResponse createThingTypeResponse = awsIotContext.getIotClient().createThingType(createThingTypeRequest);

        return result;
    }
}

