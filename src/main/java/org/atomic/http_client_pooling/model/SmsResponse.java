package org.atomic.http_client_pooling.model;

import java.util.List;
import lombok.Data;

@Data
public class SmsResponse {

  private String message;
  private List<SmsBody> data;
}
