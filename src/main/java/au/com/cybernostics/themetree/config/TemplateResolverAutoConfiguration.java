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
import au.com.cybernostics.themetree.defaults.DefaultTemplateExistenceChecker;
import au.com.cybernostics.themetree.paths.ThemePathResolver;
import au.com.cybernostics.themetree.resource.resolvers.TemplateExistenceChecker;
import au.com.cybernostics.themetree.resource.resolvers.ThemeTemplateResolver;
import au.com.cybernostics.themetree.theme.persistence.NoThemePersistence;
import au.com.cybernostics.themetree.theme.persistence.ThemePersistence;
import au.com.cybernostics.themetree.theme.persistence.ThemePersistenceInterceptor;
import au.com.cybernostics.themetree.theme.resolvers.CascadedThemeResolver;
import au.com.cybernostics.themetree.theme.resolvers.MultiCandidateThemeResolver;
import au.com.cybernostics.themetree.theme.resolvers.WrappedSpringThemeResolver;
import au.com.cybernostics.themetree.theme.sources.DefaultCandidateThemeSource;
import au.com.cybernostics.themetree.theme.sources.MutableCandidateThemeSource;

import static java.util.Arrays.stream;
import java.util.function.Consumer;
import java.util.logging.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * <p>TemplateResolverAutoConfiguration - Don't invoke directly.</p>
 * Invoked from the META-INF/spring.factories config file 
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
@Configuration
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
@Import(ThemeConfiguration.class)
public class TemplateResolverAutoConfiguration
{

    /** Constant <code>APP_PROPERTIES_THEME_PREFIX="themetree.themes"</code> */
    public static final String APP_PROPERTIES_THEME_PREFIX = "themetree.themes";
    private static final Logger logger = Logger.getLogger(TemplateResolverAutoConfiguration.class.getName());

    /**
     * <p>addResourceHandlersFunction.</p>
     *
     * @param staticResourcesConfg a {@link au.com.cybernostics.themetree.asset.StaticResourceRootCollection} object.
     * @param resolver a {@link org.springframework.web.servlet.resource.ResourceResolver} object.
     * @return a {@link java.util.function.Consumer} object.
     */
    @Bean
    @ConditionalOnMissingBean(name = "addResourceHandlersFunction")
    public Consumer<ResourceHandlerRegistry> addResourceHandlersFunction(StaticResourceRootCollection staticResourcesConfg, ResourceResolver resolver)
    {
        return (registry) ->
        {
            staticResourcesConfg.register(registry, resolver);
        };

    }

    /**
     * <p>candidateThemeSource.</p>
     *
     * @param env a {@link org.springframework.core.env.Environment} object.
     * @param context a {@link org.springframework.context.ApplicationContext} object.
     * @return a {@link au.com.cybernostics.themetree.theme.sources.MutableCandidateThemeSource} object.
     */
    @Bean
    @ConditionalOnMissingBean(MutableCandidateThemeSource.class)
    @ConditionalOnProperty(APP_PROPERTIES_THEME_PREFIX)
    public MutableCandidateThemeSource candidateThemeSource(Environment env, ApplicationContext context)
    {
        return DefaultCandidateThemeSource.fromProperties(env, context);
    }

    /**
     * <p>emptyCandidateThemeSource.</p>
     *
     * @param env a {@link org.springframework.core.env.Environment} object.
     * @param context a {@link org.springframework.context.ApplicationContext} object.
     * @return a {@link au.com.cybernostics.themetree.theme.sources.MutableCandidateThemeSource} object.
     */
    @Bean
    @ConditionalOnMissingBean(MutableCandidateThemeSource.class)
    public MutableCandidateThemeSource emptyCandidateThemeSource(Environment env, ApplicationContext context)
    {
        final DefaultCandidateThemeSource listCandidateThemeSource = new DefaultCandidateThemeSource();
        return listCandidateThemeSource;
    }

    /**
     * <p>themePersistence.</p>
     *
     * @return a {@link au.com.cybernostics.themetree.theme.persistence.ThemePersistence} object.
     */
    @Bean
    @ConditionalOnMissingBean(ThemePersistence.class)
    public ThemePersistence themePersistence()
    {
        return new NoThemePersistence();
    }

    /**
     * <p>interceptor.</p>
     *
     * @param persistence a {@link au.com.cybernostics.themetree.theme.persistence.ThemePersistence} object.
     * @return a {@link au.com.cybernostics.themetree.theme.persistence.ThemePersistenceInterceptor} object.
     */
    @Bean
    @ConditionalOnMissingBean(ThemePersistenceInterceptor.class)
    public ThemePersistenceInterceptor interceptor(ThemePersistence persistence)
    {
        return new ThemePersistenceInterceptor(persistence);
    }

    /**
     * <p>cascadedThemeResolver.</p>
     *
     * @param resolver a {@link org.springframework.web.servlet.ThemeResolver} object.
     * @return a {@link au.com.cybernostics.themetree.theme.resolvers.CascadedThemeResolver} object.
     */
    @Bean
    @ConditionalOnBean(ThemeResolver.class)
    public CascadedThemeResolver cascadedThemeResolver(ThemeResolver resolver)
    {
        return new WrappedSpringThemeResolver(resolver);
    }

    /**
     * <p>cascadedMultiThemeResolver.</p>
     *
     * @param persistence a {@link au.com.cybernostics.themetree.theme.persistence.ThemePersistence} object.
     * @param candidateThemes a {@link au.com.cybernostics.themetree.theme.sources.MutableCandidateThemeSource} object.
     * @return a {@link au.com.cybernostics.themetree.theme.resolvers.CascadedThemeResolver} object.
     */
    @Bean
    @ConditionalOnMissingBean(CascadedThemeResolver.class)
    public CascadedThemeResolver cascadedMultiThemeResolver(ThemePersistence persistence, MutableCandidateThemeSource candidateThemes)
    {
        final MultiCandidateThemeResolver multiCandidateThemeResolver = new MultiCandidateThemeResolver();
        multiCandidateThemeResolver.setPersistence(persistence);
        multiCandidateThemeResolver.setThemeSource(candidateThemes);
        return multiCandidateThemeResolver;
    }

    @Bean
    @ConditionalOnMissingBean(IDialect.class)
    IDialect defaultDialect()
    {
        return new SpringStandardDialect();
    }

    /**
     * <p>existenceChecker.</p>
     *
     * @param thymeleafProperties a {@link org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties} object.
     * @param applicationContext a {@link org.springframework.context.ApplicationContext} object.
     * @return a {@link au.com.cybernostics.themetree.resource.resolvers.TemplateExistenceChecker} object.
     */
    @Bean
    @ConditionalOnMissingBean(TemplateExistenceChecker.class)
    public TemplateExistenceChecker existenceChecker(ThymeleafProperties thymeleafProperties, ApplicationContext applicationContext)
    {
        return new DefaultTemplateExistenceChecker(thymeleafProperties, applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean(SpringTemplateEngine.class)
    SpringTemplateEngine springTemplateEngine(ITemplateResolver templateResolver, ThemePathResolver pathResolver, IDialect[] dialects)
    {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        stream(dialects).forEach(dialect -> springTemplateEngine.setDialect(dialect));
        springTemplateEngine.addTemplateResolver(new ThemeTemplateResolver(templateResolver, pathResolver));
        return springTemplateEngine;
    }

    @Bean
    @ConditionalOnMissingBean(ThemePathResolver.class)
    ThemePathResolver pathResolver()
    {
        return new ThemePathResolver();
    }
    
    @Bean
    @ConditionalOnMissingBean(ResourceUrlEncodingFilter.class)
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter(){
        return new ResourceUrlEncodingFilter();
    }
}
