package org.atomic.http_client_pooling.model;

import lombok.Data;

@Data
public class SmsBody {

  private String _id;
  private String phone_number;
  private String message;
  private String __v;
}
