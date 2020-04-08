package com.eyeeshot.study.type.service;

import com.eyeeshot.study.context.AwsIotContext;
import com.eyeeshot.study.type.domain.request.CreateTypeRequestDto;
import com.eyeeshot.study.type.domain.request.DeprecateTypeRequestDto;
import com.eyeeshot.study.type.domain.request.ListTypeRequestDto;
import com.eyeeshot.study.type.domain.request.TagDto;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import software.amazon.awssdk.services.iot.model.CreateThingTypeRequest;
import software.amazon.awssdk.services.iot.model.CreateThingTypeResponse;
import software.amazon.awssdk.services.iot.model.DeprecateThingTypeRequest;
import software.amazon.awssdk.services.iot.model.DeprecateThingTypeResponse;
import software.amazon.awssdk.services.iot.model.DescribeThingTypeRequest;
import software.amazon.awssdk.services.iot.model.DescribeThingTypeResponse;
import software.amazon.awssdk.services.iot.model.ListThingTypesRequest;
import software.amazon.awssdk.services.iot.model.ListThingTypesResponse;
import software.amazon.awssdk.services.iot.model.Tag;
import software.amazon.awssdk.services.iot.model.Tag.Builder;
import software.amazon.awssdk.services.iot.model.ThingTypeProperties;

@Component
public class TypeService {

    private final AwsIotContext awsIotContext;

    @Autowired
    public TypeService (AwsIotContext awsIotContext) {
        this.awsIotContext = awsIotContext;
    }

    public Map<String, Object> listTypes(ListTypeRequestDto listTypeRequestDto) {
        Map<String, Object> result = new HashMap<String, Object>();

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
        Map<String, Object> result = new HashMap<String, Object>();

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

        Map<String, Object> result = new HashMap<String, Object>();

        System.out.println(createTypeRequestDto.getTags());

        CreateThingTypeRequest.Builder createThingTypeRequest = CreateThingTypeRequest.builder()
            .thingTypeName(createTypeRequestDto.getName());

        if (!ObjectUtils.isEmpty(createTypeRequestDto.getTypeProperties())) {
            ThingTypeProperties thingTypeProperties = ThingTypeProperties.builder()
                .searchableAttributes(createTypeRequestDto.getTypeProperties().getSearchableAttributes())
                .thingTypeDescription(createTypeRequestDto.getTypeProperties().getTypeDescription())
                .build();
            createThingTypeRequest.thingTypeProperties(thingTypeProperties);
        }

        if (!ObjectUtils.isEmpty(createTypeRequestDto.getTags())) {
            Builder tag = Tag.builder();
            for (TagDto tagDto : createTypeRequestDto.getTags()
            ) {
                tag.key(tagDto.getKey());
                tag.value(tagDto.getValue());
            }
            createThingTypeRequest.tags(tag.build());
        }

        CreateThingTypeResponse createThingTypeResponse = awsIotContext.getIotClient().createThingType(createThingTypeRequest.build());

        result.put("name",createThingTypeResponse.thingTypeName());
        result.put("id",createThingTypeResponse.thingTypeId());
        result.put("arn",createThingTypeResponse.thingTypeArn());

        return result;
    }

    public Map<String, Object> deprecateType(String name, DeprecateTypeRequestDto deprecateTypeRequestDto) {
        Map<String, Object> result = new HashMap<String, Object>();

        DeprecateThingTypeRequest deprecateThingTypeRequest = DeprecateThingTypeRequest.builder()
            .thingTypeName(name)
            .undoDeprecate(deprecateTypeRequestDto.getStatus())
            .build();

        DeprecateThingTypeResponse deprecateThingTypeResponse = awsIotContext.getIotClient().deprecateThingType(deprecateThingTypeRequest);

        result.put("result",deprecateThingTypeResponse);

        return result;
    }
}

