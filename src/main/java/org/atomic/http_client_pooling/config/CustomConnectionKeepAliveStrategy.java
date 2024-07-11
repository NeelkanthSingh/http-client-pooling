package org.atomic.http_client_pooling.config;

import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.HttpResponse;
import org.apache.http.HeaderElement;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;

public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

  private final long defaultKeepAliveDuration;

  public CustomConnectionKeepAliveStrategy(long defaultKeepAliveDuration) {
    this.defaultKeepAliveDuration = defaultKeepAliveDuration;
  }

  @Override
  public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
    BasicHeaderElementIterator it = new BasicHeaderElementIterator(
        response.headerIterator(HTTP.CONN_KEEP_ALIVE));

    while (it.hasNext()) {
      HeaderElement he = it.nextElement();
      String param = he.getName();
      String value = he.getValue();
      if (value != null && param.equalsIgnoreCase("timeout")) {
        try {
          return Long.parseLong(value) * 1000;
        } catch (NumberFormatException ignore) {
        }
      }
    }

    return defaultKeepAliveDuration;
  }
}
