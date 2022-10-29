package pe.client.custom.app.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pe.client.custom.app.domain.ClientInformation;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.properties")
public class ClientsDetail {
    private List<ClientInformation> clientInformationList;
}
