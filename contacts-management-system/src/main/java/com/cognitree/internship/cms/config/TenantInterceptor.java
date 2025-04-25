package com.cognitree.internship.cms.config;

import com.cognitree.internship.cms.repositories.TenantRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TenantInterceptor.class);
    private static final String TENANT_HEADER = "X-Tenant-ID";

    private TenantContext tenantContext;

    private TenantRepository tenantRepository;

    @Autowired
    public void setTenantContext(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Autowired
    public void setTenantRepository(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();
        // Skip tenant validation for admin paths and error path
        if (requestPath.startsWith("/admin/") || requestPath.equals("/error")) {
            logger.info("Path excluded from tenant validation: {}", requestPath);
            return true;
        }
        String tenantId = request.getHeader(TENANT_HEADER);
        if (tenantId == null || tenantId.isEmpty()) {
            logger.error("No tenant ID provided in request header for path: {}", requestPath);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Tenant ID is required (X-Tenant-ID header)");
            return false;
        }
        // Validate that the tenant ID exists in the system
        if (!tenantRepository.existsById(tenantId)) {
            logger.error("Invalid tenant ID provided: {}", tenantId);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid tenant ID");
            return false;
        }
        logger.debug("Setting tenant context to: {} for path: {}", tenantId, requestPath);
        tenantContext.setTenantId(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.debug("Clearing tenant context");
        tenantContext.clear();
    }
}