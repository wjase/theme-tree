package com.cybernostics.themetree.resource.resolvers;

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
import com.cybernostics.themetree.paths.ThemePathResolver;
import java.util.Locale;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/*
 * Copyright (c) 2016 Cybernostics Pty Ltd.
 * All rights reserved.
 */
public class ThemeViewResolver implements Ordered, ViewResolver
{

    @Autowired
    private ThemePathResolver themePathResolver;

    private final ThymeleafViewResolver thymeleafViewResolver;
    private TemplateExistenceChecker templateExistenceChecker;

    public ThemeViewResolver(
            ThymeleafViewResolver thymeleafViewResolver,
            TemplateExistenceChecker existenceChecker,
            ThemePathResolver themePathResolver)
    {
        this.thymeleafViewResolver = thymeleafViewResolver;
        this.templateExistenceChecker = existenceChecker;
        this.themePathResolver = themePathResolver;
    }

    public int getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE - 5;
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception
    {
        Stream<String> themedViewNames = themePathResolver.themedPathFor(viewName);
        return themedViewNames
                .filter(path -> templateExistenceChecker.templateExists(path))
                .map(path -> resolve(thymeleafViewResolver, path, locale))
                .filter(v -> v != null)
                .findFirst()
                .orElse(resolve(thymeleafViewResolver, viewName, locale));
    }

    public static View resolve(ThymeleafViewResolver resolver, String viewName, Locale locale)
    {
        try
        {
            return resolver.resolveViewName(viewName, locale);
        } catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
