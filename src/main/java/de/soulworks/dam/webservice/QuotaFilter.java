package de.soulworks.dam.webservice;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class QuotaFilter implements Filter {
    private static org.apache.log4j.Logger log = Logger.getLogger(QuotaFilter.class);

    public void init(FilterConfig fc) throws ServletException {
    }

    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        // Retrieve auth-data
        // Checks limits
        // Throw exceptions when limits get exceeded
        log.info(sr.toString());
//        System.out.println(sr.toString());
    }

    public void destroy() {
    }

}
