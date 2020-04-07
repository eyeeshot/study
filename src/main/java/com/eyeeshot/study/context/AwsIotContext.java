package com.eyeeshot.study.context;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.iot.IotClient;
import software.amazon.awssdk.services.sts.model.Credentials;

@Log4j2
@Component
public class AwsIotContext {

  private final AwsContext awsContext;

  @Getter
  IotClient iotClient;

  // sts credentials
  private Credentials credentials = null;

  public AwsIotContext(AwsContext awsContext) {

    this.awsContext = awsContext;
    this.iotClient = IotClient.builder().credentialsProvider(new AwsCredentialsProvider() {
      @Override
      public AwsCredentials resolveCredentials() {
        return DefaultCredentialsProvider.builder().build().resolveCredentials();
//        return AwsSessionCredentials.create(getCredentials().accessKeyId(), getCredentials().secretAccessKey(), getCredentials().sessionToken());
      }
    }).build();

  }

  private Credentials getCredentials() {
    if (credentials == null) {
      credentials = awsContext.requestStsCredentials(3600);
    }

    return credentials;
  }
}
