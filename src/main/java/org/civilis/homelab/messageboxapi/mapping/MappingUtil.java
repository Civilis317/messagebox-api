package org.civilis.homelab.messageboxapi.mapping;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

public class MappingUtil {

    @Named("SanitizeField")
    public String sanitizeField(String value) {
        return StringUtils.strip(value, "%");
    }
}

