package org.atomic.http_client_pooling.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.atomic.http_client_pooling.model.SmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

  @Autowired
  private ObjectMapper mapper;

  public ResponseEntity<SmsResponse> getAllSms() throws Exception {
    try(CloseableHttpClient httpClient = HttpClients.createDefault()){
      HttpGet httpGet = new HttpGet("https://api.versionvaulthub.com/sms/getAll");
      httpGet.setHeader("Content-Type", "application/json");
      CloseableHttpResponse response = httpClient.execute(httpGet);
      return ResponseEntity.ok(mapper.readValue(response.getEntity().getContent(), SmsResponse.class));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
