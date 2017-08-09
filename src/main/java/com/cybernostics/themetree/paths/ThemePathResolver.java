package com.cybernostics.themetree.paths;

/*
 * #%L
 * theme-tree
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import com.cybernostics.themetree.theme.resolvers.CascadedThemeResolver;
import static java.util.Arrays.asList;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author jason
 */
public class ThemePathResolver
{

    private CascadedThemeResolver resolver;

    @Autowired
    public void setResolver(CascadedThemeResolver resolver)
    {
        this.resolver = resolver;
    }

    public Stream<String> themedPathFor(String request, HttpServletRequest httprequest)
    {
        if (request.startsWith("theme"))
        {
            // are we already asking for a themed resource?
            return asList(request).stream();
        }
        return resolver.getCurrentThemes(httprequest)
                .map(t -> String.format("theme/%s%s", t, withLeadingSlash(request)));
    }

    /**
     * Gets the themed path for the current HttpServletRequest in
     * ThreadLocalStorage. Must be called in the context of a Servlet request
     *
     * @param request - a resource path to prepend with the theme
     * @return String stream of strings like :"/theme/sometheme/yourresource"
     * where sometheme is the name of the current applied theme
     */
    public Stream<String> themedPathFor(String request)
    {
        HttpServletRequest httprequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return themedPathFor(request, httprequest);

    }

    private static String withLeadingSlash(String request)
    {
        if (request.length() > 0 && request.charAt(0) == '/')
        {
            return request;
        }

        return "/" + request;
    }

}
