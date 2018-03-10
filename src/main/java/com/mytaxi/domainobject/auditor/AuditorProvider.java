package com.mytaxi.domainobject.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;


@Component
public class AuditorProvider implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        // Ideally this should be reference Of Some User Object.
        // Currently set to String userName as there is no User Class
        return "DUMMY_USER";
    }
}
