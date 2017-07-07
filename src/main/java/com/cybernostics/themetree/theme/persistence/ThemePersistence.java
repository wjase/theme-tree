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
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Allows custom caching of themes either per application, per user or per
 * session or any other HttpServletRequest parameter.
 *
 * Subclasses of this can use cookies, session, request attributes etc to store
 * the calculated list of themes.
 *
 * @author jason
 */
public interface ThemePersistence
{

    public static final String THEME_PERSISTENCE_KEY = "themetree.themes";

    Optional<Stream<String>> get(HttpServletRequest request);

    void put(String themes,
            HttpServletRequest request,
            HttpServletResponse response);

    void clear(HttpServletRequest request,
            HttpServletResponse response);

    void clearAll();
}
