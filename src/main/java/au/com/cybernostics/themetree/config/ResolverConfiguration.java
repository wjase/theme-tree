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
import au.com.cybernostics.themetree.paths.ThemePathResolver;
import au.com.cybernostics.themetree.resource.resolvers.ThemeResourceResolver;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

/**
 * <p>
 * ResolverConfiguration class.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
@Configuration
public class ResolverConfiguration {

    @Autowired
    private ThemePathResolver themePathResolver;
    
    @Value("${themetree.version.assets:}")
    private String versionedAssets;
    

    /**
     * <p>
     * themedResourceResolver.</p>
     *
     * @return a
     * {@link au.com.cybernostics.themetree.resource.resolvers.ThemeResourceResolver}
     * object.
     */
    @Bean
    @Order(Integer.MIN_VALUE)
    public ThemeResourceResolver themedResourceResolver() {
        final ThemeResourceResolver themeResourceResolver = new ThemeResourceResolver(themePathResolver);
        for(String path:versionedAssets.split(",")){
            themeResourceResolver.addResourceVersionStrategy(path, new ContentVersionStrategy());    
        }
        
        return themeResourceResolver;
    }
}
