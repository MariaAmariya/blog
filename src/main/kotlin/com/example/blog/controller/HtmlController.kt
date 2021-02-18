package com.example.blog.controller

import com.example.blog.repository.ArticleRepository
import com.example.blog.util.ArticleUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException


@Controller
class HtmlController(private val repository: ArticleRepository) {
    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { ArticleUtils.render(it) }
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = repository.findBySlug(slug)
		if (article == null) {
			throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
		}

		val renderedArticle = ArticleUtils.render(article)

        model["title"] = renderedArticle.title
        model["article"] = renderedArticle
        return "article"
    }
}
