package ua.nure.library.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * Request logging to console filter.
 */
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final String REQUEST_LOG_MESSAGE = "Got %s request to: %s %nParameters: %s %nUser: %s";
    private static final String ENDPOINTS_REGEXP = ".*/(users).*";

    /**
     * @param request request
     * @param message message
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        if (request.getRequestURI().matches(ENDPOINTS_REGEXP)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            List<String> parameters = parameterMap.entrySet().stream().map(this::formatParameterPair).collect(toList());
            LOG.info(format(REQUEST_LOG_MESSAGE, request.getMethod(), request.getRequestURL(), parameters, request.getRemoteUser()));
        }
    }

    /**
     * @param request request
     * @param message message
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        /*NOP*/
    }

    private String formatParameterPair(Map.Entry<String, String[]> entry) {
        String[] values = entry.getValue();
        StringBuilder parameterValues = new StringBuilder();
        for (String value : values) {
            parameterValues.append(value);
        }
        return String.valueOf(entry.getKey() + " : " + parameterValues.toString() + " ");
    }
}
