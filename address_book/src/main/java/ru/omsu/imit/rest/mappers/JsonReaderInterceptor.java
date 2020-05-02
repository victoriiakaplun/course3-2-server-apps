package ru.omsu.imit.rest.mappers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.*;

@Provider
public class JsonReaderInterceptor implements ReaderInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonReaderInterceptor.class);

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        InputStream originalStream = context.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(originalStream, baos);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
        String json = reader.readLine();
        LOGGER.debug("Json input " + json);
        context.setInputStream(new ByteArrayInputStream(baos.toByteArray()));
        reader.close();
        return context.proceed();
    }
}
