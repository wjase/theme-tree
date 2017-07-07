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
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * A List-backed source of Candidate themes. Use it like a list to set add or
 * remove candidate themes.
 *
 * It is also an application event publisher which will notify subscribers when
 * the list has been updated.
 *
 * @author jason
 */
public class DefaultCandidateThemeSource implements MutableCandidateThemeSource
{

    private Set<CandidateTheme> themes = new TreeSet<>(CandidateTheme.sortedTheme);

    private ApplicationEventPublisher eventPublisher;

    public DefaultCandidateThemeSource()
    {
    }

    public DefaultCandidateThemeSource(Collection<? extends CandidateTheme> c)
    {
        themes.addAll(c.stream().collect(toList()));
    }

    @Override
    public Stream<CandidateTheme> getCandidateThemes()
    {
        return themes.stream();
    }

    @Override
    public int size()
    {
        return themes.size();
    }

    @Override
    public boolean isEmpty()
    {
        final boolean result = themes.isEmpty();
        notifyEventListeners();
        return result;
    }

    @Override
    public boolean add(CandidateTheme e)
    {
        final boolean result = themes.add(e);
        notifyEventListeners();
        return result;
    }

    @Override
    public boolean remove(CandidateTheme e)
    {
        final boolean result = themes.remove(e);
        notifyEventListeners();
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends CandidateTheme> c)
    {
        final boolean result = themes.addAll(c);
        notifyEventListeners();
        return result;
    }

    @Override
    public void clear()
    {
        themes.clear();
        notifyEventListeners();
    }

    @Autowired
    @Override
    public void setEventPublisher(ApplicationEventPublisher eventPublisher)
    {
        this.eventPublisher = eventPublisher;
        notifyEventListeners();
    }

    private void notifyEventListeners()
    {
        if (eventPublisher != null)
        {
            eventPublisher.publishEvent(new ThemesUpdatedEvent(this));
        }
    }

}
