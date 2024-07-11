package org.atomic.http_client_pooling.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
    poolingConnectionManager.setMaxTotal(50);
    poolingConnectionManager.setDefaultMaxPerRoute(40);
    return poolingConnectionManager;
  }

  @Bean
  public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingConnectionManager) {
    return HttpClients.custom()
        .setConnectionManager(poolingConnectionManager)
        .build();
  }
}
