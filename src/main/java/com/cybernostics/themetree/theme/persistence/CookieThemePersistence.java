package com.cybernostics.themetree.theme.persistence;

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
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

/**
 * Persists the list of active themes as a cookie, with an additional
 * identifier, which allows all theme cookies to be invalidated if they don't
 * match.
 *
 *
 * @author jason
 */
public class CookieThemePersistence implements ThemePersistence
{

    private static Optional<List<String>> NOTHEME = Optional.empty();
    private static CookieGenerator cookieGenerator = new CookieGenerator();
    private static String cookieGenerationId = UUID.randomUUID().toString();

    static
    {
        cookieGenerator.setCookieName(THEME_PERSISTENCE_KEY);
    }

    @Override
    public Optional<Stream<String>> get(HttpServletRequest request)
    {
        // Check request for preparsed or preset theme.
        String themeNames = (String) request.getAttribute(THEME_PERSISTENCE_KEY);
        if (themeNames != null)
        {
            return csvAsStreamIfCurrent(themeNames);
        }

        // Retrieve cookie value from request.
        Cookie cookie = WebUtils.getCookie(request, THEME_PERSISTENCE_KEY);
        if (cookie != null)
        {
            String value = cookie.getValue();
            if (StringUtils.hasText(value))
            {
                return csvAsStreamIfCurrent(value);
            }
        }
        return NOTHEME.map(l -> l.stream());
    }

    private Optional<Stream<String>> csvAsStreamIfCurrent(String themeNames)
    {
        Stream<String> values = csvAsStream(themeNames);

        // get first value and check against generation
        // if ok skip and return rest or else empty
        final Flag isCurrent = new Flag();
        Stream<String> payload = values.peek(n ->
        {
            isCurrent.setValue(cookieGenerationId.equals(n));
        }).skip(1);
        if (isCurrent.isValue())
        {
            return Optional.of(payload);
        }
        return Optional.empty();
    }

    private static Stream<String> csvAsStream(String themeNames)
    {
        return asList(themeNames.split(",")).stream();
    }

    @Override
    public void put(String themes, HttpServletRequest request, HttpServletResponse response)
    {
        cookieGenerator.addCookie(response, cookieGenerationId + "," + themes);
    }

    @Override
    public void clear(HttpServletRequest request, HttpServletResponse response)
    {
        cookieGenerator.removeCookie(response);
    }

    @Override
    public void clearAll()
    {
        cookieGenerationId = UUID.randomUUID().toString();
    }

    public static class Flag
    {

        private boolean value;

        public boolean isValue()
        {
            return value;
        }

        public void setValue(boolean value)
        {
            this.value = value;
        }

    }

}
