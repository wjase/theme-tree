package au.com.cybernostics.themetree.resource.resolvers;

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
import au.com.cybernostics.themetree.paths.ThemePathResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.springframework.web.servlet.resource.VersionStrategy;

/**
 * <p>
 * ThemeResourceResolver class.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class ThemeResourceResolver extends PathResourceResolver {

    @Autowired
    private HttpServletRequest request;

    private final ThemePathResolver themePathResolver;
    private Map<String, VersionStrategy> versionStrategies = new TreeMap<>();
    private PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * <p>
     * Constructor for ThemeResourceResolver.</p>
     *
     * @param themeResolver a
     * {@link au.com.cybernostics.themetree.paths.ThemePathResolver} object.
     */
    public ThemeResourceResolver(ThemePathResolver themeResolver) {
        this.themePathResolver = themeResolver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        Optional<VersionStrategy> strategy = strategyForPath(requestPath);

        final String version = strategy
                .map(s -> s.extractVersion(requestPath))
                .filter(v -> isMd5Hash(v))
                .orElse(null);

        String pathWithoutVersion = strategy
                .map(s -> s.removeVersion(requestPath, version))
                .orElse(requestPath);

        return themePathResolver.themedPathsFor(pathWithoutVersion, request)
                .map(path -> super.resolveResourceInternal(request, path, locations, chain))
                .filter(o -> o != null)
                .findFirst()
                .orElse(null);
    }

    private Optional<VersionStrategy> strategyForPath(String requestPath) {
        final Optional<VersionStrategy> strategy = versionStrategies
                .entrySet()
                .stream()
                .filter(e -> pathMatcher.match(e.getKey(), requestPath))
                .map(e -> e.getValue())
                .findFirst();
        return strategy;
    }

    private static boolean isMd5Hash(String s) {
        return s.matches("[0-9a-zA-Z]{32}");
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return super.resolveUrlPathInternal(resourceUrlPath, locations, chain);
    }

    @Override
    public String resolveUrlPath(String resourceUrlPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        String resoucePath = strategyForPath(resourceUrlPath).map(s -> {
            Resource internalResource = resolveResourceInternal(request, resourceUrlPath, locations, chain);
            String resourceVersion = s.getResourceVersion(internalResource);
            return s.addVersion(resourceUrlPath, resourceVersion);
        }
        ).orElse(resourceUrlPath);

        return resoucePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkResource(Resource resource, Resource location) throws IOException {
        return super.checkResource(resource, location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        return super.getResource(resourcePath, location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource[] getAllowedLocations() {
        return super.getAllowedLocations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllowedLocations(Resource... locations) {
        super.setAllowedLocations(locations);
    }

    public void addResourceVersionStrategy(String pathPattern, VersionStrategy resourceVersionStrategy) {

        versionStrategies.put(pathPattern, resourceVersionStrategy);
    }

}
