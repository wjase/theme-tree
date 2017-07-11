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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.core.Ordered;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

/**
 *
 * @author jason
 */
public class ThemeTemplateResolver implements ITemplateResolver
{

    private ITemplateResolver delegate;
    private final ThemePathResolver pathResolver;
    private static final Logger logger = Logger.getLogger(ThemeTemplateResolver.class.getName());

    public ThemeTemplateResolver(ITemplateResolver delegate, ThemePathResolver pathResolver)
    {
        this.delegate = delegate;
        this.pathResolver = pathResolver;
    }

    @Override
    public String getName()
    {
        return "themed Resolver";
    }

    @Override
    public Integer getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public TemplateResolution resolveTemplate(IEngineConfiguration iec, String string, String request, Map<String, Object> map)
    {
        final Stream<String> themedPathFor = pathResolver.themedPathFor(request);
        return themedPathFor
                .peek(p ->
                {
                    logger.log(Level.FINE, "ThemeTemplateResolver try theme: " + p);
                })
                .map(path -> delegate.resolveTemplate(iec, string, path, map))
                .filter(o -> o != null)
                .findFirst()
                .orElse(delegate.resolveTemplate(iec, string, request, map));
    }

}
