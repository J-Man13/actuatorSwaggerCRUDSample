package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HttpTraceConfiguration implements HttpTraceRepository {

    @Override
    public List<HttpTrace> findAll() {
        return null;
    }

    @Override
    public void add(HttpTrace trace) {

    }
}
