package au.com.cybernostics.themetree.theme.resolvers;

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
import java.util.Comparator;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>CandidateTheme interface.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public interface CandidateTheme
{

    /**
     * This is the actual text name used to locate the theme. It refers to
     * assets which live under: /src/main/resources/templates/themes/[this name]
     *
     * @param request - request containing variables on which to decide theme(s)
     * @return the name of the theme
     */
    String getName(HttpServletRequest request);

    /**
     * This allows multiple themes to be sorted so they correctly cascade
     *
     * @return a int.
     */
    int getOrder();

    /**
     * This allows a theme to be active based on specific request criteria, like
     * username or role
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @return a boolean.
     */
    boolean isActive(HttpServletRequest request);

    /**
     * This is the comparator used to sort themes by their order.
     */
    public static Comparator<CandidateTheme> sortedTheme = (CandidateTheme o1, CandidateTheme o2) -> Integer.reverse(Integer.compare(o1.getOrder(), o2.getOrder()));
}
