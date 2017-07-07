package com.cybernostics.themetree.theme.resolvers;

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
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;

/**
 * This is a conditional Theme controlled by a Spring Expression language SPEL
 * expression to determine if it is active.
 *
 * Expressions can refer to the current request via the #request variable
 *
 * ie Switch on a given user:
 * <pre>
 *   #request.userName=='john'
 * </pre> Other variables included:
 *
 * #requestDate a ZonedDateTime for the current request #app the application
 * context
 *
 * @author jason
 */
public class ConditionalELCandidateTheme implements CandidateTheme
{

    private SpringELRequestCondition springELRequestCondition;
    private final int order;
    private final String name;

    public ConditionalELCandidateTheme(int order, String name, String expression)
    {
        this.order = order;
        this.name = name;

        springELRequestCondition = new SpringELRequestCondition(expression);

    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public int getOrder()
    {
        return order;
    }

    @Override
    public boolean isActive(HttpServletRequest request)
    {
        return springELRequestCondition.apply(request);
    }

    public void setApplicationContext(ApplicationContext context)
    {
        springELRequestCondition.setApplicationContext(context);
    }

}
