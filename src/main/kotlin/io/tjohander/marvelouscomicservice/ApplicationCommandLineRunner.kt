package io.tjohander.marvelouscomicservice

import io.tjohander.marvelouscomicservice.service.IComicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ApplicationCommandLineRunner(
    @Autowired val comicService: IComicService,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val comicResult = comicService.getComicById("7298")
        comicResult?.let {
            println(it)
        }
    }
}