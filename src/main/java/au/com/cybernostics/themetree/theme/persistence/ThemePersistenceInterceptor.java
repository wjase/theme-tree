package au.com.cybernostics.themetree.theme.persistence;

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
import static au.com.cybernostics.themetree.theme.persistence.ThemePersistence.THEME_PERSISTENCE_KEY;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>ThemePersistenceInterceptor class.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class ThemePersistenceInterceptor extends HandlerInterceptorAdapter
{

    private ThemePersistence persistence;

    /**
     * <p>Constructor for ThemePersistenceInterceptor.</p>
     *
     * @param persistence a {@link au.com.cybernostics.themetree.theme.persistence.ThemePersistence} object.
     */
    public ThemePersistenceInterceptor(ThemePersistence persistence)
    {
        this.persistence = persistence;
    }

    /** {@inheritDoc} */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException
    {

        String newThemes = request.getParameter(THEME_PERSISTENCE_KEY);
        if (newThemes != null)
        {
            persistence.put(newThemes, request, response);
        }
        return true;
    }

}
