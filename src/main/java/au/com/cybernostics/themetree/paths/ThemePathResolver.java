package au.com.cybernostics.themetree.paths;

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
import au.com.cybernostics.themetree.theme.resolvers.CascadedThemeResolver;

import static java.util.Arrays.asList;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>ThemePathResolver class.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class ThemePathResolver
{

    private CascadedThemeResolver resolver;

    /**
     * <p>Setter for the field <code>resolver</code>.</p>
     *
     * @param resolver a {@link au.com.cybernostics.themetree.theme.resolvers.CascadedThemeResolver} object.
     */
    @Autowired
    public void setResolver(CascadedThemeResolver resolver)
    {
        this.resolver = resolver;
    }

    /**
     * <p>themedPathFor.</p>
     *
     * @param request a {@link java.lang.String} object.
     * @param httprequest a {@link javax.servlet.http.HttpServletRequest} object.
     * @return a {@link java.util.stream.Stream} object.
     */
    public Stream<String> themedPathsFor(String request, HttpServletRequest httprequest)
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
        return themedPathsFor(request, httprequest);

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
