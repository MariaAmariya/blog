package com.example.blog.entity

import com.example.blog.util.ArticleUtils
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Article(
	var title: String,
	var headline: String,
	var content: String,
	@ManyToOne var author: User,
	var slug: String = ArticleUtils.toSlug(title),
	var addedAt: LocalDateTime = LocalDateTime.now(),
	@Id @GeneratedValue var id: Long? = null)
