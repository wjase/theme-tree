package com.cybernostics.themetree.resource.resolvers;

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
import com.cybernostics.themetree.paths.ThemePathResolver;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

/**
 *
 * @author jason
 */
public class ThemeResourceResolver extends PathResourceResolver
{

    private final ThemePathResolver themePathResolver;

    public ThemeResourceResolver(ThemePathResolver themeResolver)
    {
        this.themePathResolver = themeResolver;
    }

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain)
    {
        return themePathResolver.themedPathFor(requestPath, request)
                .map(path -> super.resolveResourceInternal(request, path, locations, chain))
                .filter(o -> o != null)
                .findFirst().orElse(null);
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations, ResourceResolverChain chain)
    {
        return super.resolveUrlPathInternal(resourceUrlPath, locations, chain);
    }

    @Override
    protected boolean checkResource(Resource resource, Resource location) throws IOException
    {
        return super.checkResource(resource, location);
    }

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException
    {
        return super.getResource(resourcePath, location);
    }

    @Override
    public Resource[] getAllowedLocations()
    {
        return super.getAllowedLocations();
    }

    @Override
    public void setAllowedLocations(Resource... locations)
    {
        super.setAllowedLocations(locations);
    }

}
