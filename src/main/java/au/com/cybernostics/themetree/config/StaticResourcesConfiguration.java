package au.com.cybernostics.themetree.config;

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
import au.com.cybernostics.themetree.asset.StaticResourceRootCollection;
import static java.util.Arrays.asList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * StaticResourcesConfiguration class.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
@Configuration
public class StaticResourcesConfiguration {

    @Value("${static.resource.prefixes:css,js,img,font}")
    String[] resourcePrefixes;

    @Value("${static.resource.locations:classpath:/templates/,classpath:/static/,classpath:/webroot/}")
    String[] resourceLocations;

    /**
     * <p>
     * resourceRootCollection.</p>
     *
     * @return a
     * {@link au.com.cybernostics.themetree.asset.StaticResourceRootCollection}
     * object.
     */
    @Bean
    @ConditionalOnMissingBean(StaticResourceRootCollection.class)
    public StaticResourceRootCollection resourceRootCollection() {
        return new StaticResourceRootCollection(asList(resourcePrefixes), asList(resourceLocations));
    }
}
