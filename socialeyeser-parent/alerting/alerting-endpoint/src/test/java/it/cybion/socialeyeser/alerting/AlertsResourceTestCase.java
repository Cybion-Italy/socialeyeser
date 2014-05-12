package it.cybion.socialeyeser.alerting;

import it.cybion.socialeyeser.alerting.guice.TestServiceConfig;
import it.cybion.socialeyeser.alerting.utils.JettyServer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Arrays;

public class AlertsResourceTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertsResourceTestCase.class);

    private JettyServer jettyServer;

    @BeforeClass
    public void setUp() throws Exception {

        this.jettyServer = new JettyServer(9999);

    }

    @BeforeMethod
    public void setUpLocalJettyServer() throws Exception {

        this.jettyServer.addEventListener(new TestServiceConfig());
        this.jettyServer.start();
    }

    @AfterMethod
    public void tearDownJetty() throws Exception {

        this.jettyServer.stop();
    }

//    @Test
//    public void shouldGetAlertById() throws CybionHttpException {
//
//        final String anAlert = this.jettyServer.baseUri + "alerts/1234";
//
//        ExternalStringResponse stringResponse = null;
//
//        final Map<String, String> requestHeaderMap = Maps.newHashMap();
//        requestHeaderMap.put("Accept", MediaType.APPLICATION_JSON);
//        //TODO use a plain http-components client
//        stringResponse = CybionHttpClient.performGet(anAlert, requestHeaderMap);
//
//        LOGGER.info("response body: " + stringResponse.getObject());
//        assertEquals(ResponseStatus.OK, stringResponse.getStatus(),
//                "Unexpected result: " + stringResponse.getMessage());
//    }

    @Test
    public void shouldGetAlertsById() {

        final String anAlertPath = this.jettyServer.baseUri + "alerts/123";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(anAlertPath);
            httpget.addHeader("Accept", MediaType.APPLICATION_JSON);

            LOGGER.debug("Executing request " + httpget.getRequestLine());
            LOGGER.debug(Arrays.toString(httpget.getAllHeaders()));

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);

            LOGGER.info("----------------------------------------");
            LOGGER.info(responseBody);
            final ObjectMapper objectMapper = new ObjectMapper();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                //TODO silently close
                e.printStackTrace();
            }
        }



//        LOGGER.info("response body: " + stringResponse.getObject());
//        assertEquals(ResponseStatus.OK, stringResponse.getStatus(),
//                "Unexpected result: " + stringResponse.getMessage());
    }

    @Test
    public void shouldGetAlertsList() {

        final String anAlertPath = this.jettyServer.baseUri + "alerts?page=3&per_page=100";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(anAlertPath);
            httpget.addHeader("Accept", MediaType.APPLICATION_JSON);

            LOGGER.debug("Executing request " + httpget.getRequestLine());
            LOGGER.debug(Arrays.toString(httpget.getAllHeaders()));

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);

            LOGGER.info("----------------------------------------");
            LOGGER.info(responseBody);
            final ObjectMapper objectMapper = new ObjectMapper();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                //TODO silently close
                e.printStackTrace();
            }
        }



        //        LOGGER.info("response body: " + stringResponse.getObject());
        //        assertEquals(ResponseStatus.OK, stringResponse.getStatus(),
        //                "Unexpected result: " + stringResponse.getMessage());
    }
}
