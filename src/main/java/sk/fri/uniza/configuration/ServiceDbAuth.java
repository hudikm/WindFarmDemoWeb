
package sk.fri.uniza.configuration;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "clientId",
        "secret",
        "path",
        "serviceConnectors"
})
public class ServiceDbAuth {

    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("path")
    private String path;
    @JsonProperty("serviceConnectors")
    private List<ServiceConnector> serviceConnectors = null;

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("secret")
    public String getSecret() {
        return secret;
    }

    @JsonProperty("secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("serviceConnectors")
    public List<ServiceConnector> getServiceConnectors() {
        return serviceConnectors;
    }

    @JsonProperty("serviceConnectors")
    public void setServiceConnectors(List<ServiceConnector> serviceConnectors) {
        this.serviceConnectors = serviceConnectors;
    }

}

