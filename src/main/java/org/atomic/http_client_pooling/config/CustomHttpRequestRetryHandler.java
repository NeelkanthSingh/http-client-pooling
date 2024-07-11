package org.atomic.http_client_pooling.config;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.net.UnknownHostException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import java.net.SocketTimeoutException;
import org.apache.http.client.ClientProtocolException;

public class CustomHttpRequestRetryHandler implements HttpRequestRetryHandler {

  private final int maxRetries;
  private final long initialInterval;

  public CustomHttpRequestRetryHandler(int maxRetries, long initialInterval) {
    this.maxRetries = maxRetries;
    this.initialInterval = initialInterval;
  }

  @Override
  public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
    if (executionCount >= maxRetries) {
      return false;
    }

    if (exception instanceof NoHttpResponseException ||
        exception instanceof SocketTimeoutException ||
        exception instanceof ConnectTimeoutException ||
        exception instanceof UnknownHostException ||
        exception instanceof ClientProtocolException) {
      sleep(executionCount);
      return true;
    }

    return false;
  }

  private void sleep(int executionCount) {
    try {
      long interval = initialInterval * (long) Math.pow(2, executionCount - 1);
      Thread.sleep(interval);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
