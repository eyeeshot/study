package com.eyeeshot.study.context;

import java.net.URI;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.Credentials;
import software.amazon.awssdk.services.sts.model.GetSessionTokenRequest;
import software.amazon.awssdk.services.sts.model.GetSessionTokenResponse;

@Log4j2
@Component
public class AwsContext {

  @Value("${config.aws.region}")
  String region;
  @Value("${config.aws.stsEndpoint}")
  String stsEndpoint;

  AwsCredentials credentials = null;

  public AwsContext() {
    try {

//      this.credentials = EnvironmentVariableCredentialsProvider.create().resolveCredentials();
      this.credentials = DefaultCredentialsProvider.builder().build().resolveCredentials();

    } catch (Exception e) {
      log.error("Credentials Error : ", e);
    }
  }

  /**
   * STS token 생성
   * @param durationSecond expire seconds (900 ~ 129600) 15 min ~ 36 hours,
   *                       aws default 43200 (12 hours)
   * @return Credentials
   */
  public Credentials requestStsCredentials(Integer durationSecond) {

    StsClient stsClient = StsClient.builder()
        .region(Region.of(this.region))
        .endpointOverride(URI.create(this.stsEndpoint))
        .credentialsProvider(StaticCredentialsProvider.create(this.credentials))
        .build();

    GetSessionTokenResponse sessionTokenResponse = stsClient.getSessionToken(
        GetSessionTokenRequest.builder().durationSeconds(durationSecond).build()
    );

    log.debug("access_key : {}", sessionTokenResponse.credentials().accessKeyId());
    log.debug("secret_key : {}", sessionTokenResponse.credentials().secretAccessKey());
    log.debug("session_key : {}", sessionTokenResponse.credentials().sessionToken());

    return sessionTokenResponse.credentials();

  }

}