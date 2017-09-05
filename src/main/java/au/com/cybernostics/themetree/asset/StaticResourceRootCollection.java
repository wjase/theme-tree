package au.com.cybernostics.themetree.asset;

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
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import java.util.List;
import java.util.TreeSet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceResolver;

/**
 * Encapsulates a root path to a static resource type. eg. css, js, img, font
 * etc
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class StaticResourceRootCollection extends TreeSet<String>
{

    private String[] resourceLocations = {};
    
    /**
     * <p>Constructor for StaticResourceRootCollection.</p>
     *
     * @param rootPaths a {@link java.lang.String} object.
     */
    public StaticResourceRootCollection(List<String> rootPaths,List<String> locations)
    {
        addAll(rootPaths);
        resourceLocations = locations.toArray(resourceLocations);
    }

    /**
     * <p>register.</p>
     *
     * @param registry a {@link org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry} object.
     * @param resolver a {@link org.springframework.web.servlet.resource.ResourceResolver} object.
     */
    public void register(ResourceHandlerRegistry registry, ResourceResolver resolver)
    {
        forEach(it -> mapResourceHandler(it, registry, resolver));
    }

    private void mapResourceHandler(String type, ResourceHandlerRegistry registry, ResourceResolver resolver)
    {
        registry.addResourceHandler("/" + type + "*/**")
                .addResourceLocations(resourceLocations)
                .resourceChain(true)
                .addResolver(resolver);

    }

}
