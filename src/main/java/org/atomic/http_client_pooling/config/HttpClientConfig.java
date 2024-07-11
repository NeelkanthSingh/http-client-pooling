package org.atomic.http_client_pooling.config;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

  private static final Integer CONNECTION_REQUEST_TIMEOUT = 30000;
  private static final Integer CONNECT_TIMEOUT = 30000;
  private static final Integer SOCKET_TIMEOUT = 30000;
  private static final Integer MAX_TOTAL_CONNECTIONS = 30;
  private static final Integer MAX_CONNECTIONS_PER_ROUTE = 25;
  private static final Integer VALIDATE_AFTER_INACTIVITY = 1000;
  private static final Integer DEFAULT_KEEP_ALIVE_DURATION = 5000;
  private static final Integer MAX_RETRIES = 3;
  private static final Integer RETRY_INTERVAL = 2000;

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
    poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
    poolingConnectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);
    poolingConnectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
    return poolingConnectionManager;
  }

  @Bean
  public RequestConfig requestConfig() {
    return RequestConfig.custom()
        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
        .setConnectTimeout(CONNECT_TIMEOUT)
        .setSocketTimeout(SOCKET_TIMEOUT)
        .build();
  }

  @Bean
  public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingConnectionManager) {
    ConnectionKeepAliveStrategy keepAliveStrategy = new CustomConnectionKeepAliveStrategy(DEFAULT_KEEP_ALIVE_DURATION);
    HttpRequestRetryHandler retryHandler = new CustomHttpRequestRetryHandler(MAX_RETRIES, RETRY_INTERVAL);

    return HttpClients.custom()
        .setConnectionManager(poolingConnectionManager)
        .setKeepAliveStrategy(keepAliveStrategy)
        .setRetryHandler(retryHandler)
        .build();
  }
}
