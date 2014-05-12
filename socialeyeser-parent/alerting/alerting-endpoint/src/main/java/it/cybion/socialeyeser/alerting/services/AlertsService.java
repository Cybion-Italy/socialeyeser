package it.cybion.socialeyeser.alerting.services;

import it.cybion.socialeyeser.alerting.services.dto.AlertDTO;

import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface AlertsService {

    List<AlertDTO> list(int page, int perPage);
}
