package com.csb.koreamoviedb_mvc.model.dataclass

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

//루트
@Root(name = "Search", strict = false)
data class SearchResponse(
    @field:Element(name = "Result", required = false)
    var result: Result? = null,
)

//결과
@Root(name = "Result", strict = false)
data class Result(
    @field:Attribute(name = "TotalCount")
    var totalCount: Int = 0,

    @field:ElementList(entry = "Row", inline = true, required = false)
    var rows: List<Row>? = null
)

//검색결과들
@Root(name = "Row", strict = false)
data class Row(
    //Value
    @field:Attribute(name = "Value", required = false)
    var value: Int = 0,

    //ID
    @field:Element(name = "DOCID", required = false)
    var docId: String? = null,

    //제목
    @field:Element(name = "title", required = false)
    var title: String? = null,

    //링크
    @field:Element(name = "kmdbUrl", required = false)
    var kmdbUrl: String? = null,

    //배우
    @field:Element(name = "actors", required = false)
    var actors: Actors? = null,

    //감독
    @field:Element(name = "directors", required = false)
    var directors: Directors? = null,

    //줄거리
    @field:Element(name = "plots", required = false)
    var plots: Plots? = null,

    //등급
    @field:Element(name = "rating", required = false)
    var rating: String? = null,

    //등급
    @field:Element(name = "runtime", required = false)
    var runtime: String? = null,

    //개봉일 repRatDate
    @field:Element(name = "repRatDate", required = false)
    var repRatDate: String? = null,



    //포스터이미지<posters>
    @field:Element(name = "posters", required = false)
    var posters: String? = null,

    )

//배우
@Root(name = "actors", strict = false)
data class Actors(
    @field:ElementList(entry = "actor", inline = true, required = false)
    var actors: List<Actor>? = null
)

//배우들
@Root(name = "actor", strict = false)
data class Actor(
    @field:Element(name = "actorNm", required = false)
    var actorNm: String? = null,
)

//감독
@Root(name = "directors", strict = false)
data class Directors(
    @field:ElementList(entry = "director", inline = true, required = false)
    var directors: List<Director>? = null
)

//감독들
@Root(name = "director", strict = false)
data class Director(
    @field:Element(name = "directorNm", required = false)
    var directorNm: String? = null,
)

//줄거리
@Root(name = "plots", strict = false)
data class Plots(
    @field:ElementList(entry = "plot", inline = true, required = false)
    var plot: List<plot>? = null
)

//줄거리 (영문/한글 List<plot>.size() = 2)
@Root(name = "plot", strict = false)
data class plot(
    @field:Element(name = "plotText", required = false)
    var plotText: String? = null,
)