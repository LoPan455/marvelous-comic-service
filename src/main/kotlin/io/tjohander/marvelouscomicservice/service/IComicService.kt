package io.tjohander.marvelouscomicservice.service

import io.tjohander.marvelouscomicservice.model.api.Comic
import org.springframework.stereotype.Component

@Component
interface IComicService {
    fun getComicById(id: String): Comic?
}