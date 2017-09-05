package au.com.cybernostics.themetree.theme.resolvers;

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

import java.util.Arrays;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ThemeResolver;

/**
 * Adapter for apps using 'classic' Spring theme resolvers which only know about
 * a single active theme as opposed to a cascaded set.
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class WrappedSpringThemeResolver implements CascadedThemeResolver
{

    /**
     * The classic theme resolver
     */
    private ThemeResolver resolver;

    /**
     * <p>Constructor for WrappedSpringThemeResolver.</p>
     *
     * @param resolver a {@link org.springframework.web.servlet.ThemeResolver} object.
     */
    public WrappedSpringThemeResolver(ThemeResolver resolver)
    {
        this.resolver = resolver;
    }

    /**
     * <p>setDelegate.</p>
     *
     * @param delegate a {@link org.springframework.web.servlet.ThemeResolver} object.
     */
    public void setDelegate(ThemeResolver delegate)
    {
        this.resolver = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public Stream<String> getCurrentThemes(HttpServletRequest request)
    {
        return Arrays.asList(resolver.resolveThemeName(request)).stream();
    }

    /** {@inheritDoc} */
    @Override
    public void setDefault(String name)
    {
        throw new UnsupportedOperationException("Using classic ThemeResolver.");
    }

}
