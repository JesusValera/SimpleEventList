/*
 * Copyright (C) 2017 The Android Open Source Project
 *
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
 */

package com.rad4m.jesusreales.simpleeventlist.di

import android.arch.lifecycle.ViewModel
import com.rad4m.jesusreales.simpleeventlist.data.EventDao
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * View Model for the [UserActivity]
 */
class EventViewModel(private val dataSource: EventDao) : ViewModel() {

    fun getAll(): Flowable<List<Event>> {
        return dataSource.all()
    }

    fun insertEvent(event: Event): Completable {
        return Completable.fromAction {
            dataSource.insert(event)
        }
    }

    fun deleteEvent(event: Event): Completable {
        return Completable.fromAction {
            dataSource.delete(event)
        }
    }

}
