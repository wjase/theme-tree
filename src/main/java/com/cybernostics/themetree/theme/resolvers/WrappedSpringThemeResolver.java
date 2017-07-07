package com.cybernostics.themetree.theme.resolvers;

/*
 * #%L
 * spring-thymeleaf-themetree
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

import static java.util.Arrays.asList;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ThemeResolver;

/**
 * Adapter for apps using 'classic' Spring theme resolvers which only know about
 * a single active theme as opposed to a cascaded set.
 *
 * @author jason
 */
public class WrappedSpringThemeResolver implements CascadedThemeResolver
{

    /**
     * The classic theme resolver
     */
    private ThemeResolver resolver;

    public WrappedSpringThemeResolver(ThemeResolver resolver)
    {
        this.resolver = resolver;
    }

    public void setDelegate(ThemeResolver delegate)
    {
        this.resolver = delegate;
    }

    @Override
    public Stream<String> getCurrentThemes(HttpServletRequest request)
    {
        return asList(resolver.resolveThemeName(request)).stream();
    }

    @Override
    public void setDefault(String name)
    {
        throw new UnsupportedOperationException("Using classic ThemeResolver.");
    }

}
