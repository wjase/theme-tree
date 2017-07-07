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
import java.util.Comparator;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jason
 */
public interface CandidateTheme
{

    /**
     * This is the actual text name used to locate the theme. It refers to
     * assets which live under: /src/main/resources/templates/themes/[this name]
     *
     * @return the name of the theme
     */
    String getName();

    /**
     * This allows multiple themes to be sorted so they correctly cascade
     *
     * @return
     */
    int getOrder();

    /**
     * This allows a theme to be active based on specific request criteria, like
     * username or role
     *
     * @param request
     * @return
     */
    boolean isActive(HttpServletRequest request);

    /**
     * This is the comparator used to sort themes by their order.
     */
    public static Comparator<CandidateTheme> sortedTheme = new Comparator<CandidateTheme>()
    {
        @Override
        public int compare(CandidateTheme o1, CandidateTheme o2)
        {
            return Integer.reverse(Integer.compare(o1.getOrder(), o2.getOrder()));
        }
    };
}
