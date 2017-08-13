package au.com.cybernostics.themetree.theme.sources;

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

import au.com.cybernostics.themetree.theme.resolvers.CandidateTheme;

import java.util.Collection;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 * @author jason wraxall
 */
public interface MutableCandidateThemeSource extends CandidateThemeSource
{

    boolean add(CandidateTheme e);

    boolean addAll(Collection<? extends CandidateTheme> c);

    void clear();

    boolean isEmpty();

    boolean remove(CandidateTheme e);

    int size();

    void setEventPublisher(ApplicationEventPublisher eventPublisher);
}