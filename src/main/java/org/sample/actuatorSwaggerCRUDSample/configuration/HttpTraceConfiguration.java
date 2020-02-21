package org.sample.actuatorSwaggerCRUDSample.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class HttpTraceConfiguration implements HttpTraceRepository {

    Logger logger = LoggerFactory.getLogger("analytics");

    @Override
    public List<HttpTrace> findAll() {

        return null;
    }

    @Override
    public void add(HttpTrace trace) {

        System.out.println("KEEEEEEEEEEEEEEEEEEEK");
        logger.debug("KEEEEEEEEEEEEEEEEEEEK");
    }
}
