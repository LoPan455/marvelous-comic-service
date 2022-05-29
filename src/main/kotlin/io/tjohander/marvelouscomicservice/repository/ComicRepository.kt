package io.tjohander.marvelouscomicservice.repository

import io.tjohander.marvelouscomicservice.model.api.Comic
import org.springframework.data.mongodb.repository.MongoRepository

interface ComicRepository : MongoRepository<Comic, Int> {
}