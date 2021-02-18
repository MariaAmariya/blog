package com.example.blog.util

import com.example.blog.dto.RenderedArticle
import com.example.blog.entity.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

object ArticleUtils {
	fun toSlug(value:String):String {
		return value.toLowerCase()
			.replace("\n", " ")
			.replace("[^a-z\\d\\s]".toRegex(), " ")
			.split(" ")
			.joinToString("-")
			.replace("-+".toRegex(), "-")
	}

	fun render(article: Article):RenderedArticle {
		return RenderedArticle(
			article.slug,
			article.title,
			article.headline,
			article.content,
			article.author,
			format(article.addedAt)
		)
	}

	fun format(time: LocalDateTime): String {
		return time.format(englishDateFormatter)
	}

	private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

	private val englishDateFormatter = DateTimeFormatterBuilder()
		.appendPattern("yyyy-MM-dd")
		.appendLiteral(" ")
		.appendText(ChronoField.DAY_OF_MONTH, daysLookup)
		.appendLiteral(" ")
		.appendPattern("yyyy")
		.toFormatter(Locale.ENGLISH)

	private fun getOrdinal(n: Int) = when {
		n in 11..13 -> "${n}th"
		n % 10 == 1 -> "${n}st"
		n % 10 == 2 -> "${n}nd"
		n % 10 == 3 -> "${n}rd"
		else -> "${n}th"
	}
}
