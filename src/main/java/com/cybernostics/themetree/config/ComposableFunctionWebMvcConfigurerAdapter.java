package com.cybernostics.themetree.config;

/*-
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
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * An implementation of {@link WebMvcConfigurer} with functional consumers and
 * suppliers meaning that users don't need to subclass , but instead simply
 * implement the required bean(s) to configure what they want to configure.
 *
 * @author Jason Wraxall
 * @since 4.7
 */
@Configuration
@ConditionalOnMissingBean(WebMvcConfigurerAdapter.class)
public class ComposableFunctionWebMvcConfigurerAdapter implements WebMvcConfigurer
{

    @Autowired(required = false)
    @Qualifier("configurePathMatchFunction")
    private Consumer<PathMatchConfigurer> configurePathMatchFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureContentNegotiationFunction")
    private Consumer<ContentNegotiationConfigurer> configureContentNegotiationFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureAsyncSupportFunction")
    private Consumer<AsyncSupportConfigurer> configureAsyncSupportFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureAsyncSupportFunction")
    private Consumer<DefaultServletHandlerConfigurer> configureDefaultServletHandlingFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addFormattersFunction")
    private Consumer<FormatterRegistry> addFormattersFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addInterceptorsFunction")
    private Consumer<InterceptorRegistry> addInterceptorsFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addResourceHandlersFunction")
    private Consumer<ResourceHandlerRegistry> addResourceHandlersFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addCorsMappingsFunction")
    private Consumer<CorsRegistry> addCorsMappingsFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addViewControllersFunction")
    private Consumer<ViewControllerRegistry> addViewControllersFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("configureViewResolversFunction")
    private Consumer<ViewResolverRegistry> configureViewResolversFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("formatterRegistryConfigurer")
    private Consumer<FormatterRegistry> formatterRegistryConfigurer = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addArgumentResolversFunction")
    private Consumer<List<HandlerMethodArgumentResolver>> addArgumentResolversFunction = (hm) ->
    {
    };
    @Autowired(required = false)
    private Consumer<List<HandlerMethodReturnValueHandler>> addReturnValueHandlersFunction = (hm) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("configureMessageConvertersFunction")
    private Consumer<List<HttpMessageConverter<?>>> configureMessageConvertersFunction = (mc) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("extendMessageConvertersFunction")
    private Consumer<List<HttpMessageConverter<?>>> extendMessageConvertersFunction = (mc) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureHandlerExceptionResolversFunction")
    private Consumer<List<HandlerExceptionResolver>> configureHandlerExceptionResolversFunction = (l) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("extendHandlerExceptionResolversFunction")
    private Consumer<List<HandlerExceptionResolver>> extendHandlerExceptionResolversFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("getValidatorFunction")
    private Supplier<Validator> getValidatorFunction = () -> null;

    @Autowired(required = false)
    @Qualifier("getMessageCodesResolverFunction")
    private Supplier<MessageCodesResolver> getMessageCodesResolverFunction = () -> null;

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<PathMatchConfigurer> named
     * configurePathMatchFunction to override this.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurePathMatchFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<ContentNegotiationConfigurer> named
     * configureContentNegotiationFunction to override this.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configureContentNegotiationFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<AsyncSupportConfigurer> named
     * configureAsyncSupportFunction to override this.
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer)
    {
        configureAsyncSupportFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<DefaultServletHandlerConfigurer> named
     * configureDefaultServletHandlingFunction to override this.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configureDefaultServletHandlingFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<FormatterRegistry> named
     * addFormattersFunctionFunction to override this.
     */
    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        addFormattersFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<InterceptorRegistry> named
     * addInterceptorsFunction to override this.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        addInterceptorsFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<ResourceHandlerRegistry> named
     * addResourceHandlersFunction to override this.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        addResourceHandlersFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<CorsRegistry> named
     * addCorsMappingsFunction to override this.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        addCorsMappingsFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<ViewControllerRegistry> named
     * addViewControllersFunction to override this.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        addViewControllersFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<ViewResolverRegistry> named
     * configureViewResolversFunction to override this.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        configureViewResolversFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<List<HandlerMethodArgumentResolver>>
     * named addArgumentResolversFunction to override this.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        addArgumentResolversFunction.accept(argumentResolvers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<List<HandlerMethodReturnValueHandler>>
     * named addReturnValueHandlersFunction to override this.
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers)
    {
        addReturnValueHandlersFunction.accept(returnValueHandlers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<List<HttpMessageConverter<?>>> named
     * configureMessageConvertersFunction to override this.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        configureMessageConvertersFunction.accept(converters);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<List<HttpMessageConverter<?>>> named
     * extendMessageConvertersFunction to override this.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        extendMessageConvertersFunction.accept(converters);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<List<HandlerExceptionResolver>> named
     * configureHandlerExceptionResolversFunction to override this.
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers)
    {
        configureHandlerExceptionResolversFunction.accept(exceptionResolvers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer<List<HandlerExceptionResolver>> named
     * extendHandlerExceptionResolversFunction to override this.
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers)
    {
        extendHandlerExceptionResolversFunction.accept(exceptionResolvers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Supplier<Validator> named getValidatorFunction to
     * override this.
     */
    @Override
    public Validator getValidator()
    {
        return getValidatorFunction.get();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Supplier<MessageCodesResolver> named
     * getMessageCodesResolverFunction to override this.
     */
    @Override
    public MessageCodesResolver getMessageCodesResolver()
    {
        return getMessageCodesResolverFunction.get();
    }

}
