/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2014 ForgeRock AS.
 */

package org.forgerock.openig.ext.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Sanitize ill-formed query parameters.
 * Right now, it only changes {@literal |} (pipe) chars inside raw query string
 * into properly URL encoded {@literal %7C}.
 * <p>
 * This has to be declared inside of the {@literal WEB-INF/web.xml} descriptor:
 * <pre>{@code
 *       <filter>
 *         <filter-name>sanitizer</filter-name>
 *         <filter-class>org.forgerock.openig.ext.servlet.SanitizeQueryStringFilter</filter-class>
 *       </filter>
 *       <filter-mapping>
 *         <filter-name>sanitizer</filter-name>
 *         <servlet-name>openig-servlet</servlet-name>
 *       </filter-mapping>
 *     }
 * </pre>
 */
public class SanitizeQueryStringFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new SanitizedQueryStringHttpServletRequest((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {

    }


    private static class SanitizedQueryStringHttpServletRequest extends HttpServletRequestWrapper {
        public SanitizedQueryStringHttpServletRequest(final HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getQueryString() {
            return sanitizeQuery(super.getQueryString());
        }
        
        private static String sanitizeQuery(final String rawQuery) {
            
            // Protect against NPE
            if (rawQuery == null) {
                return null;
            }

            // Update the raw query
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rawQuery.length(); i++) {
                char c = rawQuery.charAt(i);
                if (c == '|') {
                    sb.append("%7C");
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }
}