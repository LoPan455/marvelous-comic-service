package io.tjohander.marvelouscomicservice.controller

import io.tjohander.marvelouscomicservice.model.api.Comic
import io.tjohander.marvelouscomicservice.service.ComicsServiceOkHttp
import io.tjohander.marvelouscomicservice.service.IComicService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller("/comics/v1")
class ComicsController(
    private val service: IComicService
) {

    @GetMapping()
    fun getComics() {}

    @GetMapping("/{id}")
    @ResponseBody
    fun getComicById(@PathVariable id: String): Comic? {
        return service.getComicById(id)
    }


    @PostMapping()
    fun createComic(@RequestBody comic: String) {}

}